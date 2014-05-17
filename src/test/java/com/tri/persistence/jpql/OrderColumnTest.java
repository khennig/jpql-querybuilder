package com.tri.persistence.jpql;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class OrderColumnTest {

	@Test
	public void renderDefault() {
		// setup
		OrderColumn oc = new OrderColumn("cn1");
		// test, check
		assertThat(oc.render()).isEqualTo("cn1");
	}

	@Test
	public void renderAsc() {

		// setup: ASC
		OrderColumn oc = new OrderColumn("cn1", Order.ASC);
		// test, check
		assertThat(oc.render()).isEqualTo("cn1 ASC");
	}

	@Test
	public void renderDesc() {
		// setup: DESC
		OrderColumn oc = new OrderColumn("cn1", Order.DESC);
		// test, check
		assertThat(oc.render()).isEqualTo("cn1 DESC");
	}

}
