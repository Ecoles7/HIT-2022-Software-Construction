package pattern;

import auxiliary.Dish;
import auxiliary.Person;
import auxiliary.Proposal;
import auxiliary.Voter;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ElectionStatisticsStrategy implements StatisticsStrategy<Person> {
    @Override
    public Map<Person, Double> Statistics(VoteType voteType, Map<Voter, Double> voters, Set<Vote<Person>> votes, Set<Vote<Person>> illegalvotes) {
        Map<Person, Double> score = new HashMap<>();
        for (Vote<Person> vote : votes) {
            if(illegalvotes.contains(vote))
                continue;// 非法票
            Set<VoteItem<Person>> voteItems = vote.getVoteItems();
            //开始计分
            for (VoteItem<Person> voteItem : voteItems) {
                Person candidate = voteItem.getCandidate();
                //初始化为0
                if (!score.containsKey(candidate))
                    score.put(candidate, 0.0);
                double value = score.get(candidate);
                //商业决策仅计算该提案获得的支持票数
                if (voteItem.getVoteValue().equals(voteType.findsupport()))
                     score.put(candidate, value + 1);
            }
        }
        return score;
    }
}
