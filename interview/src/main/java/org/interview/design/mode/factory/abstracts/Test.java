package org.interview.design.mode.factory.abstracts;

import org.interview.design.mode.factory.common.Sender;

/**
 * 抽象工厂的测试类
 * @author Administrator
 *
 */
public class Test {
	public static void main(String[] args) {  
        Provider provider = new SendMailFactory();  
        Sender sender = provider.produce();  
        sender.Send();  
    }  
}
