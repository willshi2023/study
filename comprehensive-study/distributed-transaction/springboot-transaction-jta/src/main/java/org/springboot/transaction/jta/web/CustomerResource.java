package org.springboot.transaction.jta.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.transaction.jta.dao.CustomerRepository;
import org.springboot.transaction.jta.domain.Customer;
import org.springboot.transaction.jta.service.CustomerServiceInAnnotation;
import org.springboot.transaction.jta.service.CustomerServiceInCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
	private static final Logger LOG = LoggerFactory.getLogger(CustomerResource.class);
	@Autowired
	private CustomerServiceInCode customerServiceInCode;
	
	@Autowired
	private CustomerServiceInAnnotation customerServiceInAnnotation;
	@Autowired
	private CustomerRepository customerRepository;
	
	/**
	 * curl -X POST -d '{"username":"imooc2","password":"111111","role":"USER"}' -H 'Content-Type: application/json' http://localhost:8080/api/customer/code
	 * @param customer
	 * @return
	 */
	@PostMapping("/code")
	public Customer createInCode(@RequestBody Customer customer) {
        LOG.info("CustomerResource create in code create customer:{}", customer.getUsername());
        return customerServiceInCode.create(customer);
	}
	
	/**
	 * curl -X POST -d '{"username":"imooc1","password":"111111","role":"USER"}' -H 'Content-Type: application/json' http://localhost:8080/api/customer/annotation
	 * @param customer
	 * @return
	 */
	@PostMapping("/annotation")
	public Customer createInAnnotation(@RequestBody Customer customer) {
		LOG.info("CustomerResource create in annotation create customer:{}", customer.getUsername());
		return customerServiceInAnnotation.create(customer);
	}
	
	/**
	 * curl http://localhost:8080/api/customer
	 * @return
	 */
	@GetMapping("")
	public List<Customer> getAll(){
		return customerRepository.findAll();
	}
}
