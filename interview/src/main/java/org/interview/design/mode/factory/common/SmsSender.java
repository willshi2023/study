package org.interview.design.mode.factory.common;

public class SmsSender implements Sender {

	@Override
	public void Send() {
		System.out.println("this is sms sender!");  
	}

}
