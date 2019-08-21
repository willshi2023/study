package org.interview.design.mode.factory.statics;

import org.interview.design.mode.factory.common.MailSender;
import org.interview.design.mode.factory.common.Sender;
import org.interview.design.mode.factory.common.SmsSender;

/**
 * 静态工厂方法模式，将多个工厂方法模式里的方法置为静态的，
 * 不需要创建实例，直接调用即可。
 * @author Administrator
 *
 */
public class SendFactory {
	public static Sender produceMail(){  
        return new MailSender();  
    }  
      
    public static Sender produceSms(){  
        return new SmsSender();  
    }  
}
