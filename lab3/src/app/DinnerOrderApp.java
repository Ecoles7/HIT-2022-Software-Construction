package app;

import auxiliary.Dish;
import auxiliary.Voter;
import pattern.DishSelectionStrategy;
import pattern.DishStatisticsStrategy;
import poll.DinnerOrder;
import poll.GeneralPollImpl;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

public class DinnerOrderApp {
	
	public static void main(String[] args) throws Exception {
		// TODO
		GeneralPollImpl<Dish> poll = new DinnerOrder<>();

		Map<Voter, Double> voters = new HashMap<>();
		Voter v1 = new Voter("GRANDPA");
		Voter v2 = new Voter("FATHER");
		Voter v3 = new Voter("MOTHER");
		Voter v4 = new Voter("SON");
		voters.put(v1, 4.0);
		voters.put(v2, 1.0);
		voters.put(v3, 2.0);
		voters.put(v4, 2.0);

		poll.setInfo("DINNER", Calendar.getInstance(), new VoteType("\"Like\"(2)|\"DLie\"(0)|\"Ntrl\"(1)"), 4);
		poll.addVoters(voters);

		List<Dish> dishList = new ArrayList<>();
		Dish A = new Dish("A", 1);
		Dish B = new Dish("B", 2);
		Dish C = new Dish("C", 3);
		Dish D = new Dish("D", 4);
		Dish E = new Dish("E", 5);
		Dish F = new Dish("F", 6);

		dishList.add(A);
		dishList.add(B);
		dishList.add(C);
		dishList.add(D);
		dishList.add(E);
		dishList.add(F);
		poll.addCandidates(dishList);

		Set<VoteItem<Dish>> item1 = new HashSet<>(), item2 = new HashSet<>(), item3 = new HashSet<>(), item4 = new HashSet<>();
		item1.add(new VoteItem<>(A, "Like"));item1.add(new VoteItem<>(B, "Like"));
		item1.add(new VoteItem<>(C, "Ntrl"));item1.add(new VoteItem<>(D, "Ntrl"));
		item1.add(new VoteItem<>(E, "DLie"));item1.add(new VoteItem<>(F, "DLie"));

		item2.add(new VoteItem<>(A, "Ntrl"));item2.add(new VoteItem<>(B, "Like"));
		item2.add(new VoteItem<>(C, "Like"));item2.add(new VoteItem<>(D, "Like"));
		item2.add(new VoteItem<>(E, "DLie"));item2.add(new VoteItem<>(F, "Like"));

		item3.add(new VoteItem<>(A, "Like"));item3.add(new VoteItem<>(B, "DLie"));
		item3.add(new VoteItem<>(C, "DLie"));item3.add(new VoteItem<>(D, "DLie"));
		item3.add(new VoteItem<>(E, "Like"));item3.add(new VoteItem<>(F, "DLie"));

		item4.add(new VoteItem<>(A, "Like"));item4.add(new VoteItem<>(B, "Ntrl"));
		item4.add(new VoteItem<>(C, "Like"));item4.add(new VoteItem<>(D, "Like"));
		item4.add(new VoteItem<>(E, "Like"));item4.add(new VoteItem<>(F, "DLie"));

		Vote<Dish> vote1 = new RealNameVote<>(v1, item1);
		Vote<Dish> vote2 = new RealNameVote<>(v2, item2);
		Vote<Dish> vote3 = new RealNameVote<>(v3, item3);
		Vote<Dish> vote4 = new RealNameVote<>(v4, item4);

		poll.addVote(vote1);
		poll.addVote(vote2);
		poll.addVote(vote3);
		poll.addVote(vote4);

		poll.statistics(new DishStatisticsStrategy());

		poll.selection(new DishSelectionStrategy());

		System.out.println(poll.result());
	}
}
