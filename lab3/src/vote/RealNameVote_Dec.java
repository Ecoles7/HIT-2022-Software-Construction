package vote;

import auxiliary.Voter;

import java.util.Set;

public class RealNameVote_Dec<C> {
    private Vote<C> vote;
    private Voter voter;
    public RealNameVote_Dec(Vote<C> vote,Voter voter){
        this.vote=vote;
        this.voter=voter;
    }
    public Set<VoteItem<C>> getVoteItems(){
        return this.vote.getVoteItems();
    }
    public Voter getVoter(){
        return this.voter;
    }
    @Override
    public boolean equals(Object obj){
        if(obj==null||!(obj instanceof RealNameVote_Dec))return false;
        RealNameVote_Dec that=(RealNameVote_Dec) obj;
        if(!that.getVoter().equals(this.getVoter())||!that.getVoteItems().equals(this.getVoteItems()))return false;
        return true;
    }


}
