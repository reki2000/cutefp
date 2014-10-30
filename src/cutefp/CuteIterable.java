package cutefp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cutefp.func.F;
import cutefp.func.F2;
import cutefp.func.Predicate;
import cutefp.impl.ConcatIterator;
import cutefp.impl.FilterIterator;
import cutefp.impl.FlatMapIterator;
import cutefp.impl.FlattenIterator;
import cutefp.impl.MapIterator;

public class CuteIterable<A> implements Iterable<A> {
	
	protected CuteIterable(Iterable<A> i) {
		this.iterable = i;
	}
	private CuteIterable(Iterator<A> i) {
		this.iterator = i;
	}
	Iterator<A> iterator = null;
	Iterable<A> iterable = null;

	@Override
	public Iterator<A> iterator() {
		if (iterable != null) {
			return iterable.iterator();
		} else {
			return iterator;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<A> toList() {
		List<A> l = new ArrayList<A>();
		for (A item : this) {
			l.add(item);
		}
		return Collections.unmodifiableList(l);
	}

	/**
	 * 
	 * @return
	 */
	public Set<A> toSet() {
		Set<A> s = new HashSet<A>();
		for (A item : this) {
			s.add(item);
		}
		return Collections.unmodifiableSet(s);
	}

	/**
	 * Make a map with generated key and source value
	 * @return
	 */
	public <B> Map<B,A> toMap(F<A,B> keyF) {
		Map<B,A> m = new HashMap<B,A>();
		for (A item : this) {
			m.put(keyF.apply(item), item);
		}
		return Collections.unmodifiableMap(m);
	}
	
	
	/**
	 * Make a map with generated key and generated value
	 * @return
	 */
	public <B,C> Map<B,C> toMap(F<A,B> keyF, F<A,C> valF) {
		Map<B,C> m = new HashMap<B,C>();
		for (A item : this) {
			m.put(keyF.apply(item), valF.apply(item));
		}
		return Collections.unmodifiableMap(m);
	}
	
	/**
	 * @return
	 */
	public CuteIterable<A> filter(final Predicate<A> p) {
		return new CuteIterable<A>(new FilterIterator<A>((Iterator<A>)iterator(), p));
	}
	
	/**
	 * returns a new itarable which is mapped by f
	 * @param f
	 * @return
	 */
	public <B> CuteIterable<B> map(final F<A,B> f) {
		return new CuteIterable<B>(new MapIterator<A,B>(iterator(), f));
	}

	/**
	 * returns a new itarable which is flatten mapped by f
	 * @param f
	 * @return
	 */
	public <B> CuteIterable<B> flatMap(final F<A,Iterable<B>> f) {
		return new CuteIterable<B>(new FlatMapIterator<A,B>(iterator(), f));
	}

	/**
	 * returns a new itarable which contains flatten elements. source type must be sub-type of Iterable<Iterable<A>>
	 * @param f
	 * @return
	 */
	public <A> CuteIterable<A> flatten() {
		return new CuteIterable<A>(new FlattenIterator<A>((Iterator<Iterable<A>>)iterator()));
	}

	/**
	 * returns a new itarable which is reverse ordered
	 * @return
	 */
	public CuteIterable<A> reverse() {
		List<A> list = this.toList();
		Collections.<A>reverse(list);
		return new CuteIterable<A>(list);
	}
	
	/**
	 * returns a new itarable which is sorted using comparator
	 * @return
	 */
	public CuteIterable<A> sort(Comparator<A> comparator) {
		List<A> list = this.toList();
		Collections.<A>sort(list, comparator);
		return new CuteIterable<A>(list);
	}
	
	/**
	 * @return
	 */
	public <B> B fold(B init, final F2<A,B,B> f) {
		B acc = init;
		for (A item : this) {
			acc = f.apply(item, acc);
		}
		return acc;
	}

	/**
	 * Concatinates two iterables into one
	 */
	public CuteIterable<A> concat(Iterable<A> tail) {
		return new CuteIterable<A>(new ConcatIterator<A>(iterator(), tail.iterator()));
	}
	
	/**
	 * skip null
	 */
	public CuteIterable<A> skipNull() {
		Predicate<A> p = new Predicate<A>() { public Boolean apply(A a) { return (a != null); } };
		return this.filter(p);
	}

	/**
	 * substitutes null to other object
	 */
	public CuteIterable<A> alterNull(final A x) {
		F<A,A> p = new F<A,A>() { public A apply(A a) { return (a == null) ? x : a; } };
		return this.map(p);
	}
}
