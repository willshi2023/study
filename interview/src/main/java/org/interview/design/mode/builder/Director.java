package org.interview.design.mode.builder;

/**
 * 这个director，就是来执行我们刚才的造人动作，可以简单理解为 指挥员 。
 * 是构建者模式的精髓
 * @author Administrator
 *
 */
public class Director {
	public Human create(IHuman iHuman){//
        iHuman.buildHead();
        iHuman.buildBody();
        iHuman.buildHand();
        iHuman.buildFoot();
        return iHuman.createHuman();
    }
}
