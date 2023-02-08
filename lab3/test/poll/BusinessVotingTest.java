package poll;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Proposal;
import auxiliary.Voter;
import org.junit.jupiter.api.Test;
import pattern.BusiSelectionStrategy;
import pattern.BusiStatisticsStrategy;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;
//import org.testng.annotations.Test;

class BusinessVotingTest {

	// test strategy
	//模拟了一个简单的商业决策场景 共有三位股东 其股份分别为 0.4. 0.26 0.31
	//其中前两位股东同意该提案，测试三人的投票是否被正确添加到了计数选票集合
	//测试能否返回决策通过这一正确结果
	// TODO
	
	@Test
	void test() {
		GeneralPollImpl<Proposal> MyPoll = new BusinessVoting<>();
		Map<String, Integer> type = new HashMap<>();
		type.put("support", 1);
		type.put("waive", 0);
		type.put("oppose", -1);
		List<Proposal> candidates = new ArrayList<>();
		Proposal p = new Proposal("test", Calendar.getInstance());
		candidates.add(p);
		MyPoll.addCandidates(candidates);
		MyPoll.setInfo("Meet_forTest", Calendar.getInstance(), new VoteType(type), 1);
		Map<Voter, Double> voters = new HashMap<>();
		Voter v1 = new Voter("Nayeon");
		Voter v2 = new Voter("SANA");
		Voter v3 = new Voter("MINA");
		voters.put(v1, 0.43);
		voters.put(v2, 0.26);
		voters.put(v3, 0.31);
		MyPoll.addVoters(voters);
		MyPoll.addCandidates(candidates);
		//Nayeon的投票:
		Set<VoteItem<Proposal>> set1 = new HashSet<>();
		set1.add(new VoteItem<>(p, "support"));
		Vote<Proposal> Nayeon_Vote = new RealNameVote<>(v1, set1);
		//SANA的投票:
		Set<VoteItem<Proposal>> set2 = new HashSet<>();
		set2.add(new VoteItem<>(p, "support"));
		Vote<Proposal> SANA_Vote = new RealNameVote<>(v2, set2);
		//MINA的投票
		Set<VoteItem<Proposal>> set3 = new HashSet<>();
		set3.add(new VoteItem<>(p, "oppose"));
		Vote<Proposal> MINA_Vote = new RealNameVote<>(v3, set3);
		//************
		MyPoll.addVote(Nayeon_Vote);
		MyPoll.addVote(SANA_Vote);
		MyPoll.addVote(MINA_Vote);


		assertTrue(MyPoll.getVotes().contains(Nayeon_Vote));
		assertTrue(MyPoll.getVotes().contains(SANA_Vote));
		assertTrue(MyPoll.getVotes().contains(MINA_Vote));
		MyPoll.statistics(new BusiStatisticsStrategy());
		MyPoll.selection(new BusiSelectionStrategy());
		System.out.println(MyPoll.result());
	}
}
