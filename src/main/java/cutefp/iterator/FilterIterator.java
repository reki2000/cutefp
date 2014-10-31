package cutefp.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cutefp.func.Predicate;

public class FilterIterator<B> implements Iterator<B> {
	
	public FilterIterator(Iterator<B> p, Predicate<B> pred) {
		this.parent = p;
		this.pred = pred;
	}
	final Predicate<B> pred;
	final Iterator<B> parent;
	
	private B child;
	private boolean isChildReady = false;
	
	@Override
	public boolean hasNext() {
		if (isChildReady) {
			return true;
		}
		
		while (parent.hasNext()) {
			child = parent.next();
			if (pred.apply(child)) {
				isChildReady = true;
				return true;
			}
		}
		return false;
	}

	@Override
	public B next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		isChildReady = false;
		return child;
	}
	
	@Override
	public void remove() {
		throw new NoSuchMethodError();
	}
	
}
