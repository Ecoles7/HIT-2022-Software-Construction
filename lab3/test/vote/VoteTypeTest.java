package vote;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VoteTypeTest {

	// test strategy
	//对checkLegality的输入划分：option包含的投票选项，option不包含的投票选项
	//对getScoreByOption的输入划分：三种投票选项及不合法的选项
	//对equals的输入划分：相等和不相等两种情况
	//对hashcode的输入划分：两个相同对象
	// TODO
	@Test
	void test() {
        VoteType busi1=new VoteType(0);//这个初始化为商业表决类型
		VoteType repre=new VoteType(1);//这个初始化为代表选举类型
		VoteType dinner=new VoteType(2);//这个初始化为聚餐点菜类型
		VoteType busi2=new VoteType(0);//这个初始化为商业表决类型
		//checkLegality测试
		//checkLegality输入option包含的投票选项
		assertEquals(true,busi1.checkLegality("reject"));
		//checkLegality输入option不包含的投票选项
		assertEquals(false,busi1.checkLegality("Strongly reject"));
		//getScoreByOption测试
		assertEquals(-1,busi1.getScoreByOption("reject"));
		assertEquals(1,busi1.getScoreByOption("support"));
		assertEquals(0,busi1.getScoreByOption("abstained"));
		assertEquals(-1,repre.getScoreByOption("reject"));
		assertEquals(1,repre.getScoreByOption("support"));
		assertEquals(0,repre.getScoreByOption("abstained"));
		assertEquals(2,dinner.getScoreByOption("like"));
		assertEquals(0,busi1.getScoreByOption("dislike"));
		assertEquals(0,busi1.getScoreByOption("neutral"));
		assertEquals(0,busi1.getScoreByOption("idontknowwwww"));
		//equals测试
		assertEquals(true,busi1.equals(busi2));
		//hashcode测试
		assertEquals(true,busi1.hashCode()==busi2.hashCode());
	}

}
