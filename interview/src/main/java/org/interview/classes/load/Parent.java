package org.interview.classes.load;

public class Parent {
	static String name = "Parent";

	static {
		System.out.println("parent static block");
	}

	{
		System.out.println("parent block");
	}

	public Parent() {
		System.out.println("parent constructor");
	}
}
