package com.mindhub.hombanking.repository;

import com.mindhub.hombanking.models.Client;
import com.mindhub.hombanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
