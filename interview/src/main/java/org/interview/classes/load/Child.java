package org.interview.classes.load;

public class Child extends Parent {
	static String childName = "Child";

	static {
		System.out.println("child static block");
	}

	{
		System.out.println("child block");
	}

	public Child() {
		System.out.println("child constructor");
	}

	public static void main(String[] args) {
		new Child();
	}
}
