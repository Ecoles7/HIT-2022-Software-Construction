package vote;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//immutable
public class VoteType {

	// key为选项名、value为选项名对应的分数
	private Map<String, Integer> options = new HashMap<>();

	// Rep Invariants
	// options中元素个数大于等于2
	// Abstract Function
	// options中的键代表投票人可选的投票选项，相应的值为该选项所代表的的分数。
	// Safety from Rep Exposure
	// ADT中的属性都用private修饰，并且在构造方法中采用防御式拷贝以防用户得到属性的引用，在其他成员方法中也没有将属性的引用直接传递给用户

	private boolean checkRep() {
		// TODO
		if(!(options.size() >= 2))
			return false;
		return true;
	}
     //找到赞同选项对应的string
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

	//找到反对选项对应的string
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
	 * 创建一个投票类型对象
	 * 
	 * 可以自行设计该方法所采用的参数
	 */
	public VoteType(int type) {
		// TODO
		if(type == 0){                 //0代表初始化商业表决类型
			this.options.put("support",1);
			this.options.put("abstained",0);
			this.options.put("reject",-1);
		}
		else if(type == 1){           //1代表初始化代表选举类型
			this.options.put("support",1);
			this.options.put("abstained",0);
			this.options.put("reject",-1);
		}
		else if(type == 2){          //2代表初始化聚会点餐类型
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
	 * 根据满足特定语法规则的字符串，创建一个投票类型对象
	 * 
	 * @param regex 遵循特定语法的、包含投票类型信息的字符串（待任务12再考虑）
	 */
	public VoteType(String regex) {
		// TODO
		String[] inputOptions = regex.split("\\|");
		int mode = 0;
		if(inputOptions.length < 2) {
			throw new IllegalArgumentException("非法输入:选项少于两个");
		}
		else {
			for(String option: inputOptions) {
				Pattern regexWithNum = Pattern.compile("\\\"(\\S+)\\\"\\(([\\+-]?\\d+)\\)"); //带权重
				Pattern regexWithoutNum = Pattern.compile("\\\"(\\S+)\\\"");  //不带权重
				Matcher m1 = regexWithNum.matcher(option);
				Matcher m2 = regexWithoutNum.matcher(option);
				if(m1.matches()) {
					// 初始化一下mode
					if(mode == 0) mode = 1;
					// 前后格式不一致了，前面是没数字的后面又有数字了
					if(mode != 1) {
						throw new IllegalArgumentException("非法输入:格式不一致");
					}

					if(m1.group(1).length() >= 5)
						throw new IllegalArgumentException("非法输入:选项名过长");
					options.put(m1.group(1), Integer.valueOf(m1.group(2)));
				}
				else if(m2.matches()) {
					if(mode == 0) mode = 2;
					if(mode != 2) {
						throw new IllegalArgumentException("非法输入:格式不一致");
					}

					if(m2.group(1).length() >= 5)
						throw new IllegalArgumentException("非法输入:选项名过长");
					options.put(m2.group(1), 1);// 默认所有选项的权值都为1
				}
				else {
					throw new IllegalArgumentException("非法输入:正则表达式不匹配");
				}
			}
		}
		checkRep();
	}

	/**
	 * 判断一个投票选项是否合法（用于Poll中对选票的合法性检查）
	 * 
	 * 例如，投票人给出的投票选项是“Strongly reject”，但options中不包含这个选项，因此它是非法的
	 * 
	 * 不能改动该方法的参数
	 * 
	 * @param option 一个投票选项
	 * @return 合法则true，否则false
	 */
	public boolean checkLegality(String option) {
		// TODO
		if(options.containsKey(option))
			return true;
		return false;
	}

	/**
	 * 根据一个投票选项，得到其对应的分数
	 * 
	 * 例如，投票人给出的投票选项是“支持”，查询得到其对应的分数是1
	 * 
	 * 不能改动该方法的参数
	 * 
	 * @param option 一个投票项取值
	 * @return 该选项所对应的分数
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
