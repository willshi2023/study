package org.interview.design.mode.factory.statics;

import org.interview.design.mode.factory.common.Sender;

/**
 * 静态工厂方法模式测试类
 * @author Administrator
 *
 */
public class FactoryTest {
	public static void main(String[] args) {      
        Sender sender = SendFactory.produceMail();  
        sender.Send();  
    }  
}
