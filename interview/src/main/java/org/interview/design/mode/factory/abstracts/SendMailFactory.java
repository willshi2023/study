package org.interview.design.mode.factory.abstracts;

import org.interview.design.mode.factory.common.MailSender;
import org.interview.design.mode.factory.common.Sender;

public class SendMailFactory implements Provider {

	@Override
	public Sender produce() {
		return new MailSender();  
	}

}
