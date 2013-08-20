package com.tri.persistence.jpql;

import java.util.Calendar;

import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * @see Parameter
 */
public class ParameterCalendar extends Parameter<Calendar> {

	final protected TemporalType temporalType;

	/**
	 * Constructor
	 * 
	 * @param position
	 * @param value
	 *            parameter value, will be cloned
	 * @param temporalType
	 */
	public ParameterCalendar(final int position, final Calendar value,
			final TemporalType temporalType) {
		super(position, (Calendar) value.clone());
		this.temporalType = temporalType;
	}

	/**
	 * @param name
	 * @param value
	 *            parameter value, will be cloned
	 * @param temporalType
	 */
	public ParameterCalendar(final String name, final Calendar value,
			final TemporalType temporalType) {
		super(name, (Calendar) value.clone());
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