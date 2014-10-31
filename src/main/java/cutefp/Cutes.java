package cutefp;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

import cutefp.func.F;
import cutefp.func.Predicate;

public class Cutes {

	/**
	 * 
	 * @param args
	 * @return
	 */
	public static <R> CuteIterable<R> fromArray(R... args) {
		return new CuteIterable<R>(Arrays.asList(args));
	}
	
	/**
	 * 
	 * @param args
	 * @return
	 */
	public static <R> CuteIterable<R> from(Iterable<R> arg) {
		return new CuteIterable<R>(arg);
	}

	/**
	 * generates a getter
	 */
	public static <A,B> F<A,B> getter(final String propertyName) {
		return new F<A,B>() {
			public B apply(A a) {
				try {
					Class<? extends A> clazz = (Class<? extends A>)a.getClass();
					Field field = clazz.getDeclaredField(propertyName);
					field.setAccessible(true);
					return (B)(field.get(a));
				} catch (NoSuchFieldException e) {
					throw new NoSuchFieldError(e.getMessage());
				} catch (IllegalArgumentException e) {
					throw new NoSuchFieldError(e.getMessage());
				} catch (IllegalAccessException e) {
					throw new NoSuchFieldError(e.getMessage());
				} catch (SecurityException e) {
					throw new NoSuchFieldError(e.getMessage());
				}
			}
		};
	}
	
	/**
	 * generates a checker
	 */
	public static <A> Predicate<A> is(final String propertyName) {
		F<A,Boolean> f = getter(propertyName);
		return (Predicate<A>)f;
	}
	
	/**
	 * generates a equality checker for a field
	 */
	public static <A,B> Predicate<A> eq(final String propertyName, final B val) {
		return new Predicate<A>() {
			public Boolean apply(A a) {
				try {
					Class<? extends A> clazz = (Class<? extends A>)a.getClass();
					Field field = clazz.getDeclaredField(propertyName);
					field.setAccessible(true);
					B thisVal = (B)(field.get(a));
					if (thisVal == null && val == null) {
						return true;
					}
					if (thisVal != null) {
						return thisVal.equals(val);
					} 
					return val.equals(thisVal);
				} catch (NoSuchFieldException e) {
					throw new NoSuchFieldError(e.getMessage());
				} catch (IllegalArgumentException e) {
					throw new NoSuchFieldError(e.getMessage());
				} catch (IllegalAccessException e) {
					throw new NoSuchFieldError(e.getMessage());
				} catch (SecurityException e) {
					throw new NoSuchFieldError(e.getMessage());
				}
			}
		};
	}


	/**
	 * generates a equality checker
	 */
	public static <A> Predicate<A> same(final A obj) {
		return new Predicate<A>() {
			public Boolean apply(A a) {
				if (a == null && obj == null) {
					return true;
				}
				if (a != null) {
					return a.equals(obj);
				} 
				return obj.equals(a);
			}
		};
	}
}
