package org.interview.operator;

public class PlusPlus2 {
	/**
	 * i++先赋值再运算，而++i是先运算再赋值
	 * @param arg
	 */
	public static void main(String[] arg){
        int x = 0;
        int y = 0;
        x = x++;
        x = x++;
        x = x++;
        x = x++;
        x = x++;
        System.out.println("now: x = " + x);
        y = ++y;
        y = ++y;
        y = ++y;
        y = ++y;
        y = ++y;
        System.out.println("now: y = " + y);
    }
}
