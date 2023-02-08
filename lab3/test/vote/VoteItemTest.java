package vote;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VoteItemTest {

	// test strategy
	//初始化了三个VoteItem对象
	//测试getCandidate，getVoteValue能否正确返回
	//测试equals能否正确返回
	// TODO
	
	@Test
	void test() {
		VoteItem<String> Itemtest1 = new VoteItem<>("LilTjay", "support");
		VoteItem<String> Itemtest2 = new VoteItem<>("PoloG", "reject");
		VoteItem<String> Itemtest3 = new VoteItem<>("LilTjay", "support");
		assertEquals("LilTjay", Itemtest1.getCandidate());
		assertEquals("support", Itemtest1.getVoteValue());
		assertTrue(Itemtest1.equals(Itemtest3));
		assertFalse(Itemtest1.equals(Itemtest2));
	}

}
