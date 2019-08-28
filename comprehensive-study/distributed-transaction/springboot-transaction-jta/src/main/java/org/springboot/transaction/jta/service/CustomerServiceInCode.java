package org.springboot.transaction.jta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.transaction.jta.dao.CustomerRepository;
import org.springboot.transaction.jta.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class CustomerServiceInCode {
	private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceInCode.class);

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;

	public Customer create(Customer customer) {
		LOG.info("CustomerService In Code create customer:{}", customer.getUsername());
		if (customer.getId() != null) {
			throw new RuntimeException("用户已经存在");
		}
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		def.setTimeout(15);
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			customer.setUsername("Code:" + customer.getUsername());
			customerRepository.save(customer);
			transactionManager.commit(status);
			return customer;
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
	}
}
