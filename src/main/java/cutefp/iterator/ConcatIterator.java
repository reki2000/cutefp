package cutefp.iterator;

import java.util.Iterator;

public class ConcatIterator<B> extends BaseIterator<B,B> {
	
	public ConcatIterator(Iterator<B> head, Iterator<B> tail) {
		super(head);
		this.tail = tail;
	}
	final Iterator<B> tail;
	private boolean useTail = false;
	
	@Override
	public boolean checkNext() {
		if (!useTail) {
			if (pickable()) {
				return foundNext(pick());
			}
			useTail = true;
		}
		if (tail.hasNext()) {
			return foundNext(tail.next());
		}
		return false;
	}
}
