package cutefp.func;

/**
 * Tuple class for 2 items
 * @author reki
 *
 */
public class T2<E1,E2> {

	public static <E1,E2> T2<E1,E2> t2(E1 e1, E2 e2) {
		return new T2<E1,E2>(e1, e2);
	}

	public T2(E1 e1, E2 e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	final public E1 e1;
	final public E2 e2;

}
