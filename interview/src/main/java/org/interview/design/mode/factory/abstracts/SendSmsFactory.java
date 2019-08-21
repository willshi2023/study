package org.interview.design.mode.factory.abstracts;

import org.interview.design.mode.factory.common.Sender;
import org.interview.design.mode.factory.common.SmsSender;

public class SendSmsFactory implements Provider {

	@Override
	public Sender produce() {
		return new SmsSender(); 
	}

}
