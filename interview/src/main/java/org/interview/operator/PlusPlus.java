package org.interview.operator;

public class PlusPlus {
	/**
	 * i++先赋值再运算，而++i是先运算再赋值
	 * @param arg
	 */
	public static void main(String[] arg){
		int a = 3;
		System.out.println("The value is: "+(++a));
		int b = 2;
		System.out.println("The value is: "+(b++));
    }
}
