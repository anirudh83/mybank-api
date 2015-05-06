package com.crossover.trial.service;

import com.crossover.trial.domain.Account;
import com.crossover.trial.domain.Transaction;
import com.crossover.trial.exception.InsufficientAmountException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by anirudh on 03/05/15.
 */
public interface TransactionService {
    List<Transaction> fetchAllTransactionsForAccountNumber(String accountNumber);
    List<Transaction> fetchAllTransactionsForAccountNumber(String accountNumber,Date startDate, Date endDate);
    Boolean transferFunds(Account fromAccount, Account toAccount, BigDecimal fundAmount,String description) throws InsufficientAmountException, InterruptedException;
}
