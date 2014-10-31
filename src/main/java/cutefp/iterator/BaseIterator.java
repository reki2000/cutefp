package cutefp.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

abstract public class BaseIterator<A,B> implements Iterator<B> {
	
	protected BaseIterator(final Iterator<A> iterator) {
		this.iterator = iterator;
	}
	final private Iterator<A> iterator;

	private B nextValue;
	private boolean isNextReady = false;

	final protected boolean foundNext(B value) {
		this.nextValue = value;
		isNextReady = true;
		return true;
	}
	
	final protected boolean pickable() {
		return iterator.hasNext();
	}
	
	final protected A pick() {
		return iterator.next();
	}
	
	/**
	 * Most fundamental part of iterator. 
	 * How to implement: <br/>
	 * - Check parent's state with <code>pickable()</code><br/>
	 * - If the next value was found, let's get it with <code>pick()</code> then build your own next value.<br/>
	 * - Then do <code>return foundNext(value)</code>, otherwise <code>return false</code>.<br/>
	 * 
	 * @return
	 */
	abstract protected boolean checkNext();

	
	@Override
	final public boolean hasNext() {
		if (isNextReady) {
			return true;
		}
		
		return checkNext();
	}
	
	@Override
	final public B next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		isNextReady = false;
		return nextValue;
	}
	
	@Override
	final public void remove() {
		throw new UnsupportedOperationException();
	}
	
}
