cutefp
======

A Handy Collection Library For Functional Programming in Java 7

## Motivation

* To introduce scala-like handy data manipulation to Java
* To learn how to implement functional style collection library. 
* Handling both of null and Optional is definately hell. I Need more null-friendly collection.
* Java 8 is cool. But I need more simple, short cord then perfect compatibility and polite type naming.
* Guava is great. But I feel guava code not easy to read.
* Kotlin is pretty. I like it. Groovy - simply I don't tried it. Anyway I have to use Java.

## Code with Cutefp

Below is an example of cutefp code. If you feel this code "simple", cutefp will help you in future :-)
```
    //
    // "User" class has 3 fields - firstName, lastName and city. "build" is an utility to build User instance
    //
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

```

## その他

Cute の 由来は Guavaさんやscalaさんのように、正しい分かなり重い感のない「かわいくてしょうがない」ライブラリにしたかったから．
