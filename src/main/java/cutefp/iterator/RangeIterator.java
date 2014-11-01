package cutefp.iterator;

import java.util.Iterator;

public class RangeIterator<B> extends BaseIterator<B,B> {
	
	public RangeIterator(Iterator<B> parent, int start, int count) {
		super(parent);
		this.start = start;
		this.count = count;
	}
	private final int start;
	private final int count;
	private int current = 0;
	
	@Override
	public boolean checkNext() {
		while (current < start) {
			if (pickable()) {
				pick();
			} else {
				return false;
			}
			current++;
		}
		
		if (current >= start + count) {
			return false;
		}
		
		if (pickable()) {
			current++;
			return foundNext(pick());
		}
		return false;
	}
}
