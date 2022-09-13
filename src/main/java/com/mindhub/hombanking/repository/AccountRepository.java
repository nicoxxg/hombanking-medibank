package com.mindhub.hombanking.repository;

import com.mindhub.hombanking.models.Account;
import com.mindhub.hombanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNumber(String number);
}
