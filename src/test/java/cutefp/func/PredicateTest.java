package cutefp.func;

import static cutefp.Cutes.fromArray;
import static cutefp.Cutes.same;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class PredicateTest {

	@Test
	public void or() {
		List<Integer> result = fromArray(1,2,3,4,5).filter(same(2).or(same(3),same(4))).toList();
		
		assertThat(result.size(), is(3));
		assertThat(result.get(0), is(2));
		assertThat(result.get(1), is(3));
		assertThat(result.get(2), is(4));
		
	}

	@Test
	public void and() {
		List<Integer> result = fromArray(1,2,3,4,5).filter(same(2).and(same(2),same(2))).toList();
		
		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(2));
	}

	@Test
	public void not() {
		List<Integer> result = fromArray(1,2,3,4,5).filter(same(2).not()).toList();
		
		assertThat(result.size(), is(4));
		assertThat(result.get(0), is(1));
		assertThat(result.get(1), is(3));
	}

}
