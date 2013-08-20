package com.tri.persistence.jpql;

import java.util.Date;

import javax.persistence.Query;

/**
 * <p>
 * {@literal JPQL} parameter holder. The query builder holds a list of
 * parameters and applies them during the build process. There is an
 * implementation for every parameter type, e.g. {@link Object}, {@link Date}
 * etc.
 * </p>
 * <p>
 * {@code Parameter} objects are (almost) immutable. Passed mutable value
 * objects changed via kept references break immutableness of {@code Parameter}
 * objects.
 * </p>
 * 
 * @param <T>
 *            the type of the parameter
 */
abstract public class Parameter<T> {

	final protected String name;

	final protected Integer position;

	final protected T value;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param value
	 */
	public Parameter(final String name, final T value) {
		this.name = name;
		this.position = null;
		this.value = value;
	}

	/**
	 * Constructor
	 * 
	 * @param position
	 * @param value
	 */
	public Parameter(final int position, final T value) {
		this.position = position;
		this.name = null;
		this.value = value;
	}

	/**
	 * Applies this parameter to given query.
	 * 
	 * @param query
	 */
	public abstract void apply(Query query);

}
