package poll;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Visitor.Visitor;
import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import vote.VoteItem;
import vote.VoteType;
import vote.Vote;

public class GeneralPollImpl<C> implements Poll<C> {

	// ͶƱ�������
	private String name;
	// ͶƱ������ʱ��
	private Calendar date;
	// ��ѡ���󼯺�
	private List<C> candidates;
	// ͶƱ�˼��ϣ�keyΪͶƱ�ˣ�valueΪ���ڱ���ͶƱ����ռȨ��
	protected Map<Voter, Double> voters;
	// ��ѡ���ĺ�ѡ�����������
	protected int quantity;
	// ����ͶƱ����õ�ͶƱ���ͣ��Ϸ�ѡ����Զ�Ӧ�ķ�����
	protected VoteType voteType;
	// ����ѡƱ����
	protected Set<Vote<C>> votes;
	// ��Ʊ�����keyΪ��ѡ����valueΪ��÷�
	public Map<C, Double> statistics;
	// ��ѡ�����keyΪ��ѡ����valueΪ������λ��
	private Map<C, Double> results;
	//��¼���Ϸ�ѡƱ
	protected Set<Vote<C>> illegalvotes = new HashSet<>();
    //���ز��Ϸ�ѡƱ
	public Set<Vote<C>> getIllegalVotes() {return  new HashSet<Vote<C>>(illegalvotes);}
    //��������ѡƱ
	public Set<Vote<C>> getVotes() {return new HashSet<Vote<C>>(votes);}

	// Rep Invariants
	// name:���ȴ���һ���ַ�������ͷ�ַ�����Ϊ�ո�;
	// date:�ǿ�;
	// candidates:�б�Ԫ�ظ������ڻ����һ;
	// voters:ӳ��Ԫ�ظ������ڻ����һ;
	// quantity:���ڵ���һ����С�ڵ���candidates�б�Ԫ�ظ���������;
	// votes:����Ԫ�ظ���С�ڻ����votersԪ�ظ���;����Ԫ�طǿա�
	// Abstract Function
	// name:ͶƱ�������;data:ͶƱ������ʱ��...��rep���Ѿ�˵��
	// Safety from Rep Exposure
	// ADT�е����Զ�����private��protected���Σ����ҹ��캯����������Ա������û�н��ڲ�����ֱ�ӱ�¶���û�

	private boolean checkRep() {
		Pattern reg=Pattern.compile("\\S.*");
		Matcher match=reg.matcher(this.name);
		if(!match.matches())return false;//�жϻ������ĺϷ���
		if(date==null)return false;//ʱ��ķǿ���
		if(this.candidates==null||this.candidates.size()==0)return false;//��ѡ����������һ��
		if(this.voters.size()==0)return false;//ͶƱ��������һ��
		if(voteType==null)return false;//ͶƱ���͵ķǿ���
		if(quantity<1||quantity>candidates.size())return false;//���ͶƱ��������һ�������ܳ�����ѡ������
		return true;
	}
	protected boolean checkLegal(Vote<C> vote) {

		Set<VoteItem<C>> items = vote.getVoteItems();
		//���ѡƱ�Ƿ�Ϸ�
		if(items.size() != candidates.size()) {
			return false;
		}
		Set<C> appeared = new HashSet<>();
		for(VoteItem<C> item: items) {
			if(appeared.contains(item.getCandidate())) {
				return false;// �Ѿ����ֹ���
			}
			if(!candidates.contains(item.getCandidate())) {
				return false;// �ö����Ǻ�ѡ����
			}
			if(!voteType.checkLegality(item.getVoteValue())) {
				return false;// ���ǺϷ�ѡ��
			}
			appeared.add(item.getCandidate());
		}
		return true;
	}
    //�򵥵��ж�ѡƱ��ͶƱ�˵�������ϵ���׳���Ӧ�쳣
	protected void checkVotes(Set<Vote<C>> votes) {
		//������������,ֱ���ж�ͶƱ�ĸ������ɡ�
		if(votes.size() > voters.size()) //ѡƱ����ͶƱ��
			throw new VoteMore("There were more votes than there were voters!");// �Զ����unchecked�쳣
			// TODO
		else if (votes.size() < voters.size()) //ѡƱ����ͶƱ��
			throw new VoteLess("The number of ballots is less than the number of voters!");// �Զ����unchecked�쳣
	}


	/**
	 * ���캯��
	 */
	public GeneralPollImpl() {
		// TODO
		this.candidates=new ArrayList<>();
		this.voters=new HashMap<>();
		this.votes=new HashSet<>();
		this.statistics=new HashMap<>();
		this.results=new HashMap<>();
	}

	@Override
	public void setInfo(String name, Calendar date, VoteType type, int quantity) {
		// TODO
		this.name=name;
		this.date = Calendar.getInstance();
		this.date.setTime(date.getTime());
		this.voteType=type;
		this.quantity=quantity;
		checkRep();
	}

	@Override
	public void addVoters(Map<Voter, Double> voters) {
		// TODO
		this.voters = new HashMap<>(voters);
		checkRep();
	}

	@Override
	public void addCandidates(List<C> candidates) {
		// TODO
		this.candidates = new ArrayList<>(candidates);
	}

	@Override
	public void addVote(Vote<C> vote) {
		// �˴�Ӧ���ȼ���ѡƱ�ĺϷ��Բ���ǣ�Ϊ������չ���޸�rep
		// TODO
        boolean p=checkLegal(vote);
		if(!p)
			illegalvotes.add(vote);
		votes.add(vote);
	}

	@Override
	public void statistics(StatisticsStrategy ss) throws VoteMore,VoteLess{
		// �˴�Ӧ���ȼ�鵱ǰ��ӵ�е�ѡƱ�ĺϷ���
		checkVotes(votes);
		statistics = ss.Statistics(voteType, voters, votes, illegalvotes);

	}

	@Override
	public void selection(SelectionStrategy ss) {
		// TODO
		results = ss.selection(statistics, quantity);
	}

	@Override
	public String result() {
		// TODO
		if(results.size() == 0) return "����ͶƱδѡ����Ч����";

		Set<C> candidates = new TreeSet<>(new Comparator<C>() {
			@Override
			public int compare(C o1, C o2) {
				return (int)(results.get(o1) - results.get(o2));
			}
		});

		candidates.addAll(results.keySet());

		int rank = 0;
		StringBuilder stringBuilder = new StringBuilder(this.name + "��ͶƱ���:\n����\t��ѡ��\n");
		for(C candidate: candidates) {
			stringBuilder.append(String.format("%d\t%s\n",++rank, candidate.toString()));
		}
		return stringBuilder.toString().trim();
	}
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}

class VoteMore extends RuntimeException {
	VoteMore(String s) {
		super(s);
	}
}
class VoteLess extends RuntimeException {
	VoteLess(String s) {
		super(s);
	}
}