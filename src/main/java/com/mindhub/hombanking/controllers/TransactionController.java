package com.mindhub.hombanking.controllers;

import com.mindhub.hombanking.DTO.PostnetDTO;
import com.mindhub.hombanking.DTO.TransactionDTO;
import com.mindhub.hombanking.models.*;
import com.mindhub.hombanking.repository.AccountRepository;
import com.mindhub.hombanking.repository.CardRepository;
import com.mindhub.hombanking.repository.ClientRepository;
import com.mindhub.hombanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Transactional
    @PostMapping(path = "/transactions")
    public ResponseEntity<Object> getTransactions(Authentication authentication,
                                                @RequestParam Double amount,
                                                @RequestParam String description,
                                                @RequestParam String numberOrigin ,
                                                @RequestParam String numberDestiny){
        Client current = clientRepository.findByEmail(authentication.getName());
        Account accountOrigin = accountRepository.findByNumber(numberOrigin);
        Account accountDestiny = accountRepository.findByNumber(numberDestiny);

        if (description.isEmpty()||numberOrigin.isEmpty()||numberDestiny.isEmpty()||amount.toString().isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (numberOrigin.equals(numberDestiny)){
            return new ResponseEntity<>("the account you are entering is equal to destination account", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0){
            return new ResponseEntity<>("you can not transfer 0 dollars or negative numbers", HttpStatus.FORBIDDEN);
        }
        if (accountOrigin == null){
            return new ResponseEntity<>("the account entered does not exist", HttpStatus.FORBIDDEN);
        }
        if (current.getAccounts().stream().filter(account -> account.getNumber().contains(numberOrigin)).collect(Collectors.toList()).size() == 0){
            return new ResponseEntity<>("this account does not belong to you", HttpStatus.FORBIDDEN);
        }
        if (accountDestiny == null){
            return new ResponseEntity<>("The account you want to spend money does not exist", HttpStatus.FORBIDDEN);
        }
        if (accountOrigin.getBalance()< amount){
            return new ResponseEntity<>("you don't have the money to spend", HttpStatus.FORBIDDEN);
        }
        LocalDateTime date = LocalDateTime.now();

        Transaction transactionDestiny = new Transaction(TransactionType.DEBITO,-amount,description,date,accountOrigin,true);
        transactionRepository.save(transactionDestiny);
        Transaction transactionOrigin = new Transaction(TransactionType.CREDITO,+amount,description,date,accountDestiny,true);
        transactionRepository.save(transactionOrigin);
        accountOrigin.setBalance(accountOrigin.getBalance() - amount);
        accountDestiny.setBalance(accountDestiny.getBalance() + amount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/postnet")
    public ResponseEntity<Object> createPostNet(@RequestBody PostnetDTO postnetDTO, Authentication authentication){
        Client current = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findByNumber(postnetDTO.getAccountNumber());
        Card card = cardRepository.findByNumber(postnetDTO.getCardNumber());
        LocalDate localDate = LocalDate.now();
        if (postnetDTO.getAccountNumber().isEmpty() || postnetDTO.getCardNumber().isEmpty() || postnetDTO.getAmount() == 0 || postnetDTO.getCvv() == 0 || postnetDTO.getDescription().isEmpty()){
            return new ResponseEntity<>("information is missing",HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(postnetDTO.getAccountNumber()) == null){
            return new ResponseEntity<>("the account does not exist",HttpStatus.FORBIDDEN);
        }
        if (cardRepository.findByNumber(postnetDTO.getCardNumber()) == null){
            return new ResponseEntity<>(
                    "the card does not exist",HttpStatus.FORBIDDEN);
        }
        if (!current.getAccounts().contains(account)){
            return new ResponseEntity<>("the account does not belong to the authenticated client",HttpStatus.FORBIDDEN);
        }
        if (!current.getCards().contains(card)){
            return new ResponseEntity<>(
                    "the card does not belong to the authenticated client",HttpStatus.FORBIDDEN);
        }
        if (!card.isExistCard()){
            return new ResponseEntity<>(
                    "the selected card is disabled and/or eliminated",HttpStatus.FORBIDDEN);
        }
        if (card.getThruDate().isAfter(localDate)){
            return new ResponseEntity<>(
                    "the card is expired",HttpStatus.FORBIDDEN);
        }
        if (card.getCvv() != postnetDTO.getCvv()){
            return new ResponseEntity<>("the cvv does not match that of the requested card",HttpStatus.FORBIDDEN);
        }
        if (account.getBalance() < postnetDTO.getAmount()){
            return new ResponseEntity<>("the account does not have the requested amount",HttpStatus.FORBIDDEN);
        }

        double amount = postnetDTO.getAmount();
        String description = postnetDTO.getDescription();
        LocalDateTime date = LocalDateTime.now();
        account.setBalance(account.getBalance() - postnetDTO.getAmount());
        Transaction transaction = new Transaction(TransactionType.DEBITO,-amount,description,date,account,true);
        accountRepository.save(account);
        transactionRepository.save(transaction);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
