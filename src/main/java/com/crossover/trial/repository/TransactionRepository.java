package com.crossover.trial.repository;

import com.crossover.trial.domain.Account;
import com.crossover.trial.domain.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by anirudh on 02/05/15.
 */
public interface TransactionRepository extends CrudRepository<Transaction,Long> {
        List<Transaction> findByAccountOrderByTransactionDateDesc(Account account);
}
