package vote;

//immutable
public class VoteItem<C> {

	// ��ͶƱ������Եĺ�ѡ����
	private C candidate;
	// �Ժ�ѡ����ľ���ͶƱѡ����硰֧�֡��������ԡ���
	// ���豣�������ֵ����Ҫʱ���Դ�ͶƱ���VoteType�����в�ѯ�õ�
	private String value;

	// Rep Invariants
	// value�����ÿո�ֿ�
	// Abstract Function
	// candidate�����Ӧ�ĺ�ѡ�ˣ�value����ͶƱ�˶��ڸú�ѡ�˵�ѡƱѡ��
	// Safety from Rep Exposure
	// ADT���������Զ���private���ͣ����ҹ��췽����û�н��ɱ����ʹ��ݸ��¶�������ԣ����ҳ�Ա����Ҳû�н��ɱ����͵����ñ�¶���û�

	private boolean checkRep() {
		// TODO
		if(value.matches("\\S+"))
			return false;
		return true;
	}

	/**
	 * ����һ��ͶƱ����� ���磺��Ժ�ѡ������������ͶƱѡ���ǡ�֧�֡�
	 * 
	 * @param candidate ����Եĺ�ѡ����
	 * @param value     ��������ͶƱѡ��
	 */
	public VoteItem(C candidate, String value) {
		this.candidate = candidate;
		this.value = value;
		checkRep();
	}

	/**
	 * �õ���ͶƱѡ��ľ���ͶƱ��
	 * 
	 * @return
	 */
	public String getVoteValue() {
		return this.value;
	}

	/**
	 * �õ���ͶƱѡ������Ӧ�ĺ�ѡ��
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
