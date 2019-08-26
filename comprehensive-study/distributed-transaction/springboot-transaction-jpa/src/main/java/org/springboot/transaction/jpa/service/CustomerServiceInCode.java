package org.springboot.transaction.jpa.service;

import org.springboot.transaction.jpa.dao.CustomerRepository;
import org.springboot.transaction.jpa.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class CustomerServiceInCode {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	public Customer create(Customer customer) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS); //该模式出错不会回滚
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); //该模式下出错会回滚
		TransactionStatus ts = transactionManager.getTransaction(def);
		try {
			customerRepository.save(customer);
			simulateError();
			transactionManager.commit(ts);
			return customer;
		} catch (TransactionException e) {
			e.printStackTrace();
			transactionManager.rollback(ts);
			throw e;
		}
	}
	
	private void simulateError() {
		throw new RuntimeException("aaa ");
	}
}
