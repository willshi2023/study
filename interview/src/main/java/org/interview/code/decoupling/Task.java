package org.interview.code.decoupling;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务的模拟类
 * 
 * @author shijia
 *
 */
public class Task {
	static ConcurrentHashMap<String, Result> resultMap = new ConcurrentHashMap<String, Result>();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 根据任务类型自动生成不重复的任务名
	 * 
	 * @param taskType 任务类型
	 * @return
	 */
	public String generateTaskName(String taskType) {
		String taskName = taskType + "_" + simpleDateFormat.format(new Date()) + "_" + System.currentTimeMillis();
		return taskName;
	}

	/**
	 * 生成A任务 这里的A任务是没有返回值的，模拟的是实际情况下无法直接得到返回结果的多平台的任务，需要手动轮询结果。
	 * 这里的结果存放到了一个map中，我们需要轮询的就是这个map，实际情况下一般都是存放到数据库中。
	 * 当然如果有更好的办法的话，尽可能不要采用定期轮询的方式，这里只是一个假设中迫不得已的场景，讨论的是关于代码解耦的问题
	 * 
	 * @param aTaskName
	 * @return
	 * @throws InterruptedException
	 */
	public void generateATask(String aTaskName) throws InterruptedException {
		System.out.println("开始执行A任务");
		Thread.sleep(10_000);// 模拟任务的时间
		Integer flag = (int) (Math.random() * 3);
		if (flag <= 1) {
			System.out.println("A任务执行失败");
			Result result = new Result();
			result.setName(aTaskName);
			result.setStatus("failure");
			resultMap.put(aTaskName, result);
		} else {
			System.out.println("A任务执行成功");
			String taskType = "btask";
			String bTaskName = generateTaskName(taskType);// a任务的结果作为b任务的关键信息，这里取的是b的任务名，实际上可能不是
			Result result = new Result();
			result.setName(aTaskName);
			result.setStatus("success");
			result.setData(bTaskName);
			resultMap.put(aTaskName, result);
		}
	}

	/**
	 * 生成B任务
	 * 
	 * @param bTaskName
	 * @throws InterruptedException
	 */
	public void generateBTask(String bTaskName) throws InterruptedException {
		System.out.println("开始执行B任务");
		Thread.sleep(15_000);// 模拟任务的时间
		Integer flag = (int) (Math.random() * 4);
		if (flag <= 1) {
			System.out.println("B任务执行失败");
			Result result = new Result();
			result.setName(bTaskName);
			result.setStatus("failure");
			resultMap.put(bTaskName, result);
		} else {
			System.out.println("B任务执行成功");
			Result result = new Result();
			result.setName(bTaskName);
			result.setStatus("success");
			result.setData(UUID.randomUUID().toString());// 这里假设b任务的执行结果信息就是一个uuid
			resultMap.put(bTaskName, result);
		}
	}

	class Result {
		private String name;// 任务名
		private String status;// 任务状态
		private String data;// 任务的结果，实际情况任务的结果可能是从数据库里面查取，这里简单的就写在了这里

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

	}
}
