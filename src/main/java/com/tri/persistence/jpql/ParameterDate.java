package com.tri.persistence.jpql;

import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * @see Parameter
 */
public class ParameterDate extends Parameter<Date> {

	final protected TemporalType temporalType;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param value
	 * @param temporalType
	 */
	public ParameterDate(final String name, final Date value,
			final TemporalType temporalType) {
		super(name, value);
		this.temporalType = temporalType;
	}

	/**
	 * Constructor
	 * 
	 * @param position
	 * @param value
	 * @param temporalType
	 */
	public ParameterDate(final int position, final Date value,
			final TemporalType temporalType) {
		super(position, value);
		this.temporalType = temporalType;
	}

	@Override
	public void apply(final Query query) {
		if (name != null) {
			query.setParameter(name, value, temporalType);
		} else if (position != null) {
			query.setParameter(position, value, temporalType);
		} else {
			throw new IllegalStateException(
					"Missing parameter name or position");
		}
	}

}