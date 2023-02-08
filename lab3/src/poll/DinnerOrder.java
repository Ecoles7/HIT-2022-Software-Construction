package poll;

import auxiliary.Dish;
import auxiliary.Proposal;
import auxiliary.Voter;
import pattern.StatisticsStrategy;
import vote.RealNameVote;
import vote.Vote;

import java.util.HashSet;
import java.util.Set;

public class DinnerOrder<Dish> extends GeneralPollImpl<Dish> implements Poll<Dish> {
	//TODO
    public void addVote(Vote<Dish> vote){
        //检查是否为实名，不实名抛出uncheck异常提示用户
        if(!(vote instanceof RealNameVote)) {
            throw new NoNameExp("Please vote in real name");
        }
        boolean p=checkLegal(vote) && voters.containsKey( ((RealNameVote) vote).getVoter());
        if(p)
            votes.add(vote);
    }
    @Override
    public  void statistics(StatisticsStrategy ss){
        boolean p=true;
        Set<Voter> votedVoters = new HashSet<>(); //记录已经投票了的投票人
        Set<Voter> voteMoreVoters = new HashSet<>(); //记录投了多次票的投票人，其所有投票无效
        for(Vote<Dish> vote: votes) {
            Voter voter = ((RealNameVote) vote).getVoter();
            if(!voters.containsKey(voter)) {
                illegalvotes.add(vote);// 不是这次投票的投票人，非法投票
                continue;
            }
            if(votedVoters.contains(voter)) {
                voteMoreVoters.add(voter);// 多次投票 加入对应集合
            }
            else {
                votedVoters.add(voter);// 加入投了票的投票人
            }
        }
        if(!votedVoters.equals(voters.keySet()))  //判断是否所有人都已投票
            p=false;
        if(p){  //开始计票
            for(Vote<Dish> vote: votes) {
                Voter voter = ((RealNameVote) vote).getVoter();
                if(voteMoreVoters.contains(voter)) // 投票人投了多次票
                    illegalvotes.add(vote);
            }
        }
        statistics = ss.Statistics(voteType, voters, votes, illegalvotes);
    }
}

