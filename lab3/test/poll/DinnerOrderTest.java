package poll;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Dish;
import auxiliary.Voter;
import org.junit.jupiter.api.Test;
import pattern.DishSelectionStrategy;
import pattern.DishStatisticsStrategy;
import vote.RealNameVote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

class DinnerOrderTest {

	// test strategy
	//模拟了一个简单的聚会点餐场景，共有三种票型 Like Dlke Ntrl
	//共有A B C D E五道菜 需要选出三种
	//投票人共有三个 分别是GRANDPA FATHER SON 三人的投票均有效，测试其选票是否被正常添加到计票选票集合
	// TODO

	@Test
	void test() {
		DinnerOrder MyPoll=new DinnerOrder();
		Map<String,Integer> type=new HashMap<>();
		type.put("Like",2);
		type.put("Dlke",0);
		type.put("Ntrl",1);
		List<Dish> candidates=new ArrayList<>();
		Dish d1=new Dish("A",24);
		Dish d2=new Dish("B",13);
		Dish d3=new Dish("C",18);
		Dish d4=new Dish("D",28);
		Dish d5=new Dish("E",15);
		candidates.add(d1);
		candidates.add(d2);
		candidates.add(d3);
		candidates.add(d4);
		candidates.add(d5);
		MyPoll.addCandidates(candidates);
		MyPoll.setInfo("Meet_forTest", Calendar.getInstance(),new VoteType(type),3);
		Map<Voter,Double> voters=new HashMap<>();
		Voter v1=new Voter("GRANDPA");
		Voter v2=new Voter("FATHER");
		Voter v3=new Voter("SON");
		voters.put(v1,4.0);
		voters.put(v2,2.0);
		voters.put(v3,1.0);
		MyPoll.addVoters(voters);
		MyPoll.addCandidates(candidates);
		//下面是爷爷的点餐:
		Set<VoteItem<Dish>> set1=new HashSet<>();
		set1.add(new VoteItem<>(d1,"Like"));
		set1.add(new VoteItem<>(d2,"Like"));
		set1.add(new VoteItem<>(d3,"Ntrl"));
		set1.add(new VoteItem<>(d4,"Ntrl"));
		set1.add(new VoteItem<>(d5,"Dlke"));
		RealNameVote<Dish> san_Vote=new RealNameVote<Dish>(v1,set1);
		//下面是爸爸的点餐:
		Set<VoteItem<Dish>> set2=new HashSet<>();
		set2.add(new VoteItem<>(d1,"Ntrl"));
		set2.add(new VoteItem<>(d2,"Like"));
		set2.add(new VoteItem<>(d3,"Like"));
		set2.add(new VoteItem<>(d4,"Like"));
		set2.add(new VoteItem<>(d5,"Dlke"));
		RealNameVote<Dish> si_Vote=new RealNameVote<>(v2,set2);
		//下面是儿子的点餐:
		Set<VoteItem<Dish>> set3=new HashSet<>();
		set3.add(new VoteItem<>(d1,"Like"));
		set3.add(new VoteItem<>(d2,"Ntrl"));
		set3.add(new VoteItem<>(d3,"Like"));
		set3.add(new VoteItem<>(d4,"Like"));
		set3.add(new VoteItem<>(d5,"Like"));
		RealNameVote<Dish> wu_Vote=new RealNameVote<>(v3,set3);
		//************
		MyPoll.addVote(san_Vote);
		MyPoll.addVote(si_Vote);
		MyPoll.addVote(wu_Vote);

		assertEquals(true,MyPoll.getVotes().contains(san_Vote));
		assertEquals(true,MyPoll.getVotes().contains(si_Vote));
		assertEquals(true,MyPoll.getVotes().contains(wu_Vote));
		MyPoll.statistics(new DishStatisticsStrategy());
		MyPoll.selection(new DishSelectionStrategy());
		System.out.println(MyPoll.result());
	}

}
