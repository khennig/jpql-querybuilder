package com.tri.persistence.jpql;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal string list to hold {@literal SQL} Statement Clause items, i.e.
 * {@code FROM} or {@code ORDER} parameters like {@code field=content}. Notice,
 * there is a special implementation for the Select Clause to support
 * Constructor Expressions.
 */
public class QueryClause implements Cloneable {

	List<String> items = new ArrayList<String>();

	QueryBuilder builder;

	/**
	 * Constructor
	 * 
	 * @param builder
	 */
	public QueryClause(final QueryBuilder builder) {
		this.builder = builder;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final QueryClause clone = (QueryClause) super.clone();
		// TODO: Evaluate: Implement: null-out builder
		// deep copy
		clone.items = new ArrayList<String>(items);
		return clone;
	}

	void setBuilder(final QueryBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Adds an item to this {@literal SQL} Clause.
	 * 
	 * @param item
	 *            clause item
	 * @return the query builder
	 */
	public QueryBuilder add(final String item) {
		items.add(item);
		return builder;
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

}
