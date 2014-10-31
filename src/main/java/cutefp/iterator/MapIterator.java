package cutefp.iterator;

import java.util.Iterator;

import cutefp.func.F;

public class MapIterator<A,B> extends BaseIterator<A,B> {
	
	public MapIterator(Iterator<A> p, F<A, B> f) {
		super(p);
		this.f = f;
	}
	final F<A, B> f;

	@Override
	public boolean checkNext() {
		if(pickable()) {
			return foundNext(f.apply(pick()));
		}
		return false;
	}
}
