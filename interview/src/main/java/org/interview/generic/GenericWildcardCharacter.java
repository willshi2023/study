package org.interview.generic;

/**
 * 泛型通配符
 * @author Administrator
 *
 */
public class GenericWildcardCharacter<T> {
	private T key;

	public void showKeyValue1(GenericWildcardCharacter<T> obj){
	    System.out.println("泛型测试key value is " + obj.getKey());
	}
	public GenericWildcardCharacter(T key) { //泛型构造方法形参key的类型也为T，T的类型由外部指定
        this.key = key;
    }

	public T getKey() {
		return key;
	}
}
