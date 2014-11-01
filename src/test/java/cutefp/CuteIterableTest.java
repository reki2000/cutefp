package cutefp;

import static cutefp.Cutes.from;
import static cutefp.Cutes.fromArray;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cutefp.CuteIterable;
import cutefp.func.F;

public class CuteIterableTest {
	
	static class Prefecture {
		String name;
	}
	static class Address {
		Prefecture prefecture;
		
	}
	static class User {
		Address address;
		String firstName;
		String lastName;
	}
	
	User build(String firstName, String lastName, String cityName) {
		User user = new User();
		user.address = new Address();
		user.firstName = firstName;
		user.lastName = lastName;
		user.address.prefecture = new Prefecture();
		user.address.prefecture.name = cityName;
		return user;
	}
	
	@Test
	public void simple_map() {
		final User[] users = new User[] { build("Steve","Jobs","MountainView"), build("Bill","Gates","Seatle") };
		final F<User,String> nameGetter = new F<User,String>() { public String apply(User u) { return u.firstName; } };

		List<String> names = fromArray(users).map(nameGetter).toList();

		assertThat(names.size(), is(2));
		assertThat(names.get(0), is("Steve"));
		assertThat(names.get(1), is("Bill"));
	}

	@Test
	public void simple_flatmap() {
		final CuteIterable<User> users = fromArray(build("Steve","Jobs","MountainView"), build("Bill","Gates","Seatle"));
		final F<User,Iterable<String>> nameListGetter = new F<User,Iterable<String>>() { public Iterable<String> apply(User u) { return Arrays.asList(u.firstName, u.lastName); } };
		final F<String,String> toUpper = new F<String,String>() { public String apply(String s) { return s.toUpperCase(); } };

		List<String> names = users.flatMap(nameListGetter).map(toUpper).toList();

		assertThat(names.size(), is(4));
		assertThat(names.get(0), is("STEVE"));
		assertThat(names.get(1), is("JOBS"));
		assertThat(names.get(2), is("BILL"));
		assertThat(names.get(3), is("GATES"));
	}

	@Test
	public void simple_concat() {
		final Integer[] head = { 1,2,3 };
		final Integer[] tail = { 4,5,6 };

		List<Integer> names = fromArray(head).concat(fromArray(tail)).toList();

		assertThat(names.size(), is(6));
		assertThat(names.get(0), is(1));
		assertThat(names.get(2), is(3));
		assertThat(names.get(3), is(4));
	}


	@Test
	public void simple_flatten() {
		final List<List<Integer>> nest = new ArrayList<List<Integer>>() {{
			add(Arrays.asList(new Integer[] {1,2}));
			add(Arrays.asList(new Integer[] {3,4}));
		}};

		CuteIterable<Integer> flatten = from(nest).flatten();
		List<Integer> names = flatten.toList();

		assertThat(names.size(), is(4));
		assertThat(names.get(0), is(1));
		assertThat(names.get(1), is(2));
		assertThat(names.get(2), is(3));
	}
	
	@Test
	public void simple_skipNull() {
		final Integer[] head = { 1, null, 3 };

		List<Integer> names = fromArray(head).skipNull().toList();

		assertThat(names.size(), is(2));
		assertThat(names.get(0), is(1));
		assertThat(names.get(1), is(3));
	}

	@Test
	public void simple_alterNull() {
		final Integer[] head = { 1, null, 3 };

		List<Integer> names = fromArray(head).alterNull(5).toList();

		assertThat(names.size(), is(3));
		assertThat(names.get(0), is(1));
		assertThat(names.get(1), is(5));
		assertThat(names.get(2), is(3));
	}

	@Test
	public void simple_take() {
		List<Integer> result = fromArray(1, null, null, 4).take(2).toList();

		assertThat(result.size(), is(2));
		assertThat(result.get(0), is(1));
		assertThat(result.get(1), is(nullValue()));
	}

	@Test
	public void take_2args() {
		List<Integer> result = fromArray(1, null, null, 4).take(3,2).toList();

		assertThat(result.size(), is(2));
		assertThat(result.get(0), is(nullValue()));
		assertThat(result.get(1), is(4));
	}

	@Test
	public void drop() {
		List<Integer> result = fromArray(1, null, null, 4).drop(2).toList();

		assertThat(result.size(), is(2));
		assertThat(result.get(0), is(nullValue()));
		assertThat(result.get(1), is(4));
	}
}
