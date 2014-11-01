package cutefp;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cutefp.collector.CollectionCollector;
import cutefp.func.F;
import cutefp.func.F2;
import cutefp.func.Predicate;
import cutefp.iterator.ConcatIterator;
import cutefp.iterator.RangeIterator;
import cutefp.iterator.FilterIterator;
import cutefp.iterator.FlatMapIterator;
import cutefp.iterator.FlattenIterator;
import cutefp.iterator.MapIterator;

public class CuteIterable<A> implements Iterable<A> {
	
	protected CuteIterable(Iterable<A> i) {
		this.iterator = i.iterator();
	}
	private CuteIterable(Iterator<A> i) {
		this.iterator = i;
	}
	private Iterator<A> iterator = null;

	@Override
	public Iterator<A> iterator() {
		return iterator;
	}
	
	/**
	 * Converter to standard collection class
	 * @return
	 */
	
	public A[] toArray() {
		return CollectionCollector.<A>toArray(this);
	}

	public List<A> toList() {
		return CollectionCollector.<A>toList(this);
	}

	public Set<A> toSet() {
		return CollectionCollector.<A>toSet(this);
	}

	public <B> Map<B,A> toMap(F<A,B> keyF) {
		return CollectionCollector.<A,B>toMap(this, keyF);
	}
	
	public <B,C> Map<B,C> toMap(F<A,B> keyF, F<A,C> valF) {
		return CollectionCollector.<A,B,C>toMap(this, keyF, valF);
	}
	
	
	/**
	 * Make new iterable which is filtered with given predicate
	 * @return
	 */
	public CuteIterable<A> filter(final Predicate<A> p) {
		return new CuteIterable<A>(new FilterIterator<A>((Iterator<A>)iterator(), p));
	}
	
	/**
	 * Make new iterable which is mapped by f
	 * @param f
	 * @return
	 */
	public <B> CuteIterable<B> map(final F<A,B> f) {
		return new CuteIterable<B>(new MapIterator<A,B>(iterator(), f));
	}

	/**
	 * Make new iterable which is flatten mapped by f
	 * @param f
	 * @return
	 */
	public <B> CuteIterable<B> flatMap(final F<A,Iterable<B>> f) {
		return new CuteIterable<B>(new FlatMapIterator<A,B>(iterator(), f));
	}

	/**
	 * Make new iterable which contains flatten elements. source type must be sub-type of Iterable<Iterable<A>>
	 * @param func
	 * @return
	 */
	public <A> CuteIterable<A> flatten() {
		return new CuteIterable<A>(new FlattenIterator<A>((Iterator<Iterable<A>>)iterator()));
	}

	/**
	 * Make new iterable which is Concatinated with two iterables
	 */
	public CuteIterable<A> concat(Iterable<A> tail) {
		return new CuteIterable<A>(new ConcatIterator<A>(iterator(), tail.iterator()));
	}

	/**
	 * Make new iterable which has part of original iterable
	 */
	public CuteIterable<A> take(int count) {
		return take(0, count);
	}
	public CuteIterable<A> drop(int count) {
		return take(count, Integer.MAX_VALUE);
	}
	public CuteIterable<A> take(int start, int count) {
		if (count < 0) {
			throw new IllegalArgumentException("count should be positive or zero");
		}
		if (start < 0) {
			throw new IllegalArgumentException("start should be positive or zero");
		}
		return new CuteIterable<A>(new RangeIterator<A>(iterator(), start, count));
	}

	/**
	 * Make new iterable which is reverse ordered
	 * @return
	 */
	public CuteIterable<A> reverse() {
		List<A> list = this.toList();
		Collections.<A>reverse(list);
		return new CuteIterable<A>(list);
	}
	
	/**
	 * Make new itarable which is sorted with given comparator
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
	 * any
	 */
	public boolean any(Predicate<A> pred) {
		for (A item : this) {
			if (pred.apply(item)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * all
	 */
	public boolean all(Predicate<A> pred) {
		for (A item : this) {
			if (!pred.apply(item)) {
				return false;
			}
		}
		return true;
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
