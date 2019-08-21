package org.interview.design.mode.factory.multi;

import org.interview.design.mode.factory.common.Sender;

/**
 * 多个工厂方法模式测试类
 * @author Administrator
 *
 */
public class FactoryTest {
	public static void main(String[] args) {  
        SendFactory factory = new SendFactory();  
        Sender sender = factory.produceMail();  
        sender.Send();  
    }  
}
