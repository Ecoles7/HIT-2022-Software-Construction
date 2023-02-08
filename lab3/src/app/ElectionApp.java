package app;

import java.util.*;

import auxiliary.Person;
import auxiliary.Voter;
import pattern.ElectionSelectionStrategy;
import pattern.ElectionStatisticsStrategy;
import poll.GeneralPollImpl;
import poll.Poll;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

public class ElectionApp {

	public static void main(String[] args) {

		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType vt = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("ABC", 19);
		Person p2 = new Person("DEF", 20);
		Person p3 = new Person("GHI", 21);

		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
		VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");
		VoteItem<Person> vi12 = new VoteItem<>(p2, "Oppose");
		VoteItem<Person> vi13 = new VoteItem<>(p3, "Support");
		VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");
		VoteItem<Person> vi22 = new VoteItem<>(p2, "Waive");
		VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");

		// ����2��ͶƱ��vr1��vr2��ѡƱ
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

		// ����ͶƱ�
		GeneralPollImpl<Person> poll = new GeneralPollImpl<>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		poll.setInfo("Vote", Calendar.getInstance(), vt, 2);
		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		//���Ӻ�ѡ��
		List<Person> candidates = new ArrayList<>();
		candidates.add(p1);candidates.add(p2);candidates.add(p3);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1);
		poll.addVote(rv2);

		// �������Ʊ
		poll.statistics(new ElectionStatisticsStrategy());

		// ��������ѡ
		poll.selection(new ElectionSelectionStrategy());
		// �����ѡ���
		System.out.println(poll.result());
	}

}
