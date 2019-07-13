package org.interview.code.decoupling;

import org.interview.code.decoupling.Task.Result;

/**
 * 未解耦的代码
 * 未解耦的代码由两个极度相似的代码块组成，他们分别去做了定时轮询任务，只不过定时轮询的内容是不同的，轮询的模块实际上是可以解耦的
 * @author shijia
 *
 */
public class CommonCode {
	public static void main(String[] args) {
		Task task = new Task();
		String taskType="atask";
		String aTaskName = task.generateTaskName(taskType);
		scheduleTaskA(task, aTaskName);
	}

	/**
	 * 调度任务A
	 * @param task
	 * @param aTaskName
	 */
	public static void scheduleTaskA(Task task, String aTaskName) {
		try {
			task.generateATask(aTaskName);
			while(true) {
				if(Task.resultMap.containsKey(aTaskName)) {
					Result aTaskResult = Task.resultMap.get(aTaskName);
					String aTaskStatus = aTaskResult.getStatus();
					if(aTaskStatus.contains("success")) {
						writeTaskStatus("atask",aTaskStatus);
						String bTaskName = aTaskResult.getData();
						sheduleTaskB(task, bTaskName);
					}else if(aTaskStatus.contains("failure")) {
						writeTaskStatus("atask",aTaskStatus);
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								scheduleTaskA(task, aTaskName);
							}
						}).start();
					}else {
						System.out.println("未知的状态："+aTaskStatus);
					}
					break;
				}
				Thread.sleep(10_000);//设定轮询任务为10秒一次
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 调度任务
	 * @param task
	 * @param bTaskName
	 */
	public static void sheduleTaskB(Task task,  String bTaskName)  {
		try {
			task.generateBTask(bTaskName);
			while(true) {
				if(Task.resultMap.containsKey(bTaskName)) {
					Result result = Task.resultMap.get(bTaskName);
					String bTaskStatus = result.getStatus();
					if(bTaskStatus.equals("success")) {
						writeTaskStatus("btask", bTaskStatus);
						System.out.println("将B任务的结果写入到数据库");
					}else if(bTaskStatus.equals("failure")) {
						writeTaskStatus("btask", bTaskStatus);
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								sheduleTaskB(task, bTaskName);
							}
						}).start();
					}else {
						System.out.println("未知的状态："+bTaskStatus);
					}
					break;
				}
				Thread.sleep(10_000);//设定轮询任务为10秒一次
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 手动模拟的一个写入数据库状态的函数
	 * 这里两个任务一般是不同的数据库
	 * @param string
	 * @param status
	 */
	private static void writeTaskStatus(String taskType, String status) {
		System.out.println("任务类型："+taskType+"状态为"+status+"，写入数据库成功！");
	}
}
