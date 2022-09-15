package com.mindhub.hombanking;

import com.mindhub.hombanking.models.*;
import com.mindhub.hombanking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HombankingApplication {


	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HombankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientrepository,
									  AccountRepository accountrepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) {
		return (args) -> {

			// save a couple of customers
			Client admin = new Client("erick","barraza","admin@gmail.com", passwordEncoder.encode("admin"));

			clientrepository.save(admin);

			Client client1 = new Client("melba","morel","melba@mindhub.com", passwordEncoder.encode("melba"));

			clientrepository.save(client1);

			LocalDateTime creationDate = LocalDateTime.now();

			Account account1 = new Account("VIN001",creationDate,4700.00,client1,true,AccountType.ahorro);

			accountrepository.save(account1);
			LocalDate thruDate = LocalDate.now();

			LocalDate fromDate = LocalDate.now().plusYears(5);

			Card card1 = new Card("Melba Morel",CardType.DEBIT,CardColor.GOLD,"5412 7512 3412 3456",123,thruDate,fromDate,true,client1);

			cardRepository.save(card1);

			LocalDateTime date = LocalDateTime.now();

			Transaction transaction2 = new Transaction(TransactionType.CREDITO,200.02,"transaccion hecha a pepe",date,account1,true);

			transactionRepository.save(transaction2);
			Transaction transaction3 = new Transaction(TransactionType.CREDITO,210.02,"transaccion hecha a pepe",date,account1,true);

			transactionRepository.save(transaction3);
			Transaction transaction4 = new Transaction(TransactionType.CREDITO,5200.02,"transaccion hecha a pepe",date,account1,true);

			transactionRepository.save(transaction4);
			Transaction transaction5 = new Transaction(TransactionType.CREDITO,3200.02,"transaccion hecha a pepe",date,account1,true);

			transactionRepository.save(transaction5);
			Transaction transaction6 = new Transaction(TransactionType.CREDITO,2030.02,"transaccion hecha a pepe",date,account1,true);

			transactionRepository.save(transaction6);
			Transaction transaction7 = new Transaction(TransactionType.CREDITO,1200.02,"transaccion hecha a pepe",date,account1,true);

			transactionRepository.save(transaction7);
			Transaction transaction8 = new Transaction(TransactionType.CREDITO,1200.02,"transaccion hecha a pepe",date,account1,true);

			transactionRepository.save(transaction8);
			Transaction transaction9 = new Transaction(TransactionType.CREDITO,20.02,"transaccion hecha a pepe",date,account1,true);

			transactionRepository.save(transaction9);
			Transaction transaction10 = new Transaction(TransactionType.CREDITO,2200.02,"transaccion hecha a pepe",date,account1,true);

			transactionRepository.save(transaction10);
			Transaction transaction11 = new Transaction(TransactionType.CREDITO,1200.02,"transaccion hecha a pepe",date,account1,true);

			transactionRepository.save(transaction11);
			/*
			Client client2 = new Client("melba2","morel2","melba2@gmail.com", passwordEncoder.encode("melba2"));

			clientrepository.save(client2);

			Client client3 = new Client("melba3","morel3","melba3@gmail.com", passwordEncoder.encode("melba3"));

			clientrepository.save(client3);



			Account account1admin = new Account("VIN011",creationDate,4700.00,admin);

			accountrepository.save(account1admin);

			Account account2admin = new Account("VIN012",creationDate,2400.00,admin);

			accountrepository.save(account2admin);

			Account account4admin = new Account("VIN013",creationDate,3200.00,admin);

			accountrepository.save(account4admin);



			Account account2 = new Account("VIN002",creationDate,100.00,client1);

			accountrepository.save(account2);

			Account account4 = new Account("VIN003",creationDate,200.00,client1);

			accountrepository.save(account4);

			Account account3 = new Account("VIN004",creationDate,900.00,client2);

			accountrepository.save(account3);



            Transaction transaction1 = new Transaction(TransactionType.DEBITO,-1200.02,"transaccion hecha a pepe",date,account1);

			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(TransactionType.CREDITO,200.02,"transaccion hecha a pepe",date,account1);

			transactionRepository.save(transaction2);

			Transaction transaction3 = new Transaction(TransactionType.DEBITO,-3500.02,"transaccion hecha a pepe",date,account1);

			transactionRepository.save(transaction3);
			Transaction transaction4 = new Transaction(TransactionType.DEBITO,-2500.02,"transaccion hecha a pepe",date,account1);

			transactionRepository.save(transaction4);

			Transaction transaction6 = new Transaction(TransactionType.CREDITO,4200.02,"transaccion hecha a pepe",date,account1);

			transactionRepository.save(transaction6);

			Transaction transaction7 = new Transaction(TransactionType.DEBITO,-4200.02,"transaccion hecha a pepe",date,account2);

			transactionRepository.save(transaction7);
			Transaction transaction8 = new Transaction(TransactionType.CREDITO,3100.02,"transaccion hecha a pepe",date,account2);

			transactionRepository.save(transaction8);
			Transaction transaction9 = new Transaction(TransactionType.DEBITO,-700.02,"transaccion hecha a pepe",date,account2);

			transactionRepository.save(transaction9);

			Transaction transaction10 = new Transaction(TransactionType.CREDITO,1200.02,"transaccion hecha a pepe",date,account2);

			transactionRepository.save(transaction10);

			//

			Transaction transaction1admin = new Transaction(TransactionType.DEBITO,-1200.02,"transaccion hecha a pepe",date,account1admin);

			transactionRepository.save(transaction1admin);

			Transaction transaction2admin = new Transaction(TransactionType.CREDITO,200.02,"transaccion hecha a pepe",date,account1admin);

			transactionRepository.save(transaction2admin);

			Transaction transaction3admin = new Transaction(TransactionType.DEBITO,-3500.02,"transaccion hecha a pepe",date,account1admin);

			transactionRepository.save(transaction3admin);

			Transaction transaction4admin = new Transaction(TransactionType.DEBITO,-2500.02,"transaccion hecha a pepe",date,account1admin);

			transactionRepository.save(transaction4admin);

			Transaction transaction5admin = new Transaction(TransactionType.CREDITO,100.02,"transaccion hecha a pepe",date,account1admin);

			transactionRepository.save(transaction5admin);

			Transaction transaction6admin = new Transaction(TransactionType.CREDITO,4200.02,"transaccion hecha a pepe",date,account1admin);

			transactionRepository.save(transaction6admin);

			Transaction transaction7admin = new Transaction(TransactionType.DEBITO,-4200.02,"transaccion hecha a pepe",date,account2admin);

			transactionRepository.save(transaction7admin);

			Transaction transaction8admin = new Transaction(TransactionType.CREDITO,3100.02,"transaccion hecha a pepe",date,account2admin);

			transactionRepository.save(transaction8admin);

			Transaction transaction9admin = new Transaction(TransactionType.DEBITO,-700.02,"transaccion hecha a pepe",date,account2admin);

			transactionRepository.save(transaction9admin);

			Transaction transaction10admin = new Transaction(TransactionType.CREDITO,1200.02,"transaccion hecha a pepe",date,account2admin);

			transactionRepository.save(transaction10admin);
*/

			// pagos
			//forma uno de hacerlo
			List<Integer> mortagePayment = List.of(12,24,36,48,60);

			Loan loanmortage = new Loan("Mortage",500000,mortagePayment,1.5,50);

			loanRepository.save(loanmortage);

			Loan personalLoan = new Loan("Personal",100000, Arrays.asList(6,12,24),1.2,20);// forma 2

			loanRepository.save(personalLoan);

			Loan automotiveLoan = new Loan("Automotive",300000,List.of(6,12,24,36),1.3,30);// forma 3

			loanRepository.save(automotiveLoan);

			// clientLoan
/*
			ClientLoan loan1 = new ClientLoan(400000,60,client1,loanmortage,12);

			clientLoanRepository.save(loan1);

			ClientLoan loan2 = new ClientLoan(50000,12,client1,personalLoan,12);

			clientLoanRepository.save(loan2);

			ClientLoan loan3 = new ClientLoan( 100000,24,admin,personalLoan,12);

			clientLoanRepository.save(loan3);

			ClientLoan loan4 = new ClientLoan( 200000,36,admin,automotiveLoan,12);

			clientLoanRepository.save(loan4);

            ClientLoan loan5 = new ClientLoan(10000,12,admin,personalLoan,12);

            clientLoanRepository.save(loan5);

            ClientLoan loan6 = new ClientLoan(200000,36,admin,loanmortage,12);

            clientLoanRepository.save(loan6);

            ClientLoan loan7 = new ClientLoan(1000,6,admin,personalLoan,12);

            clientLoanRepository.save(loan7);
			ClientLoan loan8 = new ClientLoan( 100000,24,admin,personalLoan,12);

			clientLoanRepository.save(loan8);

			ClientLoan loan9 = new ClientLoan( 200000,36,admin,automotiveLoan,12);

			clientLoanRepository.save(loan9);

			ClientLoan loan10 = new ClientLoan(10000,12,admin,personalLoan,12);

			clientLoanRepository.save(loan10);

			ClientLoan loan11 = new ClientLoan(200000,36,admin,loanmortage,12);

			clientLoanRepository.save(loan11);

			ClientLoan loan12 = new ClientLoan(1000,6,admin,personalLoan,12);

			clientLoanRepository.save(loan12);

			// tarjetas



			Card card2 = new Card("Erick Barraza",CardType.DEBIT,CardColor.GOLD,"5412 7512 3412 3456","5412 7512 3412 3456",123,thruDate,fromDate,admin);

			cardRepository.save(card2);

			Card card3 = new Card("Erick Barraza",CardType.CREDIT,CardColor.TITANIUM,"5555 5123 2314 8821","5555 5123 2314 8821",223,thruDate,fromDate,admin);

			cardRepository.save(card3);


*/

    	};

	}

}
