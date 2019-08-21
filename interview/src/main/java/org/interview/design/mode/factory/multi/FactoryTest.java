package org.interview.design.mode.factory.multi;

import org.interview.design.mode.factory.common.Sender;

public class FactoryTest {
	public static void main(String[] args) {  
        SendFactory factory = new SendFactory();  
        Sender sender = factory.produceMail();  
        sender.Send();  
    }  
}
