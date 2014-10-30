package cutefp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


public abstract class Opt<A> extends CuteIterable<A> {
	protected Opt(Iterable<A> val) {
		super(val);
	};
	
	abstract public boolean isSome();
	abstract public boolean isNone();
	abstract public A get();
	abstract public A getOrElse(A alt);

	public static <A> Opt<A> none() { 
		return new Opt<A>(Collections.<A>emptyList()) {
			public boolean isSome() { return false; }
			public boolean isNone() { return true; }
			public A get() { throw new NoSuchElementException(); }
			public A getOrElse(A alt) { return alt; }
		};
	}
	
	public static <A> Opt<A> some(A val) {
		List<A> list = new ArrayList<A>(1);
		list.add(val);
		return new Opt<A>(list) {
			public boolean isSome() { return true; }
			public boolean isNone() { return false; }
			public A get() { return iterator().next(); }
			public A getOrElse(A alt) { return iterator().next(); }
		};
	};
}
