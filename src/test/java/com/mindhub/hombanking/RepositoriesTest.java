package com.mindhub.hombanking;


import com.mindhub.hombanking.models.*;
import com.mindhub.hombanking.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest// es un testeo general
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }
    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }
    @Test
    public void findAllClients(){
        List<Client> client = clientRepository.findAll();
        assertThat(client, is(not(empty())));
    }
    @Test
    public void findClient(){
        Client client = clientRepository.findByEmail("admin@gmail.com");
        String clientEmail = client.getEmail();
        assertThat(clientEmail,containsString("admin@gmail.com"));
    }
    @Test
    public  void findAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }
    @Test
    public  void findAccountById(){
        long id = 1;
        Optional<Account> accounts = accountRepository.findById(id);
        long accountId = accounts.get().getId();
        assertThat(accountId, is(id));
    }
    @Test
    public void findAccountByNumber(){
        String vin = "VIN001";
        Account account = accountRepository.findByNumber(vin);
        String number = account.getNumber();
        assertThat(number, is(vin));
    }
    @Test
    public void findAllCards(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }
    @Test
    public void findCardById(){
        long id = 1;
        Optional<Card> card = cardRepository.findById(id);
        long cardId = card.get().getId();
        assertThat(cardId, is(id));
    }
    @Test
    public void findAllTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));
    }
    @Test
    public void findTransactionsById(){
        long id = 1;
        Optional<Transaction> transaction = transactionRepository.findById(id);
        assertThat(transaction.get().getId(), is(id));
    }
    @Test
    public void findAllClientLoans(){
        List<ClientLoan> clientLoans = clientLoanRepository.findAll();
        assertThat(clientLoans, is(not(empty())));
    }
    @Test
    public void findClientLoanById(){
        long id = 1;
        Optional<ClientLoan> clientLoan = clientLoanRepository.findById(id);
        assertThat(clientLoan.get().getId(), is(id));
    }


}
