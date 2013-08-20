package com.tri.persistence.jpql;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.persistence.Query;

import org.junit.Test;

import com.tri.persistence.jpql.ParameterObject;

public class ParameterObjectTest {

	@Test
	public void apply() {
		// setup
		final String name = "n1";
		final Object object = new Object();
		final ParameterObject parameter = new ParameterObject(name, object);
		final Query query = mock(Query.class);
		// test
		parameter.apply(query);
		// check
		verify(query, times(1)).setParameter(eq(name), eq(object));
	}

}
