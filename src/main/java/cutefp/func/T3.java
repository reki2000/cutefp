package cutefp.func;

/**
 * Tuple class for 3 items
 * @author reki
 *
 */
public class T3<E1,E2,E3> {

	public static <E1,E2,E3> T3<E1,E2,E3> t3(E1 e1, E2 e2, E3 e3) {
		return new T3<E1,E2,E3>(e1, e2, e3);
	}
	
	public T3(E1 e1, E2 e2, E3 e3) {
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
	}
	
	final public E1 e1;
	final public E2 e2;
	final public E3 e3;

}
