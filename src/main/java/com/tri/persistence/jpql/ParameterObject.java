package com.tri.persistence.jpql;

import javax.persistence.Query;

/**
 * {@code ParameterObject} objects are immutable if passed value is immutable.
 * 
 * @see Parameter
 */
public class ParameterObject extends Parameter<Object> {

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param value
	 *            parameter value, pass an immutable object or pass a copy not
	 *            used any further
	 */
	public ParameterObject(final String name, final Object value) {
		super(name, value);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param value
	 *            parameter value, pass an immutable object or pass a copy not
	 *            used any further
	 */
	public ParameterObject(final int position, final Object value) {
		super(position, value);
	}

	@Override
	public void apply(final Query query) {
		if (name != null) {
			query.setParameter(name, value);
		} else if (position != null) {
			query.setParameter(position, value);
		} else {
			throw new IllegalStateException(
					"Missing parameter name or position");
		}
	}

}