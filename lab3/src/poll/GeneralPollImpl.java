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

	// 投票活动的名称
	private String name;
	// 投票活动发起的时间
	private Calendar date;
	// 候选对象集合
	private List<C> candidates;
	// 投票人集合，key为投票人，value为其在本次投票中所占权重
	protected Map<Voter, Double> voters;
	// 拟选出的候选对象最大数量
	protected int quantity;
	// 本次投票拟采用的投票类型（合法选项及各自对应的分数）
	protected VoteType voteType;
	// 所有选票集合
	protected Set<Vote<C>> votes;
	// 计票结果，key为候选对象，value为其得分
	public Map<C, Double> statistics;
	// 遴选结果，key为候选对象，value为其排序位次
	private Map<C, Double> results;
	//记录不合法选票
	protected Set<Vote<C>> illegalvotes = new HashSet<>();
    //返回不合法选票
	public Set<Vote<C>> getIllegalVotes() {return  new HashSet<Vote<C>>(illegalvotes);}
    //返回所有选票
	public Set<Vote<C>> getVotes() {return new HashSet<Vote<C>>(votes);}

	// Rep Invariants
	// name:长度大于一的字符串，开头字符不能为空格;
	// date:非空;
	// candidates:列表元素个数大于或等于一;
	// voters:映射元素个数大于或等于一;
	// quantity:大于等于一并且小于等于candidates列表元素个数的整数;
	// votes:集合元素个数小于或等于voters元素个数;其余元素非空。
	// Abstract Function
	// name:投票活动的名称;data:投票活动发起的时间...在rep中已经说明
	// Safety from Rep Exposure
	// ADT中的属性都采用private或protected修饰，并且构造函数及其他成员函数都没有将内部引用直接暴露给用户

	private boolean checkRep() {
		Pattern reg=Pattern.compile("\\S.*");
		Matcher match=reg.matcher(this.name);
		if(!match.matches())return false;//判断会议名的合法性
		if(date==null)return false;//时间的非空性
		if(this.candidates==null||this.candidates.size()==0)return false;//候选对象至少有一个
		if(this.voters.size()==0)return false;//投票人至少有一个
		if(voteType==null)return false;//投票类型的非空性
		if(quantity<1||quantity>candidates.size())return false;//最大投票数量至少一个，不能超过候选人数量
		return true;
	}
	protected boolean checkLegal(Vote<C> vote) {

		Set<VoteItem<C>> items = vote.getVoteItems();
		//检查选票是否合法
		if(items.size() != candidates.size()) {
			return false;
		}
		Set<C> appeared = new HashSet<>();
		for(VoteItem<C> item: items) {
			if(appeared.contains(item.getCandidate())) {
				return false;// 已经出现过了
			}
			if(!candidates.contains(item.getCandidate())) {
				return false;// 该对象不是候选对象
			}
			if(!voteType.checkLegality(item.getVoteValue())) {
				return false;// 不是合法选项
			}
			appeared.add(item.getCandidate());
		}
		return true;
	}
    //简单的判断选票和投票人的数量关系并抛出相应异常
	protected void checkVotes(Set<Vote<C>> votes) {
		//对于匿名而言,直接判断投票的个数即可。
		if(votes.size() > voters.size()) //选票多于投票人
			throw new VoteMore("There were more votes than there were voters!");// 自定义的unchecked异常
			// TODO
		else if (votes.size() < voters.size()) //选票少于投票人
			throw new VoteLess("The number of ballots is less than the number of voters!");// 自定义的unchecked异常
	}


	/**
	 * 构造函数
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
		// 此处应首先检查该选票的合法性并标记，为此需扩展或修改rep
		// TODO
        boolean p=checkLegal(vote);
		if(!p)
			illegalvotes.add(vote);
		votes.add(vote);
	}

	@Override
	public void statistics(StatisticsStrategy ss) throws VoteMore,VoteLess{
		// 此处应首先检查当前所拥有的选票的合法性
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
		if(results.size() == 0) return "本次投票未选出有效对象";

		Set<C> candidates = new TreeSet<>(new Comparator<C>() {
			@Override
			public int compare(C o1, C o2) {
				return (int)(results.get(o1) - results.get(o2));
			}
		});

		candidates.addAll(results.keySet());

		int rank = 0;
		StringBuilder stringBuilder = new StringBuilder(this.name + "的投票结果:\n排名\t候选者\n");
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