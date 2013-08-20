package com.tri.persistence.jpql;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.junit.Test;

import com.tri.persistence.jpql.ParameterDate;

public class ParameterDateTest {

	@Test
	public void apply() {
		// setup
		final Date date = new Date();
		final String name = "cn1";
		final ParameterDate parameter = new ParameterDate(name, date,
				TemporalType.DATE);
		final Query query = mock(Query.class);
		// test
		parameter.apply(query);
		// check
		verify(query, times(1)).setParameter(eq(name), eq(date),
				eq(TemporalType.DATE));
	}

}
