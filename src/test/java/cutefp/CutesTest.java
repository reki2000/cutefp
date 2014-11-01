package cutefp;

import static cutefp.Cutes.eq;
import static cutefp.Cutes.fromArray;
import static cutefp.Cutes.getter;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cutefp.func.F;
import cutefp.func.Predicate;

public class CutesTest {

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
	public void simple_getter() {
		final User[] users = new User[] { build("Steve","Jobs","MountainView"), build("Bill","Gates","Seatle") };
		final F<User,String> nameGetter = getter("firstName");
		
		List<String> names = fromArray(users).map(nameGetter).toList();

		assertThat(names, contains("Steve", "Bill"));
	}

	@Test
	public void simple_eq_field() {
		final User[] users = new User[] { build("Steve","Jobs","MountainView"), build("Bill","Gates","Seatle") };
		final Predicate<User> nameChecker = eq("firstName", "Steve");
		
		List<User> names = fromArray(users).filter(nameChecker).toList();

		assertThat(names.size(), is(1));
		assertThat(names.get(0).firstName, is("Steve"));
	}

	
	@Test
	public void multi_1() {
		final User[] users      = new User[] { build("Steve","Jobs","Mountain View"), null, build("Bill","Gates","Seatle") };
		final List<String> more = Arrays.asList(new String[] { "Mark", "Jeff" });

		final F<User,String> nameGetter   = getter("firstName");      // static import (getter)
		final Predicate<User> nameChecker = eq("firstName", "Steve"); // static import (eq)
		
		List<String> names = fromArray(users) // static import (from)
		                        .skipNull()
		                        .filter(nameChecker)
		                        .map(nameGetter)
		                        .concat(more)
		                        .toList();
		
		assertThat(names.size(), is(3));
	}
}
