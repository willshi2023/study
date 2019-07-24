package org.interview.data.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * hashMap的解读
 * @author Administrator
 *
 */
public class TestHashMap {
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String,Integer>();
		for(int i=0;i<10000;i++) {
			map.put(""+i, i);
		}
		System.out.println("程序完成");
	}
}
