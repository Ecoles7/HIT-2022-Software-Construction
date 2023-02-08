package vote;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//immutable
public class VoteType {

	// keyΪѡ������valueΪѡ������Ӧ�ķ���
	private Map<String, Integer> options = new HashMap<>();

	// Rep Invariants
	// options��Ԫ�ظ������ڵ���2
	// Abstract Function
	// options�еļ�����ͶƱ�˿�ѡ��ͶƱѡ���Ӧ��ֵΪ��ѡ��������ĵķ�����
	// Safety from Rep Exposure
	// ADT�е����Զ���private���Σ������ڹ��췽���в��÷���ʽ�����Է��û��õ����Ե����ã���������Ա������Ҳû�н����Ե�����ֱ�Ӵ��ݸ��û�

	private boolean checkRep() {
		// TODO
		if(!(options.size() >= 2))
			return false;
		return true;
	}
     //�ҵ���ͬѡ���Ӧ��string
	public String findsupport(){
		Set<Integer> value=new HashSet<>(options.values());
		Integer x=-10;
		for(Integer vl:value){
			if(vl>x)
				x=vl;
		}
		for (Map.Entry<String, Integer>  entry : options.entrySet()) {
			String mapKey = entry.getKey();
			Integer mapValue = entry.getValue();
			if(mapValue == x)
				return mapKey;
		}
		return null;
	}

	//�ҵ�����ѡ���Ӧ��string
	public String findoppose(){
		Set<Integer> value=new HashSet<>(options.values());
		Integer x=10;
		for(Integer vl:value){
			if(vl<x)
				x=vl;
		}
		for (Map.Entry<String, Integer>  entry : options.entrySet()) {
			String mapKey = entry.getKey();
			Integer mapValue = entry.getValue();
			if(mapValue == x)
				return mapKey;
		}
		return null;
	}

	/**
	 * ����һ��ͶƱ���Ͷ���
	 * 
	 * ����������Ƹ÷��������õĲ���
	 */
	public VoteType(int type) {
		// TODO
		if(type == 0){                 //0�����ʼ����ҵ�������
			this.options.put("support",1);
			this.options.put("abstained",0);
			this.options.put("reject",-1);
		}
		else if(type == 1){           //1�����ʼ������ѡ������
			this.options.put("support",1);
			this.options.put("abstained",0);
			this.options.put("reject",-1);
		}
		else if(type == 2){          //2�����ʼ���ۻ�������
			this.options.put("like",2);
			this.options.put("neutral",1);
			this.options.put("dislike",0);
		}
		checkRep();
	}

	public VoteType(Map<String,Integer> types) {
		Map<String,Integer> MyOptions=new HashMap<>();
		for(Map.Entry<String,Integer> entry:types.entrySet()){
			MyOptions.put(entry.getKey(), entry.getValue());
		}
		this.options=MyOptions;
		checkRep();
	}

	/**
	 * ���������ض��﷨������ַ���������һ��ͶƱ���Ͷ���
	 * 
	 * @param regex ��ѭ�ض��﷨�ġ�����ͶƱ������Ϣ���ַ�����������12�ٿ��ǣ�
	 */
	public VoteType(String regex) {
		// TODO
		String[] inputOptions = regex.split("\\|");
		int mode = 0;
		if(inputOptions.length < 2) {
			throw new IllegalArgumentException("�Ƿ�����:ѡ����������");
		}
		else {
			for(String option: inputOptions) {
				Pattern regexWithNum = Pattern.compile("\\\"(\\S+)\\\"\\(([\\+-]?\\d+)\\)"); //��Ȩ��
				Pattern regexWithoutNum = Pattern.compile("\\\"(\\S+)\\\"");  //����Ȩ��
				Matcher m1 = regexWithNum.matcher(option);
				Matcher m2 = regexWithoutNum.matcher(option);
				if(m1.matches()) {
					// ��ʼ��һ��mode
					if(mode == 0) mode = 1;
					// ǰ���ʽ��һ���ˣ�ǰ����û���ֵĺ�������������
					if(mode != 1) {
						throw new IllegalArgumentException("�Ƿ�����:��ʽ��һ��");
					}

					if(m1.group(1).length() >= 5)
						throw new IllegalArgumentException("�Ƿ�����:ѡ��������");
					options.put(m1.group(1), Integer.valueOf(m1.group(2)));
				}
				else if(m2.matches()) {
					if(mode == 0) mode = 2;
					if(mode != 2) {
						throw new IllegalArgumentException("�Ƿ�����:��ʽ��һ��");
					}

					if(m2.group(1).length() >= 5)
						throw new IllegalArgumentException("�Ƿ�����:ѡ��������");
					options.put(m2.group(1), 1);// Ĭ������ѡ���Ȩֵ��Ϊ1
				}
				else {
					throw new IllegalArgumentException("�Ƿ�����:������ʽ��ƥ��");
				}
			}
		}
		checkRep();
	}

	/**
	 * �ж�һ��ͶƱѡ���Ƿ�Ϸ�������Poll�ж�ѡƱ�ĺϷ��Լ�飩
	 * 
	 * ���磬ͶƱ�˸�����ͶƱѡ���ǡ�Strongly reject������options�в��������ѡ�������ǷǷ���
	 * 
	 * ���ܸĶ��÷����Ĳ���
	 * 
	 * @param option һ��ͶƱѡ��
	 * @return �Ϸ���true������false
	 */
	public boolean checkLegality(String option) {
		// TODO
		if(options.containsKey(option))
			return true;
		return false;
	}

	/**
	 * ����һ��ͶƱѡ��õ����Ӧ�ķ���
	 * 
	 * ���磬ͶƱ�˸�����ͶƱѡ���ǡ�֧�֡�����ѯ�õ����Ӧ�ķ�����1
	 * 
	 * ���ܸĶ��÷����Ĳ���
	 * 
	 * @param option һ��ͶƱ��ȡֵ
	 * @return ��ѡ������Ӧ�ķ���
	 */
	public int getScoreByOption(String option) {
		// TODO
		if(options.containsKey(option))
			return options.get(option);
		else
		    return 0;
	}

	@Override
	public int hashCode() {
		// TODO
		int result=0;
		for(Map.Entry<String,Integer> entry:this.options.entrySet()){
			result+=entry.hashCode();
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO
		if(obj==null)return false;
		if(!(obj instanceof VoteType))return false;
		VoteType that=(VoteType) obj;
		if(this.options.equals(that.options)){
			return true;
		}
		else return false;
	}
}
