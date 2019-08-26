package org.springboot.transaction.jpa.dao;

import org.springboot.transaction.jpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	Customer findOneByUsername(String username);
}
