package org.interview.generic;

public class TestGenericWildcardCharacter2 {
	public static void main(String[] args) {
		GenericWildcardCharacter<Number> genericWildcardCharacter1 = new GenericWildcardCharacter<Number>(123);
		GenericWildcardCharacter<Integer> genericWildcardCharacter2 = new GenericWildcardCharacter<Integer>(456);
		showKeyValue(genericWildcardCharacter1);
		showKeyValue(genericWildcardCharacter2);
//		GenericWildcardCharacter<Integer> genericWildcardCharacter3 = new GenericWildcardCharacter<Integer>("789");
	}
	
	
	
	
	
	

	/**
	 * 测试泛型能否有子类关系
	 * @param genericWildcardCharacter1
	 */
	private static void showKeyValue(GenericWildcardCharacter<? extends Number> genericWildcardCharacter1) {
		System.out.println("key is "+genericWildcardCharacter1.getKey());
	}
}
