package com.crossover.trial.repository;

import com.crossover.trial.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by anirudh on 02/05/15.
 */
public interface UserRepository extends CrudRepository<User,Long>{

    User findByUsername(String username);
}
