package org.interview.design.mode.singleton;

//饱汉
// UnThreadSafe
public class Singleton1 {
	  private static Singleton1 singleton = null;
	  private Singleton1() {
	  }
	  public static Singleton1 getInstance() {
	    if (singleton == null) {
	      singleton = new Singleton1();
	    }
	    return singleton;
	  }
}
