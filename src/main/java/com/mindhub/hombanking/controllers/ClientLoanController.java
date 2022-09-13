package com.mindhub.hombanking.controllers;

import com.mindhub.hombanking.DTO.ClientDTO;
import com.mindhub.hombanking.DTO.ClientLoanDTO;
import com.mindhub.hombanking.repository.ClientLoanRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientLoanController {
    @Autowired
    ClientLoanRepository clientLoanRepository;

    @GetMapping("/clientloans")
    public List<ClientLoanDTO> getClientLoans(){
        return clientLoanRepository.findAll().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toList());

    }
    @GetMapping("/clientloans/{id}")
    public ClientLoanDTO getClientLoansById(@PathVariable long id){
        return clientLoanRepository.findById(id).map(clientLoan -> new ClientLoanDTO(clientLoan)).orElse(null);
    }
}
