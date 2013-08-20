package com.tri.persistence.jpql;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Calendar;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.junit.Test;

import com.tri.persistence.jpql.ParameterCalendar;

public class ParameterCalendarTest {

	@Test
	public void apply() {
		// setup
		final Calendar calendar = Calendar.getInstance();
		final String name = "cn1";
		final ParameterCalendar parameter = new ParameterCalendar(name,
				calendar, TemporalType.DATE);
		final Query query = mock(Query.class);
		// test
		parameter.apply(query);
		// check
		verify(query, times(1)).setParameter(eq(name), eq(calendar),
				eq(TemporalType.DATE));
	}

}
