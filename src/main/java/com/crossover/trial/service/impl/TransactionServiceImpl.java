package com.crossover.trial.service.impl;

import com.crossover.trial.domain.Account;
import com.crossover.trial.domain.Transaction;
import com.crossover.trial.domain.TransactionType;
import com.crossover.trial.exception.InsufficientAmountException;
import com.crossover.trial.repository.AccountRepository;
import com.crossover.trial.repository.TransactionRepository;
import com.crossover.trial.service.IndexService;
import com.crossover.trial.service.TransactionService;
import com.crossover.trial.transformer.TransactionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by anirudh on 03/05/15.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IndexService indexService;

    @Autowired
    private TransactionTransformer transactionTransformer;

    @Override
    public List<Transaction> fetchAllTransactionsForAccountNumber(String accountNumber) {
        final com.crossover.trial.domain.Account account = accountRepository.findByAccountNumber(accountNumber);
        final List<Transaction> byAccount = transactionRepository.findByAccountOrderByTransactionDateDesc(account);
        System.out.println("number of transactions found : " + byAccount.size());
        return byAccount;
    }

    @Override
    public List<Transaction> fetchAllTransactionsForAccountNumber(String accountNumber, Date startDate, Date endDate) {
        //TODO
        return null;
    }

    @Override
    @Transactional
    public Boolean transferFunds(Account fromAccount, Account toAccount, BigDecimal fundAmount,String description) throws InsufficientAmountException, InterruptedException {

        if (subtractAndCheckLessThanZero(toAccount.getBalance(), fundAmount)) {
            throw new InsufficientAmountException(
                    "Insufficient Balance");
        } else {

            while (!fromAccount.tryTransfer(toAccount,fundAmount,accountRepository)) {
                continue;
            }

            logTransaction(fromAccount, toAccount, fundAmount, description);
        }
        return true;
    }

    private void logTransaction(Account fromAccount, Account toAccount, BigDecimal fundAmount, String description) {
        logTransaction(fromAccount,description, TransactionType.WITHDRAWAL.toString(),fundAmount,fromAccount.getBalance());
        logTransaction(toAccount,description,TransactionType.DEPOSIT.toString(),fundAmount,toAccount.getBalance());
    }

    private void logTransaction(Account account, String description, String type,BigDecimal amount,BigDecimal closingBalance ) {
        Transaction transaction = new Transaction();

        transaction.setAccount(account);
        transaction.setTransactionDate(new Date());
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setTransactionType(type);
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setClosingBalance(closingBalance);

        final Transaction savedTransaction = transactionRepository.save(transaction);
        System.out.println("Indexing doc with transaction id "+savedTransaction.getId());
        indexService.indexDocument(transactionTransformer.getTransactionDtoFromDomain(savedTransaction));
    }

    private boolean subtractAndCheckLessThanZero(BigDecimal balance, BigDecimal amount) {
        BigDecimal check_bal = new BigDecimal(balance.toString());
        return (check_bal.subtract(amount).compareTo(BigDecimal.ZERO) < 0);
    }
}
