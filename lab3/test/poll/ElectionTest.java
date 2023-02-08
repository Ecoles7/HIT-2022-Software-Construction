package poll;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Person;
import auxiliary.Voter;
import org.junit.jupiter.api.Test;
import pattern.ElectionSelectionStrategy;
import pattern.ElectionStatisticsStrategy;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

class ElectionTest {
	
	// test strategy
	//设计了一个投票活动，投票类型如下面定义，有support，waive，oppse三种票型
	//共有三位候选人，需要选出两位
	//共有三为投票人ABC 其中C的选票由于赞同的人数多余遴选人数，会作废，对这部分进行测试
	// TODO

	@Test
	void test() {
		Election MyPoll=new Election();
		Map<String,Integer> type=new HashMap<>();
		type.put("Support",1);
		type.put("Waive",0);
		type.put("Oppose",-1);
		List<Person> candidates=new ArrayList<>();
		Person p1=new Person("Nayeon",18);
		Person p2=new Person("SANA",19);
		Person p3=new Person("MINA",20);
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		MyPoll.addCandidates(candidates);
		MyPoll.setInfo("Meet_forTest", Calendar.getInstance(),new VoteType(type),2);
		Map<Voter,Double> voters=new HashMap<>();
		Voter v1=new Voter("A");
		Voter v2=new Voter("B");
		Voter v3=new Voter("C");
		voters.put(v1,1.0);
		voters.put(v2,1.0);
		voters.put(v3,1.0);
		MyPoll.addVoters(voters);
		MyPoll.addCandidates(candidates);
		//A选票:
		Set<VoteItem<Person>> set1=new HashSet<>();
		set1.add(new VoteItem<>(p1,"Support"));
		set1.add(new VoteItem<>(p2,"Waive"));
		set1.add(new VoteItem<>(p3,"Oppose"));
		Vote<Person> A_Vote=new Vote<>(set1);
		//B选票:
		Set<VoteItem<Person>> set2=new HashSet<>();
		set2.add(new VoteItem<>(p1,"Support"));
		set2.add(new VoteItem<>(p2,"Support"));
		set2.add(new VoteItem<>(p3,"Waive"));
		Vote<Person> B_Vote=new Vote<>(set2);
		//下C选票:
		Set<VoteItem<Person>> set3=new HashSet<>();
		set3.add(new VoteItem<>(p1,"Support"));
		set3.add(new VoteItem<>(p2,"Support"));
		set3.add(new VoteItem<>(p3,"Support"));
		Vote<Person> C_Vote=new Vote<>(set3);
		//************
		MyPoll.addVote(A_Vote);
		MyPoll.addVote(B_Vote);
		MyPoll.addVote(C_Vote);

		assertEquals(true,MyPoll.getVotes().contains(A_Vote));
		assertEquals(true,MyPoll.getVotes().contains(B_Vote));
		assertTrue(MyPoll.getIllegalVotes().contains(C_Vote));

		MyPoll.statistics(new ElectionStatisticsStrategy());
		MyPoll.selection(new ElectionSelectionStrategy());
		System.out.println(MyPoll.result());
	}

}
