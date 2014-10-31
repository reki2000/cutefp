package cutefp.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cutefp.func.F;

public class FlatMapIterator<A,B> implements Iterator<B> {
	
	public FlatMapIterator(Iterator<A> iterator, F<A,Iterable<B>> func) {
		this.iterator = iterator;
		this.func = func;
	}
	final Iterator<A> iterator;
	private B nextValue;
	private boolean isNextReady = false;

	final F<A,Iterable<B>> func;
	private Iterator<B> childIterator;
	
	private boolean setNextValue(B value) {
		this.nextValue = value;
		isNextReady = true;
		return true;
	}
	
	@Override
	public boolean hasNext() {
		if (isNextReady) {
			return true;
		}
		
		if (childIterator != null && childIterator.hasNext()) {
			return setNextValue(childIterator.next());
		}

		while (iterator.hasNext()) {
			childIterator = func.apply(iterator.next()).iterator();
			if (childIterator.hasNext()) {
				return setNextValue(childIterator.next());
			}
		}
		
		return false;
	}

	@Override
	public B next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		isNextReady = false;
		return nextValue;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}
