package org.interview.design.mode.singleton;

import java.io.Serializable;

public class Singleton3 implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 对保存实例的变量添加volatile的修饰
     */
    private volatile static Singleton3 instance = null;
    private Singleton3(){
    }
    public static Singleton3 getInstance(){
//先检查实例是否存在，如果不存在才进入下面的同步块
        if(instance == null){
//同步块，线程安全的创建实例
            synchronized(Singleton3.class){
//再次检查实例是否存在，如果不存在才真的创建实例
                if(instance == null){
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}
