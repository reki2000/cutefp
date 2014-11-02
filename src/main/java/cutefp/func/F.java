package cutefp.func;

/**
 * This class desribes function. following SML style, all functions can have only 1 argument.
 * For functions which take 2 or more arguments, pack them into one tuble class - 
 * "Tn" (x:2,3,...) class and its factory  "tn (n:2,3,..)"
 * 
 * @author reki
 *
 * @param <A>
 * @param <B>
 */
public interface F<A, B> {
	public B apply(A x);
}
