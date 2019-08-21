package org.interview.design.mode.factory.abstracts;

import org.interview.design.mode.factory.common.Sender;

/**
 * 抽象工厂模式通过提供工厂抽象接口，
 * 需要增加新功能的时候直接增加新的工厂类。
 * 弥补了其他工厂模式无法拓展的缺点
 * @author Administrator
 *
 */
public interface Provider {
	public Sender produce();  
}
