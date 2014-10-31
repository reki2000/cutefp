package cutefp.collector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cutefp.func.F;

public class CollectionCollector {

	/**
	 * 
	 * @return
	 */
	public static <A> List<A> toList(Iterable<A> iter) {
		List<A> l = new ArrayList<A>();
		for (A item : iter) {
			l.add(item);
		}
		return Collections.unmodifiableList(l);
	}

	/**
	 * 
	 * @return
	 */
	public static <A> Set<A> toSet(Iterable<A> iter) {
		Set<A> s = new HashSet<A>();
		for (A item : iter) {
			s.add(item);
		}
		return Collections.unmodifiableSet(s);
	}

	/**
	 * Make a map with generated key and source value
	 * @return
	 */
	public static <A,B> Map<B,A> toMap(Iterable<A> iter, F<A,B> keyF) {
		Map<B,A> m = new HashMap<B,A>();
		for (A item : iter) {
			m.put(keyF.apply(item), item);
		}
		return Collections.unmodifiableMap(m);
	}
	
	
	/**
	 * Make a map with generated key and generated value
	 * @return
	 */
	public static <A, B,C> Map<B,C> toMap(Iterable<A> iter, F<A,B> keyF, F<A,C> valF) {
		Map<B,C> m = new HashMap<B,C>();
		for (A item : iter) {
			m.put(keyF.apply(item), valF.apply(item));
		}
		return Collections.unmodifiableMap(m);
	}

}
