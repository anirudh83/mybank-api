package com.crossover.trial.web;

import com.crossover.trial.dto.Account;
import com.crossover.trial.dto.AccountType;
import com.crossover.trial.dto.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anirudh on 03/05/15.
 */
public class UserResourceTest {


    private UserResource userResource;

    @Before
    public void setup(){
        userResource = new UserResource();
    }


    @Test
    public void testUserResource() throws JsonProcessingException {

        System.out.print(getUserJSON());

    }

    private String getUserJSON() throws JsonProcessingException {
        User user = new User();
        user.setUsername("123");
        user.setFirstName("test");
        user.setAddress("one");
        user.setPassword("test");
        user.setCountry("India");

        Account account = new Account();
        account.setAccountNumber("1234");
        account.setAccountType(AccountType.SAVINGS);
        account.setBalance(new BigDecimal(10000));

        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        user.setUserAccounts(accountList);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(user);
    }

}
