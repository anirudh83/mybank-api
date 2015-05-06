package com.crossover.trial.transformer;

import com.crossover.trial.domain.Account;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by anirudh on 03/05/15.
 */

@Component
public class AccountTransformer {

    public Account getAccountDomainFromDto(com.crossover.trial.dto.Account accountDto){
        Account account = new Account();
        BeanUtils.copyProperties(accountDto,account);
        return account;
    }

        public com.crossover.trial.dto.Account getAccountDtoFromDomain(Account account){
        com.crossover.trial.dto.Account accountDto = new com.crossover.trial.dto.Account();
        BeanUtils.copyProperties(account,accountDto);
        return accountDto;
    }
}
