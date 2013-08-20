package com.tri.persistence.jpql;

/**
 * Internal string to hold an {@literal SQL} Update or Insert Clause item, i.e.
 * Update or Insert clause items. Notice, there is a special implementation for
 * the Select Clause to support Constructor Expressions.
 */
public class StatementItem implements Cloneable {

	String item;

	QueryBuilder builder;

	StatementItem(final QueryBuilder builder) {
		this.builder = builder;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO: Evaluate: Implement: null-out builder
		return super.clone();
	}

	void setBuilder(final QueryBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Adds an item to this {@literal SQL} clause.
	 * 
	 * @param item
	 *            clause item
	 * @return the query builder
	 */
	public QueryBuilder set(final String item) {
		this.item = item;
		return builder;
	}

	public boolean isEmpty() {
		return item == null;
	}

}
