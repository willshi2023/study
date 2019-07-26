package org.interview.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * 演示volatile关键字无法确保原子性
 * @author Administrator
 *
 */
public class Volatile {
	private static volatile int count = 0;
	private static int countNumber = 100000;
	private static CountDownLatch countDownLatch = new CountDownLatch(countNumber);
	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<countNumber;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					count++;
					countDownLatch.countDown();
				}
			}).start();
		}
		countDownLatch.await();
		System.out.println("最终的结果："+count);
	}
}
