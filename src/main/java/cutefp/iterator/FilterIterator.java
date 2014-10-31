package cutefp.iterator;

import java.util.Iterator;

import cutefp.func.Predicate;

public class FilterIterator<B> extends BaseIterator<B,B> {
	
	public FilterIterator(Iterator<B> p, Predicate<B> pred) {
		super(p);
		this.pred = pred;
	}
	final Predicate<B> pred;
	
	@Override
	public boolean checkNext() {
		while (pickable()) {
			B value = pick();
			if (pred.apply(value)) {
				return foundNext(value);
			}
		}
		return false;
	}
}
