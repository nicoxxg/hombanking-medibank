package com.mindhub.hombanking.controllers;

import com.mindhub.hombanking.DTO.LoanApplicationDTO;
import com.mindhub.hombanking.DTO.LoanDTO;
import com.mindhub.hombanking.models.*;
import com.mindhub.hombanking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class LoanController {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @GetMapping(path = "/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map( loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }
    @Transactional
    @PostMapping(path = "/loans")
    public ResponseEntity<Object> createLoans(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
        Client current = clientRepository.findByEmail(authentication.getName());
        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId());
        Account accountVerificate = accountRepository.findByNumber(loanApplicationDTO.getNumber());
        if (loanApplicationDTO.getLoanId() == 0 || loanApplicationDTO.getNumber().isEmpty()){
            return new ResponseEntity<>("please complete the data",HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() <= 0 ){
            return new ResponseEntity<>("please enter an amount other than 0 or negative number",HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getPayments() == 0){
            return new ResponseEntity<>("que intentas hacer poniendo 0 cuotas?",HttpStatus.FORBIDDEN);
        }
        if (loan == null){
            return new ResponseEntity<>("the account you selected does not exist",HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("the requested amount exceeds the requested loan",HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("the amount of requested payments exceeds the amount of loan payments",HttpStatus.FORBIDDEN);
        }
        if (accountVerificate == null){
            return new ResponseEntity<>("the account does not exist",HttpStatus.FORBIDDEN);
        }
        if (current.getAccounts().stream().filter(account -> account.getNumber().contains(loanApplicationDTO.getNumber())).collect(Collectors.toList()).size() == 0){
            return new ResponseEntity<>("the account does not belong to the verified customer",HttpStatus.FORBIDDEN);
        }
        double amount = loanApplicationDTO.getAmount() * loan.getPercentage();
        double amountPayment = Math.rint(amount) / loanApplicationDTO.getPayments();
        ClientLoan clientLoan = new ClientLoan(amount, loanApplicationDTO.getPayments(), current,loan,amountPayment);
        clientLoanRepository.save(clientLoan);
        LocalDateTime date = LocalDateTime.now();
        Transaction transaction = new Transaction(TransactionType.CREDITO,+loanApplicationDTO.getAmount(), "loan approved",date,accountVerificate,true);
        transactionRepository.save(transaction);
        accountVerificate.setBalance(accountVerificate.getBalance() + loanApplicationDTO.getAmount());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/loans/create")
    public ResponseEntity<Object> createNewLoan(@RequestBody LoanApplicationDTO loanApplicationDTO){

        if (loanApplicationDTO.getName().isEmpty() ||  loanApplicationDTO.getMaxAmount() == 0 || loanApplicationDTO.getPercentage() == 0 || loanApplicationDTO.getPercentageInt() == 0){
            return new ResponseEntity<>("te falta informacion capa",HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getListPayments().toString().isEmpty()){
            return new ResponseEntity<>("la lista esta mal capa",HttpStatus.FORBIDDEN);
        }
        String name = loanApplicationDTO.getName();
        List<Integer> payments = loanApplicationDTO.getListPayments();
        double maxAmount = loanApplicationDTO.getMaxAmount();
        double percentage = loanApplicationDTO.getPercentage();
        int percentageInt = loanApplicationDTO.getPercentageInt();
        loanRepository.save(new Loan(name,maxAmount,payments,percentage,percentageInt));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
