package pattern;

import auxiliary.Voter;
import vote.Vote;
import vote.VoteType;

import java.util.Map;
import java.util.Set;

public interface StatisticsStrategy<C> {
	// TODO
    public Map<C,Double> Statistics(VoteType voteType, Map<Voter,Double> voters, Set<Vote<C>> votes, Set<Vote<C>> illegalvotes);

}
