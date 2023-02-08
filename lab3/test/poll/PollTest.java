package poll;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Person;
import auxiliary.Voter;
import org.junit.jupiter.api.Test;
import pattern.ElectionSelectionStrategy;
import pattern.ElectionStatisticsStrategy;
import pattern.StatisticsStrategy;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

class PollTest {
	
	// test strategy
	// TODO

	// test strategy
	// 首先构建一个简单的投票活动，投票选项为Support,Oppose,Waive,其值分别为1,0，-1，包含两位投票人，其投票都有效
	 //三个候选对象ABC DEF GHI 最终结果应为ABC和GHI

	@Test
	void test() {
		// 创建2个投票人
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// 设定2个投票人的权重
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// 设定投票类型
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType vt = new VoteType(types);

		// 创建候选对象：候选人
		Person p1 = new Person("ABC", 19);
		Person p2 = new Person("DEF", 20);
		Person p3 = new Person("GHI", 21);

		// 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
		VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");
		VoteItem<Person> vi12 = new VoteItem<>(p2, "Oppose");
		VoteItem<Person> vi13 = new VoteItem<>(p3, "Support");
		VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");
		VoteItem<Person> vi22 = new VoteItem<>(p2, "Waive");
		VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");

		// 创建2个投票人vr1、vr2的选票
		Set<VoteItem<Person>> vote1=new HashSet<>();
		vote1.add(vi11);
		vote1.add(vi12);
		vote1.add(vi13);
		Set<VoteItem<Person>> vote2=new HashSet<>();
		vote2.add(vi21);
		vote2.add(vi22);
		vote2.add(vi23);
		Vote<Person> rv1 = new Vote<Person>(vote1);
		Vote<Person> rv2 = new Vote<Person>(vote2);

		// 创建投票活动
		GeneralPollImpl<Person> poll = new GeneralPollImpl<>();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		poll.setInfo("Vote", Calendar.getInstance(), vt, 2);
		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		//增加候选人
		List<Person> candidates = new ArrayList<>();
		candidates.add(p1);candidates.add(p2);candidates.add(p3);
		poll.addCandidates(candidates);
		// 增加三个投票人的选票
		poll.addVote(rv1);
		poll.addVote(rv2);

		assertEquals(true,poll.getVotes().contains(rv1));
		assertEquals(true,poll.getVotes().contains(rv2));

		// 按规则计票
		poll.statistics(new ElectionStatisticsStrategy());

		// 按规则遴选
		poll.selection(new ElectionSelectionStrategy());
		// 输出遴选结果
		System.out.println(poll.result());
	}

}
