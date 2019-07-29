package org.interview.generic;

public class TestGenericWildcardCharacter1 {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		GenericWildcardCharacter<Number> genericWildcardCharacter1 = new GenericWildcardCharacter<Number>(123);
		GenericWildcardCharacter<Integer> genericWildcardCharacter2 = new GenericWildcardCharacter<Integer>(456);
		showKeyValue(genericWildcardCharacter1);
//		showKeyValue(genericWildcardCharacter2);
	}
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 测试泛型能否有子类关系
	 * @param genericWildcardCharacter1
	 */
	private static void showKeyValue(GenericWildcardCharacter<Number> genericWildcardCharacter1) {
		System.out.println("key is "+genericWildcardCharacter1.getKey());
	}
}
