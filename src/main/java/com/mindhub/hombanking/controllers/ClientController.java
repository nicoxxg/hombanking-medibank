package com.mindhub.hombanking.controllers;
import com.mindhub.hombanking.DTO.ClientDTO;
import com.mindhub.hombanking.Services.ClientService;
import com.mindhub.hombanking.models.Account;
import com.mindhub.hombanking.models.AccountType;
import com.mindhub.hombanking.models.Client;
import com.mindhub.hombanking.repository.AccountRepository;
import com.mindhub.hombanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired //genera una instancia del repositorio (inyeccion de dependencia)
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    public ClientService clientService;

    int min = 1;
    int max = 9999999;
    Random random = new Random();

    @PostMapping(path = "/clients")
    public ResponseEntity<Object> register(

            @RequestParam String name, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {

        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }
        if (clientRepository.findByEmail(email) !=  null) {

            return new ResponseEntity<>("email already in use", HttpStatus.FORBIDDEN);

        }
        if (email.contains("@admin")){
            return new ResponseEntity<>("ni en pedo vas a pensar en registrarte como admin", HttpStatus.FORBIDDEN);
        }
        LocalDateTime creationDate = LocalDateTime.now();

        String number;
        do {
            number = "VIN"+random.ints(min, max).findFirst().getAsInt();
        }while ( accountRepository.findByNumber(number) != null);

        double balance = 0.00;

        Client client = new Client(name, lastName, email, passwordEncoder.encode(password));

        clientService.saveClients(client);
        accountRepository.save(new Account(number,creationDate,balance,client,true, AccountType.corriente));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping(value = "/clients/current")

    public ClientDTO  getClientDTO(Authentication authentication) {

        return new ClientDTO(clientService.getClientByEmail(authentication.getName()));

    }

    /*
    @Autowired
    private AccountRepository accountRepository;
    */
    @GetMapping("/clients") //me define la ruta de la peticion
    public List<ClientDTO> getClients(){
        return clientService.getAllClients().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    };
    @GetMapping("/clients/{id}")
    public ClientDTO getClientId(@PathVariable long id){
        return new ClientDTO(clientService.getClientById(id)) ; //clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    };


    /*
    @PostMapping("/clients")
    public void getClientsPost( String name, String lastName, String email){
        Client post = new Client(name,lastName,email);
        this.clientRepository.save(post);
    };*/


    /*
    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }
    */


}
