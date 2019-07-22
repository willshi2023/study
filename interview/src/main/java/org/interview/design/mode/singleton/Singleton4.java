package org.interview.design.mode.singleton;

public class Singleton4 {
	// 4：定义一个静态变量来存储创建好的类实例
	// 直接在这里创建类实例，只会创建一次
	private static Singleton4 instance = new Singleton4();

	// 1：私有化构造方法，好在内部控制创建实例的数目
	private Singleton4() {
	}

	// 2：定义一个方法来为客户端提供类实例
	// 3：这个方法需要定义成类方法，也就是要加static
	// 这个方法里面就不需要控制代码了
	public static Singleton4 getInstance() {
		// 5：直接使用已经创建好的实例
		return instance;
	}
}
