package Visitor;

import poll.GeneralPollImpl;

import java.util.Map;

public interface Visitor<C> {
    //直接对投票进行访问,进行信息统计
    public void visit(GeneralPollImpl<C> poll);

    //获取统计信息
    public double getData();
}


