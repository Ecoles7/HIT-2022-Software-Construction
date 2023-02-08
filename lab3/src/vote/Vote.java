package vote;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

//immutable
public class Vote<C> {

	//增加一个新的属性id用于区分短时间内连续加入的投票
	private int id;
	static private int num = 0;
	// 缺省为“匿名”投票，即不需要管理投票人的信息

	// 一个投票人对所有候选对象的投票项集合
	private Set<VoteItem<C>> voteItems = new HashSet<>();
	// 投票时间
	private Calendar date = Calendar.getInstance();

	// Rep Invariants
	// voteItems中如果有多个投票项，投票项之间候选人两两不相等，值可以相等。data非空
	// Abstract Function
	// voteItems表示该投票人对他投过票的候选对象的投票项，date表示该选票的日期
	// Safety from Rep Exposure
	// ADT中所有属性都用private修饰，在构造函数中，采用防御式拷贝避免将属性的引用暴露给客户，并且成员函数中部分采用防御式拷贝来避免表示泄露

	private boolean checkRep() {
		if(date==null||voteItems==null)return false;
		for(VoteItem Item0:voteItems){
			int num=0;
			for(VoteItem Item1:voteItems){
				if(Item0.getCandidate().equals(Item1.getCandidate()))num++;
			}
			if(num!=1)return false;
		}
		return true;
	}

	/**
	 * 创建一个选票对象
	 * 
	 * 可以自行设计该方法所采用的参数
	 * 
	 */
	public Vote(Set<VoteItem<C>> voteItems) {
		// TODO
		this.id = ++Vote.num;
		this.voteItems.addAll(voteItems);
		checkRep();
	}

	/**
	 * 查询该选票中包含的所有投票项
	 * 
	 * @return 所有投票项
	 */
	public Set<VoteItem<C>> getVoteItems() {
		// TODO
		Set<VoteItem<C>> voteItems1 = new HashSet<>(voteItems);
		return voteItems1;
	}

	/**
	 * 一个特定候选人是否包含本选票中
	 * 
	 * @param candidate 待查询的候选人
	 * @return 若包含该候选人的投票项，则返回true，否则false
	 */
	public boolean candidateIncluded(C candidate) {
		// TODO
		for(VoteItem<C> V : voteItems){
			if(V.getCandidate()==candidate){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO
		if(obj==null)return false;
		if(!(obj instanceof Vote))return false;
		Vote that=(Vote) obj;
		if(that.getVoteItems().equals(this.getVoteItems()))return true;
		return false;
	}
}
