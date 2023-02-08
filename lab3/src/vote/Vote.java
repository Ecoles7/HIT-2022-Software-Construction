package vote;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

//immutable
public class Vote<C> {

	//����һ���µ�����id�������ֶ�ʱ�������������ͶƱ
	private int id;
	static private int num = 0;
	// ȱʡΪ��������ͶƱ��������Ҫ����ͶƱ�˵���Ϣ

	// һ��ͶƱ�˶����к�ѡ�����ͶƱ���
	private Set<VoteItem<C>> voteItems = new HashSet<>();
	// ͶƱʱ��
	private Calendar date = Calendar.getInstance();

	// Rep Invariants
	// voteItems������ж��ͶƱ�ͶƱ��֮���ѡ����������ȣ�ֵ������ȡ�data�ǿ�
	// Abstract Function
	// voteItems��ʾ��ͶƱ�˶���Ͷ��Ʊ�ĺ�ѡ�����ͶƱ�date��ʾ��ѡƱ������
	// Safety from Rep Exposure
	// ADT���������Զ���private���Σ��ڹ��캯���У����÷���ʽ�������⽫���Ե����ñ�¶���ͻ������ҳ�Ա�����в��ֲ��÷���ʽ�����������ʾй¶

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
	 * ����һ��ѡƱ����
	 * 
	 * ����������Ƹ÷��������õĲ���
	 * 
	 */
	public Vote(Set<VoteItem<C>> voteItems) {
		// TODO
		this.id = ++Vote.num;
		this.voteItems.addAll(voteItems);
		checkRep();
	}

	/**
	 * ��ѯ��ѡƱ�а���������ͶƱ��
	 * 
	 * @return ����ͶƱ��
	 */
	public Set<VoteItem<C>> getVoteItems() {
		// TODO
		Set<VoteItem<C>> voteItems1 = new HashSet<>(voteItems);
		return voteItems1;
	}

	/**
	 * һ���ض���ѡ���Ƿ������ѡƱ��
	 * 
	 * @param candidate ����ѯ�ĺ�ѡ��
	 * @return �������ú�ѡ�˵�ͶƱ��򷵻�true������false
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
