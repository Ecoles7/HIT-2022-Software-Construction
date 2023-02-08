package Visitor;

import poll.GeneralPollImpl;

public class VoteLegalVisitor<C> implements Visitor<C> {

    private double data;
    @Override
    public void visit(GeneralPollImpl<C> poll) {
        data =  1.0 - 1.0 * poll.getIllegalVotes().size() / poll.getVotes().size();
    }

    @Override
    public double getData() {
        return this.data;
    }
}
