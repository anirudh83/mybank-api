package com.crossover.trial.web;

import com.crossover.trial.dto.Account;
import com.crossover.trial.dto.Transaction;
import com.crossover.trial.dto.TransferDto;
import com.crossover.trial.exception.InsufficientAmountException;
import com.crossover.trial.repository.AccountRepository;
import com.crossover.trial.service.TransactionService;
import com.crossover.trial.transformer.AccountTransformer;
import com.crossover.trial.transformer.TransactionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by anirudh on 03/05/15.
 */

@Controller
@RequestMapping("/api/accounts")
public class AccountResource {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionTransformer transactionTransformer;
    @Autowired
    private AccountTransformer transformer;

    @RequestMapping(value = "/{accountnumber}")
    @ResponseBody
    public Account getAccountDetails(@PathVariable("accountnumber") String accountNumber){
        final com.crossover.trial.domain.Account byAccountNumber = accountRepository.findByAccountNumber(accountNumber);
        if(byAccountNumber!=null) {
            return transformer.getAccountDtoFromDomain(byAccountNumber);
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/{accountnumber}/transactions")
    @ResponseBody
    public List<Transaction> getAllTransactions(@PathVariable("accountnumber") String accountNumber,
                                                @RequestParam(value = "startDate",required = false) String startDate,
                                                @RequestParam(value = "endDate",required = false) String endDate){

        return transactionTransformer.getTransactionDtoListFromDomainList(transactionService.fetchAllTransactionsForAccountNumber(accountNumber));
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public String transferAmount(@RequestBody TransferDto transferDto){
        System.out.println("request recived for transfer "+transferDto.getAccountNumberFrom()+" -->" +transferDto.getAccountNumberTo());
        final com.crossover.trial.domain.Account accountFrom = accountRepository.findByAccountNumber(transferDto.getAccountNumberFrom());
        final com.crossover.trial.domain.Account accountTo = accountRepository.findByAccountNumber(transferDto.getAccountNumberTo());
        if(accountTo==null){
            return "The account you are trying to transfer does not exist";
        }
        try {
            transactionService.transferFunds(accountFrom,accountTo,new BigDecimal(transferDto.getAmount()),transferDto.getDescription());
        } catch (InsufficientAmountException e) {
            e.printStackTrace();
            return "failed to transfer due to insufficient funds!";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "failed to transfer!";
        }
        return "Successfully transferred!";
    }
}
