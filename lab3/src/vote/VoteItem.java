package vote;

//immutable
public class VoteItem<C> {

	// 本投票项所针对的候选对象
	private C candidate;
	// 对候选对象的具体投票选项，例如“支持”、“反对”等
	// 无需保存具体数值，需要时可以从投票活动的VoteType对象中查询得到
	private String value;

	// Rep Invariants
	// value不能用空格分开
	// Abstract Function
	// candidate代表对应的候选人，value代表投票人对于该候选人的选票选项
	// Safety from Rep Exposure
	// ADT的所有属性都是private类型，并且构造方法是没有将可变类型传递给新对象的属性，并且成员方法也没有将可变类型的引用暴露给用户

	private boolean checkRep() {
		// TODO
		if(value.matches("\\S+"))
			return false;
		return true;
	}

	/**
	 * 创建一个投票项对象 例如：针对候选对象“张三”，投票选项是“支持”
	 * 
	 * @param candidate 所针对的候选对象
	 * @param value     所给出的投票选项
	 */
	public VoteItem(C candidate, String value) {
		this.candidate = candidate;
		this.value = value;
		checkRep();
	}

	/**
	 * 得到该投票选项的具体投票项
	 * 
	 * @return
	 */
	public String getVoteValue() {
		return this.value;
	}

	/**
	 * 得到该投票选项所对应的候选人
	 * 
	 * @return
	 */
	public C getCandidate() {
		return this.candidate;
	}

	@Override
	public int hashCode() {
		// TODO
		return this.candidate.hashCode()+this.value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO
		if(this.hashCode()==obj.hashCode())
		   return true;
		return false;
	}
}
