package com.tri.persistence.jpql;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.tri.persistence.jpql.QueryBuilder;

public class QueryBuilderTest {

	public enum ENUMTEST {
		A, B;
	}

	@Test
	public void renderSelect() {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.select.add("field");
		builder.from.add("entity1");
		builder.from.add("entity2");
		builder.where.add("field='content'");
		builder.order.add("field");
		// test, check
		assertThat(builder.render()).isEqualTo(
				"SELECT field FROM entity1, entity2 WHERE "
						+ "(field='content') ORDER BY field");
	}

	@Test
	public void renderSelectFluent() {
		QueryBuilder builder = new QueryBuilder();
		builder.select.add("field").from.add("column").order.add("field").where
				.add("field=content").add("field=1").add("field=2");
		// test, check
		assertThat(builder.render()).isEqualTo(
				"SELECT field FROM column WHERE (field=content "
						+ "AND field=1 AND field=2) ORDER BY field");
	}

	@Test
	public void and() {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.select.add("field");
		builder.from.add("column");
		builder.where.add("field=content");
		builder.where.and().add("field=1").add("field=2");
		builder.order.add("field");
		// test, check
		assertThat(builder.render()).isEqualTo(
				"SELECT field FROM column WHERE (field=content "
						+ "AND (field=1 AND field=2)) ORDER BY field");
	}

	@Test
	public void or() {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.select.add("field");
		builder.from.add("column");
		builder.where.add("field=content");
		builder.where.or().add("field=1").add("field=2");
		builder.order.add("field");
		// test, check
		assertThat(builder.render()).isEqualTo(
				"SELECT field FROM column WHERE (field=content "
						+ "AND (field=1 OR field=2)) ORDER BY field");

	}

	@Test
	public void addConstructorExpression() {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.select.add(ConstructorExpressionTest.class, "field1", "field2").from
				.add("column").where.add("field1=content");
		// test, check
		assertThat(builder.render()).isEqualTo(
				"SELECT NEW " + ConstructorExpressionTest.class.getName()
						+ "(field1, field2) FROM column "
						+ "WHERE (field1=content)");

	}

	@Test
	public void addSubquery() {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.select.add("field").from.add("column").where.add(
				"field=content").subquery("anotherfield IN").select
				.add("subfield").from.add("subcolumn").where
				.add("subfield=subcontent");
		// test, check
		assertThat(builder.render())
				.isEqualTo(
						"SELECT field FROM column WHERE (field=content AND anotherfield IN ("
								+ "SELECT subfield FROM subcolumn WHERE (subfield=subcontent)))");
	}

	@Test
	public void addInWithString() {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.select.add("field");
		builder.from.add("column");
		builder.where.in("field", new String[] { "A", "B", "C" });
		// test, check
		assertThat(builder.render()).isEqualTo(
				"SELECT field FROM column WHERE (field IN ('A', 'B', 'C'))");
	}

	@Test
	public void addInWithIntegers() {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.select.add("field");
		builder.from.add("column");
		builder.where.in("field", new Integer[] { 1, 2, 3 });
		// test, check
		assertThat(builder.render()).isEqualTo(
				"SELECT field FROM column WHERE (field IN (1, 2, 3))");
	}

	@Test
	public void addInWithEnums() {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.select.add("field");
		builder.from.add("column");
		builder.where.in("field", ENUMTEST.values());
		// test, check
		assertThat(builder.render()).isEqualTo(
				"SELECT field FROM column " + "WHERE (field IN ("
						+ ENUMTEST.class.getName() + "." + ENUMTEST.A.name()
						+ ", " + ENUMTEST.class.getName() + "."
						+ ENUMTEST.B.name() + "))");
	}

	@Test
	public void cloneSelectBuilder() throws CloneNotSupportedException {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.select.add("field1");
		builder.select.add("field2");
		builder.from.add("column");
		builder.where.add("field1='content'");
		builder.order.add("field2");
		// test
		final QueryBuilder clone = (QueryBuilder) builder.clone();
		// check
		assertThat(clone.render()).isEqualTo(
				"SELECT field1, field2 FROM column WHERE (field1='content') "
						+ "ORDER BY field2");
	}

	@Test
	public void cloneUpdateBuilder() throws CloneNotSupportedException {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.update.set("Entity e");
		builder.set.add("e.field1=:'content1'");
		builder.where.add("e.field2='content2'");
		// test
		QueryBuilder clone = (QueryBuilder) builder.clone();
		// check
		assertThat(clone.render()).isEqualTo(
				"UPDATE Entity e SET e.field1=:'content1' "
						+ "WHERE (e.field2='content2')");
	}

	@Test
	public void cloneDeleteBuilder() throws CloneNotSupportedException {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.deleteFrom.set("Entity e");
		builder.where.add("e.field2='content2'");
		// test
		QueryBuilder clone = (QueryBuilder) builder.clone();
		// check
		assertThat(clone.render()).isEqualTo(
				"DELETE FROM Entity e WHERE (e.field2='content2')");
	}

	@Test
	public void renderUpdate() {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.update.set("Entity e");
		builder.set.add("e.field1='content1'");
		builder.set.add("e.field2='content2'");
		builder.where.add("e.field3='content3'");
		// test, check
		assertThat(builder.render()).isEqualTo(
				"UPDATE Entity e SET e.field1='content1', e.field2='content2' "
						+ "WHERE (e.field3='content3')");
	}

	@Test
	public void renderDelete() {
		// setup
		QueryBuilder builder = new QueryBuilder();
		builder.deleteFrom.set("Entity e");
		builder.where.add("e.field2='content2'");
		// test, check
		assertThat(builder.render()).isEqualTo(
				"DELETE FROM Entity e WHERE (e.field2='content2')");
	}

	/**
	 * Test class for constructor expression test in testBuild().
	 */
	public class ConstructorExpressionTest {

		@SuppressWarnings("unused")
		private String field;

		@SuppressWarnings("unused")
		private String field2;

		public ConstructorExpressionTest(String field) {
			this.field = field;
		}

		public ConstructorExpressionTest(String field, String field2) {
			this.field = field;
			this.field2 = field2;
		}

	}

}