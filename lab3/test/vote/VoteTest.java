package vote;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class VoteTest {

	// test strategy
	//创建了一个选票集合并向其中添加了三个元素
	//判断能否正确返回包含在其中的元素和没包含在其中的元素
	// TODO
	
	@Test
	void test() {
		Set<VoteItem<String>> voteItemsYRC = new HashSet<>();
		voteItemsYRC.add(new VoteItem<String>("KANYE", "support"));
		voteItemsYRC.add(new VoteItem<String>("POPSMOKE", "support"));
		voteItemsYRC.add(new VoteItem<String>("SIXNINE", "reject"));
		Vote<String> vote = new Vote<String>(voteItemsYRC);

		assertTrue(vote.candidateIncluded("KANYE"));
		assertTrue(vote.candidateIncluded("SIXNINE"));
		assertFalse(vote.candidateIncluded("TYGA"));
		assertEquals(voteItemsYRC, vote.getVoteItems());
	}

}
