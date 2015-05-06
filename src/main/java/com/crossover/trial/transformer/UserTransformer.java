package com.crossover.trial.transformer;

import com.crossover.trial.domain.Account;
import com.crossover.trial.domain.AccountType;
import com.crossover.trial.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anirudh on 03/05/15.
 */

@Component
public class UserTransformer {

    public User getUserDomainObjectFromDto(com.crossover.trial.dto.User userDto){

        com.crossover.trial.domain.User toBeSavedUser = new com.crossover.trial.domain.User();
        BeanUtils.copyProperties(userDto, toBeSavedUser);

        List<Account> accountList = new ArrayList<>();
        for(com.crossover.trial.dto.Account userAccount : userDto.getUserAccounts()) {
            Account account = new Account();
            BeanUtils.copyProperties(userAccount, account);
            account.setAccountType(AccountType.valueOf(userAccount.getAccountType().toString()));
            account.setUser(toBeSavedUser);
            accountList.add(account);
        }

        toBeSavedUser.setUserAccounts(accountList);
        System.out.println("transformed user user accounts size"+toBeSavedUser.getUserAccounts().size());
        return toBeSavedUser;
    }

    public com.crossover.trial.dto.User getUserDtoFromUserDomain(User user){
        com.crossover.trial.dto.User userDto = new com.crossover.trial.dto.User();
        BeanUtils.copyProperties(user, userDto);

        List<com.crossover.trial.dto.Account> accountDtoList = new ArrayList<>();
        for(Account account : user.getUserAccounts()){
            com.crossover.trial.dto.Account accountDto = new com.crossover.trial.dto.Account();
            BeanUtils.copyProperties(account, accountDto);
            accountDtoList.add(accountDto);
        }

        userDto.setUserAccounts(accountDtoList);
        return userDto;

    }

    public List<com.crossover.trial.dto.User> getUserDtoList(List<User> userDomainList){
        List<com.crossover.trial.dto.User> userDtoList = new ArrayList<>();
        for(User user : userDomainList){
            userDtoList.add(getUserDtoFromUserDomain(user));
        }
        return userDtoList;
    }
}
