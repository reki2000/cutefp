package cutefp.iterator;

import java.util.Iterator;

import cutefp.func.F;

public class FlatMapIterator<A,B> extends BaseIterator<A,B> {
	
	public FlatMapIterator(Iterator<A> iterator, F<A,Iterable<B>> func) {
		super(iterator);
		this.func = func;
	}
	final F<A,Iterable<B>> func;
	private Iterator<B> child;
	
	@Override
	public boolean checkNext() {
		if (child != null && child.hasNext()) {
			return foundNext(child.next());
		}

		while (pickable()) {
			child = func.apply(pick()).iterator();
			if (child.hasNext()) {
				return foundNext(child.next());
			}
		}
		
		return false;
	}
}
