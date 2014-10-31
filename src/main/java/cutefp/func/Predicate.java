package cutefp.func;

public interface Predicate<A> extends F<A,Boolean> {
	public Boolean apply(A x);
}
