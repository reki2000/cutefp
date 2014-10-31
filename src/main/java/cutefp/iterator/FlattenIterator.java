package cutefp.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FlattenIterator<B> implements Iterator<B> {
	
	public FlattenIterator(Iterator<Iterable<B>> parent) {
		this.parent = parent;
	}
	private final Iterator<Iterable<B>> parent;
	private Iterator<B> child;
	private boolean isChildReady = false;
	
	@Override
	public boolean hasNext() {
		if (isChildReady && child.hasNext()) {
			return true;
		}
		
		while (parent.hasNext()) {
			child = parent.next().iterator();
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
		return child.next();
	}
	
	@Override
	public void remove() {
		child.remove();
	}
	
}
