package pattern;

import auxiliary.Dish;
import auxiliary.Person;
import auxiliary.Voter;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DishStatisticsStrategy implements StatisticsStrategy<Dish> {
    @Override
    public Map<Dish, Double> Statistics(VoteType voteType, Map<Voter, Double> voters, Set<Vote<Dish>> votes, Set<Vote<Dish>> illegalvotes) {
        Map<Dish, Double> score = new HashMap<>();
        for(Vote<Dish> vote: votes) {
            if(illegalvotes.contains(vote))
                continue;// 非法票
            Voter voter = ((RealNameVote)vote).getVoter();// 向下转型，获取投票人
            double weight = voters.get(voter);// 该投票人对应权值

            //该投票人投的各票
            Set<VoteItem<Dish>> voteItems = vote.getVoteItems();
            for(VoteItem<Dish> voteItem: voteItems) {
                Dish candidate = voteItem.getCandidate();
                //如果还没统计到，初始化值为0
                if(!score.containsKey(candidate)) score.put(candidate, 0.0);
                double value = score.get(candidate);
                score.put(candidate, value + weight * voteType.getScoreByOption(voteItem.getVoteValue()));
            }
        }

        return score;
    }
}
