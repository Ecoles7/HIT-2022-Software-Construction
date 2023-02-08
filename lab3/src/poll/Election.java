package poll;

import auxiliary.Person;
import vote.Vote;
import vote.VoteItem;

import java.util.Set;

public class Election extends GeneralPollImpl<Person> implements Poll<Person> {

	//TODO
    public void addVote(Vote<Person> vote){
        if(!checkLegal(vote))
            illegalvotes.add(vote);
        int count = 0;
        Set<VoteItem<Person>> voteItems = vote.getVoteItems();
        for(VoteItem<Person> item: voteItems)
            if(item.getVoteValue().equals(voteType.findsupport()))
                count++;

        if(count > this.quantity)
            illegalvotes.add(vote);
         votes.add(vote);
    }
}
