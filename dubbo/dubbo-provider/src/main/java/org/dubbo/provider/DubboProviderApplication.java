package org.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboProviderApplication {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"dubbo-provider.xml"});
        context.start();
        System.in.read(); // 按任意键退出
    }
}
