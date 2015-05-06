package com.crossover.trial.domain;

import com.crossover.trial.repository.AccountRepository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by anirudh on 02/05/15.
 */

@Entity
public class Account {

    private Long id;
    private String accountNumber;


    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private BigDecimal balance;

    private User user;

    private List<Transaction> transactionList;

    public final Lock lock = new ReentrantLock();

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "account")
    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    boolean withdraw(BigDecimal amount,AccountRepository repository) {
        if (this.lock.tryLock()) {
            // Wait to simulate io like database access ...
            try {
                Thread.sleep(10l);
            } catch (InterruptedException e) {
            }
            this.balance = this.balance.subtract(amount);
            repository.save(this);
            return true;
        }
        return false;
    }

    boolean deposit(BigDecimal amount,AccountRepository repository) {
        if (this.lock.tryLock()) {
            try {
                Thread.sleep(10l);
            } catch (InterruptedException e) {
            }
            this.balance = this.balance.add(amount);
            repository.save(this);
            return true;
        }
        return false;
    }

    public boolean tryTransfer(Account destinationAccount, BigDecimal amount,AccountRepository repository) {
        if (this.withdraw(amount,repository)) {
            if (destinationAccount.deposit(amount,repository)) {
                return true;
            } else {
                // destination account busy, refund source account.
                this.deposit(amount,repository);
            }
        }

        return false;
    }
}
