package com.crossover.trial.web;

import com.crossover.trial.dto.Account;
import com.crossover.trial.dto.User;
import com.crossover.trial.exception.NoSuchUserException;
import com.crossover.trial.repository.UserRepository;
import com.crossover.trial.transformer.UserTransformer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by anirudh on 02/05/15.
 */

@Controller
@RequestMapping("/api/users")
public class UserResource {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransformer userTransformer;

    @RequestMapping(value = "/{username}")
    @ResponseBody
    public User getUserByUserName(@PathVariable("username") String username) throws NoSuchUserException {
        final com.crossover.trial.domain.User persistedUser = userRepository.findByUsername(username);

        if (persistedUser == null) {
            throw new NoSuchUserException("No such user exist!");
        }

        final User targetUser = new User();
        BeanUtils.copyProperties(persistedUser, targetUser);
        return targetUser;

    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userRepository.save(userTransformer.getUserDomainObjectFromDto(user));
        return new ResponseEntity<String>("User Saved successfully!", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/authenticate")
    @ResponseBody
    public String authenticateUser(@RequestBody User user) {
        final com.crossover.trial.domain.User persistedUser = userRepository.findByUsername(user.getUsername());
        System.out.println("user with user name  " + user.getUsername() + " exists");

        if (persistedUser != null && persistedUser.getPassword().equals(user.getPassword())) {
            BeanUtils.copyProperties(persistedUser, user);
            System.out.println("user with user name  " + user.getUsername() + " authenticated!");
            return "true";
        } else {
            System.out.println("Authentication failed for user with user name  " + user.getUsername());
            return null;
        }

    }

    @RequestMapping(value ="/accounts/{username}")
    @ResponseBody
    public List<Account> getAllAcountsOfUser(@PathVariable("username") String username) throws NoSuchUserException {
        final com.crossover.trial.domain.User user = userRepository.findByUsername(username);
        if(user==null){
            throw new NoSuchUserException("No such user exist!");
        }

        System.out.println("user found : " + user);
        final List<com.crossover.trial.domain.Account> userAccounts = user.getUserAccounts();
        System.out.println("user accounts found : "+userAccounts.size());

        return userTransformer.getUserDtoFromUserDomain(user).getUserAccounts();

    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> getAlUsers(){
        List<com.crossover.trial.domain.User> userList = (List<com.crossover.trial.domain.User>)userRepository.findAll();
        return userTransformer.getUserDtoList(userList);
    }
}
