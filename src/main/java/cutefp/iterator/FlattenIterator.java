package cutefp.iterator;

import java.util.Iterator;

public class FlattenIterator<B> extends BaseIterator<Iterable<B>,B> {
	
	public FlattenIterator(Iterator<Iterable<B>> parent) {
		super(parent);
	}
	private Iterator<B> child;
	
	@Override
	public boolean checkNext() {
		if (child != null && child.hasNext()) {
			return foundNext(child.next());
		}
		
		while (pickable()) {
			child = pick().iterator();
			if (child.hasNext()) {
				return foundNext(child.next());
			}
		}
		return false;
	}
}
