package com.crossover.trial.repository;

import com.crossover.trial.domain.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by anirudh on 02/05/15.
 */
public interface AccountRepository extends CrudRepository<Account,Long> {
    Account findByAccountNumber(String accountNumber);
}
