package com.crossover.trial.transformer;

import com.crossover.trial.dto.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anirudh on 03/05/15.
 */

@Component
public class TransactionTransformer {

    public Transaction getTransactionDtoFromDomain(com.crossover.trial.domain.Transaction transaction){
        Transaction transactionDto = new Transaction();
        BeanUtils.copyProperties(transaction,transactionDto);
        transactionDto.setAccountNumber(transaction.getAccount().getAccountNumber());
        return transactionDto;
    }
    public com.crossover.trial.domain.Transaction getTransactionDomainFromDto(Transaction transactionDto){
        com.crossover.trial.domain.Transaction transaction = new com.crossover.trial.domain.Transaction();
        BeanUtils.copyProperties(transactionDto,transaction);
        return transaction;
    }

    public List<Transaction> getTransactionDtoListFromDomainList(List<com.crossover.trial.domain.Transaction> transactionList){
        List<Transaction> transactionDtoList = new ArrayList<>();
        for(com.crossover.trial.domain.Transaction transaction : transactionList){
            transactionDtoList.add(getTransactionDtoFromDomain(transaction));
        }
        System.out.println("transaction list " + transactionDtoList.size());
        return transactionDtoList;
    }
}
