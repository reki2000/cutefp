package cutefp.impl;

import java.util.Iterator;

public class ConcatIterator<B> implements Iterator<B> {
	
	public ConcatIterator(Iterator<B> head, Iterator<B> tail) {
		this.head = head;
		this.tail = tail;
	}
	final Iterator<B> tail;
	final Iterator<B> head;
	
	private boolean useTail = false;
	
	@Override
	public boolean hasNext() {
		if (useTail) {
			return tail.hasNext();
		} else {
			if (head.hasNext()) {
				return true;
			}
			useTail = true;
			return tail.hasNext();
		}
	}

	@Override
	public B next() {
		if (useTail) {
			return tail.next();
		} else {
			return head.next();
		}
	}
	
	@Override
	public void remove() {
		if (useTail) {
			tail.remove();
		} else {
			head.remove();
		}
	}
	
}
