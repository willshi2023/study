package org.interview.design.mode.builder;

public class TestHuman implements IHuman{
	Human human;

    public TestHuman(){
        human = new Human();
    }

    @Override
    public void buildHead() {
        human.setHead("高智商的头脑");
    }

    @Override
    public void buildBody() {
        human.setBody("强壮的身体");
    }

    @Override
    public void buildHand() {
        human.setHand("灵活的小手");
    }

    @Override
    public void buildFoot() {
        human.setFoot("有点肥的脚");
    }

    @Override
    public Human createHuman() {
        return human;
    }
}
