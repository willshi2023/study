package org.interview.design.mode.builder;

public class Human {
	// 构造一个人的基本属性
	private String head;
	private String body;
	private String hand;
	private String foot;

	// 这里用于打印通过我们造出一个人的基本属性
	public void show() {
		System.out.println(head);
		System.out.println(body);
		System.out.println(hand);
		System.out.println(foot);
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getHand() {
		return hand;
	}

	public void setHand(String hand) {
		this.hand = hand;
	}

	public String getFoot() {
		return foot;
	}

	public void setFoot(String foot) {
		this.foot = foot;
	}
}
