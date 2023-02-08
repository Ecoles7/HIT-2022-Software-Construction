package app;

import auxiliary.Proposal;
import auxiliary.Voter;
import pattern.BusiSelectionStrategy;
import pattern.BusiStatisticsStrategy;
import poll.BusinessVoting;
import poll.GeneralPollImpl;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

public class BusinessVotingApp {

	public static void main(String[] args) throws Exception {
		// TODO
		GeneralPollImpl<Proposal> poll = new BusinessVoting<>();

		Map<Voter, Double> voters = new HashMap<>();
		Voter v1 = new Voter("A");
		Voter v2 = new Voter("B");
		Voter v3 = new Voter("C");
		Voter v4 = new Voter("D");
		Voter v5 = new Voter("E");
		voters.put(v1, 0.05);
		voters.put(v2, 0.51);
		voters.put(v3, 0.10);
		voters.put(v4, 0.24);
		voters.put(v5, 0.20);


		poll.setInfo("proposal test", Calendar.getInstance(), new VoteType(0), 1);
		poll.addVoters(voters);

		List<Proposal> proposalList = new ArrayList<>();
		Proposal p0 = new Proposal("XXXXX", Calendar.getInstance());
		proposalList.add(p0);
		poll.addCandidates(proposalList);

		Set<VoteItem<Proposal>> supportItem = new HashSet<>(), rejectItem = new HashSet<>(), abstainItem = new HashSet<>();
		supportItem.add(new VoteItem<>(p0, "support"));
		rejectItem.add(new VoteItem<>(p0, "abstained"));
		abstainItem.add(new VoteItem<>(p0, "reject"));

		Vote<Proposal> voteA = new RealNameVote<>(v1, rejectItem);
		Vote<Proposal> voteB = new RealNameVote<>(v2, supportItem);
		Vote<Proposal> voteC = new RealNameVote<>(v3, supportItem);
		Vote<Proposal> voteD = new RealNameVote<>(v4, rejectItem);
		Vote<Proposal> voteE = new RealNameVote<>(v5, abstainItem);

		poll.addVote(voteA);
		poll.addVote(voteB);
		poll.addVote(voteC);
		poll.addVote(voteD);
		poll.addVote(voteE);


		poll.statistics(new BusiStatisticsStrategy());

		poll.selection(new BusiSelectionStrategy());

		System.out.println(poll.result());
	}
}
