package org.interview.design.mode.singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 测试 单例的序列化 这里对比的是双检锁和枚举类的序列化
 * 
 * @author Administrator
 *
 */
public class TestSerialization {
	public static void main(String[] args) {
//		Singleton3 singleton3 = Singleton3.getInstance();
//		Object singleton3S = readAndwriteObject(singleton3);
//		System.out.println("双检索单例的序列化结果是否一致？"+singleton3.equals(singleton3S));
		Singleton6 singleton6 = Singleton6.instance;
		Object singleton6S = readAndwriteObject(singleton6);
		System.out.println("枚举类单例的序列化结果是否一致？"+singleton6.equals(singleton6S));
	}

	private static <T> Object readAndwriteObject(T obj) {
		try {
			String fileName = String.valueOf(obj.hashCode());
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			fos.close();
			oos.close();
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			T objS = (T) ois.readObject();
			fis.close();
			ois.close();
			file.delete();
			return objS;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
