package com.tri.persistence.jpql;

/**
 * Single item or logical structure item of a where clause (actually an
 * {@literal SQL} predicate). There are implementations for single where clause
 * items ({@link WhereTextual}), containers (i.e. list of where items, e.g.
 * {@link WhereItems}), logically {@code AND}/{@code OR} ({@link WhereAnd},
 * {@link WhereOr}) collections and sub-queries ({@link WhereSubquery}).
 */
abstract public class WhereItem implements Cloneable {

	private QueryBuilder builder;

	/**
	 * Constructor
	 * 
	 * @param builder
	 *            {@link QueryBuilder} to associate
	 */
	public WhereItem(final QueryBuilder builder) {
		this.builder = builder;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO: Evaluate: Implement: null-out builder
		return super.clone();
	}

	/**
	 * Renders this item as {@literal JPQL} fragment.
	 * 
	 * @return WhereItem rendered as String
	 */
	public abstract String render();

	/**
	 * Associate given {@link QueryBuilder} with this object.
	 * 
	 * @param builder
	 *            QueryBuilder to associate
	 */
	void setBuilder(final QueryBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Returns the {@link QueryBuilder} associated with this {@code WhereItem}.
	 * 
	 * @return associated {@link QueryBuilder}
	 */
	public QueryBuilder builder() {
		return builder;
	}

}