package cutefp.impl;

import java.util.Iterator;

import cutefp.func.F;

public class MapIterator<A,B> implements Iterator<B> {
	
	public MapIterator(Iterator<A> p, F<A, B> f) {
		this.p = p;
		this.f = f;
	}
	final F<A, B> f;
	final Iterator<A> p;

	@Override
	public boolean hasNext() {
		return p.hasNext();
	}

	@Override
	public B next() {
		return f.apply(p.next());
	}
	
	@Override
	public void remove() {
		p.remove();
	}

}
