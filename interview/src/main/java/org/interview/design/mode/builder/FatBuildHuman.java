package org.interview.design.mode.builder;

public class FatBuildHuman implements IHuman {
	Human human;

    public FatBuildHuman (){
        human =new Human();
    }

    @Override
    public void buildHead() {
        human.setHead("胖人的头");
    }

    @Override
    public void buildBody() {
        human.setBody("胖人的身体");
    }

    @Override
    public void buildHand() {
        human.setHand("胖人的手");
    }

    @Override
    public void buildFoot() {
        human.setFoot("胖人的脚");
    }

    @Override
    public Human createHuman() {
        return human;//返回我们构造的人
    }


}
