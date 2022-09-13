package com.mindhub.hombanking.repository;
import com.mindhub.hombanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientLoanRepository  extends JpaRepository<ClientLoan, Long>{
}
