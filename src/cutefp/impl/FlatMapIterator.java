package cutefp.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cutefp.func.F;

public class FlatMapIterator<A,B> implements Iterator<B> {
	
	public FlatMapIterator(Iterator<A> p, F<A,Iterable<B>> f) {
		this.parent = p;
		this.f = f;
	}
	final F<A,Iterable<B>> f;
	final Iterator<A> parent;
	
	private Iterator<B> child;
	private boolean isChildReady = false;
	
	@Override
	public boolean hasNext() {
		if (isChildReady && child.hasNext()) {
			return true;
		}
		
		while (parent.hasNext()) {
			child = f.apply(parent.next()).iterator();
			if (child.hasNext()) {
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
		return child.next();
	}
	
	@Override
	public void remove() {
		child.remove();
	}
	
}
