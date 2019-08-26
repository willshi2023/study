package org.springboot.transaction.jpa.web;

import java.util.List;

import org.springboot.transaction.jpa.dao.CustomerRepository;
import org.springboot.transaction.jpa.domain.Customer;
import org.springboot.transaction.jpa.service.CustomerServiceInAnnotation;
import org.springboot.transaction.jpa.service.CustomerServiceInCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
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
		return customerServiceInCode.create(customer);
	}
	
	/**
	 * curl -X POST -d '{"username":"imooc1","password":"111111","role":"USER"}' -H 'Content-Type: application/json' http://localhost:8080/api/customer/annotation
	 * @param customer
	 * @return
	 */
	@PostMapping("/annotation")
	public Customer createInAnnotation(@RequestBody Customer customer) {
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
