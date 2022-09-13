package com.mindhub.hombanking.controllers;
import com.mindhub.hombanking.DTO.AccountDTO;
import com.mindhub.hombanking.DTO.ClientDTO;
import com.mindhub.hombanking.models.*;
import com.mindhub.hombanking.repository.AccountRepository;
import com.mindhub.hombanking.repository.ClientRepository;
//import com.mindhub.hombanking.repository.TransactionDisabledRepository;
import com.mindhub.hombanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired //me permite usar los metodos y propiedades de mi repositorio
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    //@Autowired
    //private TransactionDisabledRepository transactionDisabledRepository;

    int min = 1000000;
    int max = 9999999;
    Random random = new Random();

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
   };

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountsId(@PathVariable long id){
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    };

    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccountsDTO(Authentication authentication){
        Client current = clientRepository.findByEmail(authentication.getName());
        List<Account> existAccount = current.getAccounts().stream().filter(account -> account.isExistAccount()).collect(Collectors.toList());
        return existAccount.stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> registerAccount(Authentication authentication, @RequestParam AccountType accountType) { //significa entidad respuesta, le aplico una logica de negocio asociada a una ruta
        // response entiti nos permite enviar respuesta http desde nuestra aplicacion backend hasta nuestro front
        Client current = clientRepository.findByEmail(authentication.getName());
        List<Account> existAccount = current.getAccounts().stream().filter(account -> account.isExistAccount()).collect(Collectors.toList());
        if (accountType.toString().isEmpty()){
            return new ResponseEntity<>("please select a account type", HttpStatus.FORBIDDEN);
        }
        if (existAccount.size() >= 3){
            return new ResponseEntity<>("nonoo ya tenes 3 cuentas creadas", HttpStatus.FORBIDDEN);
        }
        LocalDateTime creationDate = LocalDateTime.now();
        String number;
        do {
            number = "VIN"+random.ints(min, max).findFirst().getAsInt();
        }while (accountRepository.findByNumber(number) != null);
        double balance = 0;
        accountRepository.save( new Account(number,creationDate,balance,current,true,accountType));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PatchMapping("/clients/current/accounts/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable long id,Authentication authentication){
        Client current = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findById(id).orElse(null);

        if (current.getAccounts().size() < 2){
            return new ResponseEntity<>("no puedes eliminar todas las cuentas", HttpStatus.FORBIDDEN);
        }

        if (!current.getAccounts().contains(account)){
            return new ResponseEntity<>("la cuenta no pertenece al ciente verificado", HttpStatus.FORBIDDEN);
        }
        account.getTransaction().stream().forEach(transaction -> transaction.setExistTransaction(false));
        account.setExistAccount(false);
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
