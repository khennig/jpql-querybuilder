package com.tri.persistence.jpql;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * Logical structure item of a {@literal SQL} Where Clause (actually an
 * {@literal SQL} predicate). There are implementations for logically
 * {@code AND}/{@code OR} ({@link WhereAnd}, {@link WhereOr}) collections and
 * sub-queries ( {@link WhereSubQuery}).
 * </p>
 * <p>
 * This class contains most needed helper methods to build the Where Clause of
 * an SQL Statement.
 * </p>
 */
abstract public class WhereItems extends WhereItem {

	protected ArrayList<WhereItem> items = new ArrayList<WhereItem>();

	/**
	 * Constructor
	 * 
	 * @param builder
	 *            {@link QueryBuilder} to associate
	 */
	public WhereItems(final QueryBuilder builder) {
		super(builder);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final WhereItems clone = (WhereItems) super.clone();
		// deep copy
		clone.items = new ArrayList<WhereItem>(items.size());
		for (WhereItem item : items) {
			clone.items.add((WhereItem) item.clone());
		}
		return clone;
	}

	/**
	 * Associate given {@link QueryBuilder} with this object and all of its
	 * items.
	 * 
	 * @param builder
	 *            {@link QueryBuilder} to associate
	 */
	void setBuilder(final QueryBuilder builder) {
		super.setBuilder(builder);
		for (WhereItem item : items) {
			item.setBuilder(builder);
		}
	}

	/**
	 * Add a {@link WhereItem} to this object.
	 * 
	 * @return this object
	 */
	public WhereItem add(final WhereItem item) {
		items.add(item);
		item.setBuilder(builder());
		return this;
	}

	/**
	 * Adds a simple textual comparison predicate.
	 * 
	 * @param predicate
	 *            comparison predicate, e.g. {@code field=:whatever}
	 * @return this object
	 */
	public WhereItems add(final String predicate) {
		items.add(new WhereTextual(builder(), predicate));
		return this;
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	/**
	 * Adds an {@literal SQL} {@code AND} clause.
	 * 
	 * @return the added {@code AND} clause as {@link WhereAnd} object
	 */
	public WhereAnd and() {
		final WhereAnd and = new WhereAnd(builder());
		items.add(and);
		return and;
	}

	/**
	 * Adds an {@literal SQL} {@code OR} clause.
	 * 
	 * @return the added {@code OR} clause as {@link WhereOr} object
	 */
	public WhereOr or() {
		final WhereOr or = new WhereOr(builder());
		items.add(or);
		return or;
	}

	/**
	 * Adds an {@literal SQL} {@code IN} predicate.
	 * 
	 * @param expression
	 *            {@literal SQL} expression, e.g. expression in
	 *            {@code expression IN (...)}, actually the left part of the
	 *            predicate
	 * @param collection
	 *            collection of literal values
	 * @return this object
	 */
	public <V extends Object> WhereItems in(final String expression,
			final Collection<V> collection) {
		items.add(new WhereIn(builder(), expression, false, collection));
		return this;
	}

	/**
	 * Adds an {@literal SQL} {@code IN} predicate.
	 * 
	 * @param expression
	 *            {@literal SQL} expression, e.g. expression in
	 *            {@code expression IN (...)}, actually the left part of the
	 *            predicate
	 * @param array
	 *            array literal values
	 * @return this object
	 */
	public <V extends Object> WhereItems in(final String expression,
			final V... array) {
		items.add(new WhereIn(builder(), expression, false, array));
		return this;
	}

	/**
	 * Adds a {@code NOT IN} predicate.
	 * 
	 * @param expression
	 *            SQL expression, e.g. expression in
	 *            {@code expression NOT IN (...)}, actually the left part of the
	 *            predicate
	 * @param collection
	 *            collection of literal values
	 * @return this object
	 */
	public <V extends Object> WhereItems notIn(final String expression,
			final Collection<V> collection) {
		items.add(new WhereIn(builder(), expression, true, collection));
		return this;
	}

	/**
	 * Adds a "NOT IN" predicate.
	 * 
	 * @param expression
	 *            SQL expression, e.g. "expression" of
	 *            "expression NOT IN (...)", actually the left part of the
	 *            predicate
	 * @param array
	 *            array of literal values
	 * @return this object
	 */
	public <V extends Object> WhereItems notIn(final String expression,
			final V... array) {
		items.add(new WhereIn(builder(), expression, true, array));
		return this;
	}

	/**
	 * Adds a sub-query predicate.
	 * 
	 * @param lhsPredicate
	 *            left hand side of the predicate, i.e. expression and operator,
	 *            e.g. "field IN" or "EXISTS"
	 * @return the added subquery builder
	 */
	public QueryBuilder subquery(final String lhsPredicate) {
		final WhereSubquery subquery = new WhereSubquery(builder(),
				lhsPredicate);
		items.add(subquery);
		return subquery.getQueryBuilder();
	}

}