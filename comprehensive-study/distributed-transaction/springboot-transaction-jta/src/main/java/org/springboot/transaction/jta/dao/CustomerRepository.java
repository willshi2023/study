package org.springboot.transaction.jta.dao;

import org.springboot.transaction.jta.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	Customer findOneByUsername(String username);
}
