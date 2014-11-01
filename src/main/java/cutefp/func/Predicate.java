package cutefp.func;

public abstract class Predicate<A> implements F<A,Boolean> {
	public abstract Boolean apply(A x);
	
	/**
	 * Make new predicate which should satisfies one of this and all given predicates
	 * @return
	 */
	public Predicate<A> or(final Predicate<A>... predicates) {

		final Predicate<A> self = this;

		return new Predicate<A>() {
			public Boolean apply(A x) {
				if (self.apply(x)) {
					return true;
				}
				for (Predicate<A> pred : predicates) {
					if (pred.apply(x)) {
						return true;
					}
				}
				return false;
			}
		};
	}

	/**
	 * Make new predicate which should satisfies this and all given predicates
	 * @return
	 */
	public Predicate<A> and(final Predicate<A>... predicates) {

		final Predicate<A> self = this;

		return new Predicate<A>() {
			public Boolean apply(A x) {
				if (!self.apply(x)) {
					return false;
				}
				for (Predicate<A> pred : predicates) {
					if (!pred.apply(x)) {
						return false;
					}
				}
				return true;
			}
		};
	}

	/**
	 * Make new negative predicate
	 * @return
	 */
	public Predicate<A> not() {

		final Predicate<A> p = this;
		
		return new Predicate<A>() {
			public Boolean apply(A x) {
				return !(p.apply(x));
			}
		};
	}
}
