package org.interview.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 * @author Administrator
 *
 */
public class BookFacadeProxy implements InvocationHandler {
	private Object target;

	public Object bind(Object target){
		this.target=target;
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), (Class<?>[])target.getClass().getGenericInterfaces(),this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("事物开始执行");
		method.invoke(target, args);
		System.out.println("事情结束");
		return null;
	}

	public static void main(String[] args) {
		BookFacade target = new BookFacadeImpl();
		BookFacade bookFacadeProxy = (BookFacade) new BookFacadeProxy().bind(target);
		bookFacadeProxy.addBook();
	}
}
