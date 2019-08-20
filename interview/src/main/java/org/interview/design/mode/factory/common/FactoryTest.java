package org.interview.design.mode.factory.common;

/**
 * 测试普通工厂模式的测试类
 * @author Administrator
 *
 */
public class FactoryTest {
	public static void main(String[] args) {  
        SendFactory factory = new SendFactory();  
        Sender sender = factory.produce("sms");  
        sender.Send();  
    }  
}
