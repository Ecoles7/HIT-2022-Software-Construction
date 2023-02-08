package vote;

import auxiliary.Voter;

import java.util.Set;

//immutable
public class RealNameVote<C> extends Vote<C>{
	
	//Õ∂∆±»À
	private Voter voter;
	
	public RealNameVote(Voter voter, Set<VoteItem<C>> voteItems) {
		// TODO
		super(voteItems);
		this.voter = voter;
	}
	
	public Voter getVoter() {
		return this.voter;
	}
}
