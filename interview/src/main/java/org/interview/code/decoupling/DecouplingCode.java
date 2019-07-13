package org.interview.code.decoupling;

import java.util.concurrent.LinkedBlockingQueue;

import org.interview.code.decoupling.Task.Result;

import com.alibaba.fastjson.JSONObject;

/**
 * 解耦的代码
 * @author shijia
 *
 */
public class DecouplingCode {
	LinkedBlockingQueue<JSONObject> linkedBlockingQueue = new LinkedBlockingQueue<JSONObject>();
	
	public static void main(String[] args) {
		Task task = new Task();
		String taskType="atask";
		String aTaskName = task.generateTaskName(taskType);
		DecouplingCode decouplingCode = new DecouplingCode();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				decouplingCode.scheduleTask(task,taskType,aTaskName);
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						JSONObject jsonObject = decouplingCode.linkedBlockingQueue.take();
						String taskType = jsonObject.getString("taskType");
						if(taskType.equals("atask")) {
							Result aTaskResult = (Result) jsonObject.get("result");
							String aTaskStatus = aTaskResult.getStatus();
							if(aTaskStatus.contains("success")) {
								writeTaskStatus("atask",aTaskStatus);
								String bTaskName = aTaskResult.getData();
								decouplingCode.scheduleTask(task,"btask",bTaskName);
							}else if(aTaskStatus.contains("failure")) {
								writeTaskStatus("atask",aTaskStatus);
								new Thread(new Runnable() {
									
									@Override
									public void run() {
										decouplingCode.scheduleTask(task,taskType,aTaskName);
									}
								}).start();
							}else {
								System.out.println("未知的状态："+aTaskStatus);
							}
						}else if(taskType.equals("btask")) {
							Result bTaskResult = (Result) jsonObject.get("result");
							String bTaskStatus = bTaskResult.getStatus();
							String bTaskName = bTaskResult.getName();
							if(bTaskStatus.equals("success")) {
								writeTaskStatus("btask", bTaskStatus);
								System.out.println("将B任务的结果写入到数据库");
							}else if(bTaskStatus.equals("failure")) {
								writeTaskStatus("btask", bTaskStatus);
								new Thread(new Runnable() {
									
									@Override
									public void run() {
										decouplingCode.scheduleTask(task,taskType,bTaskName);
									}
								}).start();
							}else {
								System.out.println("未知的状态："+bTaskStatus);
							}
							break;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	

	/**
	 * 定时轮询任务结果
	 * @param task
	 * @param taskType
	 * @param taskName
	 */
	private void scheduleTask(Task task,String taskType,String taskName) {
		try {
			if(taskType.equals("atask")) {
				task.generateATask(taskName);
			}else if(taskType.equals("btask")) {
				task.generateBTask(taskName);
			}else {
				System.err.println("未知的任务类型");
			}
			while(true) {
				if(Task.resultMap.containsKey(taskName)) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("taskType", taskType);
					Result result = Task.resultMap.get(taskName);
					jsonObject.put("result", result);
					linkedBlockingQueue.add(jsonObject);
					break;
				}
				Thread.sleep(10_000);//设定轮询任务为10秒一次
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void writeTaskStatus(String taskType, String status) {
		System.out.println("任务类型："+taskType+"状态为"+status+"，写入数据库成功！");
	}
}
