package org.interview.proxy.dynamic.jdk;

import java.io.Serializable;

public class BookFacadeImpl implements BookFacade,Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void addBook() {
		System.out.println("增加图书方法。。。");  
	}

}
