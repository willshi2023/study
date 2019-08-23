package org.interview.operator;

public class PlusPlus3 {
	/**
	 * i++先赋值再运算，而++i是先运算再赋值
	 * 如果出现e+++e，则取e+ ++e 或者 e++ +e都是一样的结果
	 * @param arg
	 */
	public static void main(String[] arg) {
		int a = 1;
		a = a++ + ++a; // 1+(2+1)=4
		int b = 1;
		b = ++b + b++ + b++ + b++; // (1+1)+2+3+4=11
		int c = 1;
		c = c++ + c++ + c++ + ++c; // 1+2+3+(4+1)=11
		int d = 1;
		d = ++d + ++d; // (1+1)+(2+1)=5
		int e = 1;
		e = e+++e; 
		// e+ ++e // 1+ (1+1)
		// e++ +e // 1+2
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		System.out.println("c = " + c);
		System.out.println("d = " + d);
		System.out.println("e = " + e);
	}
}
