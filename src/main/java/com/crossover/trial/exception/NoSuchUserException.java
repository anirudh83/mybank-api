package com.crossover.trial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by anirudh on 03/05/15.
 */

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such User")
public class NoSuchUserException extends Exception {
    public NoSuchUserException(String message) {
        super(message);
    }
}
