package org.interview.generic;

/*
 * 泛型方法类
 */
public class GenericMethod2 {
	/**
	 * 该泛型定义一个泛型的变量，然后可以拿来作为参数，表示传入任意的类型，但是该类型不像object,传入什么类型返回也要是什么类型
	 * @param <T>
	 * @param t
	 */
	public <T> T showClass(T t) {
		System.out.println(t.toString());
		return t;
	}
	
	/**
	 * 由于该泛型方法没有限定返回值，所以此时传入的泛型T和object其实效果是一样的
	 * @param <T>
	 * @param t
	 */
	public <T> void showClass2(T t) {
		System.out.println(t.toString());
	}
	
	public static void main(String[] args) {
		GenericMethod2 genericMethod2 = new GenericMethod2();
		TestStudent testStudent = genericMethod2.showClass(new TestStudent());
		System.out.println(testStudent);
		genericMethod2.showClass2(new TestStudent("李四", 15));
	}
}
