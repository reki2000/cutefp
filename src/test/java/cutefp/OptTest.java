package cutefp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class OptTest {
	
	@Test
	public void simple_some() {
		Opt<Integer> opt = Opt.<Integer>some(1);
		assertThat(opt.isSome(), is(true));
		assertThat(opt.isNone(), is(false));
		assertThat(opt.get(), is(1));
		assertThat(opt.orElse(3), is(1));
	}

	@Test
	public void simple_none() {
		Opt<Integer> opt = Opt.<Integer>none();
		assertThat(opt.isSome(), is(false));
		assertThat(opt.isNone(), is(true));
	}

	@Test
	public void simple_for_some() {
		int count = 0;
		Opt<Integer> opt = Opt.<Integer>some(1);
		for (Integer i : opt) {
			count++;
		}
		assertThat(count, is(1));
	}

	@Test
	public void simple_for_none() {
		int count = 0;
		Opt<Integer> opt = Opt.<Integer>none();
		for (Integer i : opt) {
			count++;
		}
		assertThat(count, is(0));
	}

}
