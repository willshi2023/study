package org.interview.design.mode.builder;

public interface IHuman {
	void buildHead();//构造人的头
    void buildBody();//构造人的身体
    void buildHand();//构造人的手
    void buildFoot();//构造人的脚
    Human createHuman();//返回一个我们想要构造的人
}
