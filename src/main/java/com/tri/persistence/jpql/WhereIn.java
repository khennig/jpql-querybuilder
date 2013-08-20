package com.tri.persistence.jpql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Support for {@literal SQL} In Expressions in Where Clauses.
 */
public class WhereIn extends WhereItem {

	String expression;

	List<Object> collection;

	boolean not;

	/**
	 * Constructor
	 * 
	 * @param builder
	 *            {@link QueryBuilder} to associate
	 * @param expression
	 *            {@literal SQL} Expression, e.g. expression in
	 *            {@code expression IN (...)}, actually the left part of the
	 *            predicate
	 * @param collection
	 *            set of literal values
	 */
	public <V extends Object> WhereIn(final QueryBuilder builder,
			final String expression, final boolean not,
			final Collection<V> collection) {
		super(builder);
		if (collection.size() == 0) {
			throw new IllegalArgumentException(
					"Not empty collection required for IN expression ");
		}

		this.expression = expression;
		this.not = not;
		this.collection = new ArrayList<Object>(collection.size());
		for (Object value : collection) {
			this.collection.add(value);
		}
	}

	/**
	 * Constructor
	 * 
	 * @param builder
	 *            {@link QueryBuilder} to associate
	 * @param expression
	 *            {@literal SQL} expression, e.g. expression in
	 *            {@code expression IN (...)}, actually the left part of the
	 *            predicate
	 * @param collection
	 *            set of literal values
	 */
	public <V extends Object> WhereIn(final QueryBuilder builder,
			final String expression, final boolean not, final V[] collection) {
		super(builder);
		if (collection.length == 0) {
			throw new IllegalArgumentException(
					"Not empty array required for IN expression ");
		}

		this.expression = expression;
		this.not = not;
		this.collection = new ArrayList<Object>(collection.length);
		for (Object value : collection) {
			this.collection.add(value);
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final WhereIn clone = (WhereIn) super.clone();
		// TODO: Evaluate: Implement: null-out builder
		// deep copy
		clone.collection = new ArrayList<Object>(collection);
		return clone;
	}

	@Override
	public String render() {

		final StringBuilder result = new StringBuilder();

		for (Object value : collection) {

			if (result.length() > 0) {
				result.append(", ");
			}

			if (value instanceof Number) {
				result.append(value);
			} else if (value instanceof Enum<?>) {
				result.append(value.getClass().getName());
				result.append(".");
				result.append(((Enum<?>) value).name());
			} else {
				result.append("'");
				result.append(value);
				result.append("'");
			}

		}

		if (not == false) {
			result.insert(0, expression + " IN (");
		} else {
			result.insert(0, expression + " NOT IN (");
		}

		result.append(")");
		return result.toString();

	}

}