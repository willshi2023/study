package org.interview.service;

import org.springframework.stereotype.Service;

@Service
public interface TransactionTestService {
	public void testNever();
	public void testRequired();
	public void testNested();
	public void testNested2();
	public void testRequiresNew();
	public void testRequiresNew2();
	public void testNotSupported();
	public void testNotSupported2();
	public void testMandatory();
}
