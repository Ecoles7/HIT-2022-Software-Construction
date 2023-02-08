package pattern;

import auxiliary.Proposal;
import auxiliary.Voter;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.awt.desktop.SystemEventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BusiStatisticsStrategy implements StatisticsStrategy<Proposal> {
    @Override
    public Map<Proposal, Double> Statistics(VoteType voteType, Map<Voter,Double> voters, Set<Vote<Proposal>> votes, Set<Vote<Proposal>> illegalvotes) {
        Map<Proposal, Double> score = new HashMap<>();
        for(Vote<Proposal> vote: votes){
            if(illegalvotes.contains(vote))
                continue;// 非法票
            Voter voter = ((RealNameVote)vote).getVoter();
            double weight = voters.get(voter);  //获得该投票人的权重
            Set<VoteItem<Proposal>> voteItems = vote.getVoteItems();
            //开始计分
            for(VoteItem<Proposal> voteItem: voteItems) {
                Proposal candidate = voteItem.getCandidate();
                //初始化为0
                if(!score.containsKey(candidate))
                    score.put(candidate, 0.0);
                double value = score.get(candidate);
                //商业决策仅计算该提案获得的支持比例
                if(voteItem.getVoteValue().equals(voteType.findsupport()))
                     score.put(candidate, value + weight * 1);
            }
        }
        return score;

    }
}
