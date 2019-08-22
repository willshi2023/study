package org.interview.design.mode.builder;

public class Test {
	public static void main(String[] args) {
        Director director = new Director();
        Human human = director.create(new FatBuildHuman());
        human.show();
        Human human1 = director.create(new TestHuman());
        human1.show();
    }
}
