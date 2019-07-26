package org.interview.proxy.dynamic.cglib;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class BookFacadeProxyCglib implements MethodInterceptor {
	private Object target;
	
	public Object getInstance(Object target){
		this.target=target;
		Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		//回调方法
		//enhancer.setCallbackType(this.target.getClass());
		enhancer.setCallback(this);
		//创建代理对象
		return enhancer.create();
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("before run");
		proxy.invokeSuper(obj, args);
//		Object returnvalue=proxy.invokeSuper(obj, args);
		System.out.println("after run");
		return null;
	}

	public static void main(String[] args) {
		 BookFacadeCglib target = new BookFacadeCglib();
		 BookFacadeCglib bookFacadeCglib = (BookFacadeCglib) new BookFacadeProxyCglib().getInstance(target);
		 bookFacadeCglib.addBook();
	}
}
