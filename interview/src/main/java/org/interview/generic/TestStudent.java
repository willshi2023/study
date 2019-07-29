package org.interview.generic;

/**
 * 用于测试泛型方法
 * @author Administrator
 *
 */
public class TestStudent {
	private String name;
	private Integer age;
	
	public TestStudent() {
		this.name = "张三";
		this.age = 18;
	}
	
	public TestStudent(String name,Integer age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "TestStudent [name=" + name + ", age=" + age + "]";
	}
	
}
