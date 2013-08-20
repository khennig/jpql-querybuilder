package com.tri.persistence.jpql;

/**
 * Sub query support for where clause.
 */
public class WhereSubquery extends WhereItem {

	/**
	 * Left hand side of the predicate, i.e. expression and operator, e.g.
	 * {@code field IN} or {@code EXISTS}
	 */
	String lhsPredicate;

	QueryBuilder queryBuilder;

	/**
	 * Constructor
	 * 
	 * @param builder
	 *            {@link QueryBuilder} to associate
	 * @param lhsPredicate
	 *            left hand side of the predicate, i.e. expression and operator,
	 *            e.g. {@code field IN} or {@code EXISTS}
	 */
	public WhereSubquery(final QueryBuilder builder, final String lhsPredicate) {
		super(builder);
		this.lhsPredicate = lhsPredicate;
		queryBuilder = new QueryBuilder();
		queryBuilder.setParent(builder);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final WhereSubquery clone = (WhereSubquery) super.clone();
		// TODO: Evaluate: Implement: null-out builder
		// deep copy
		clone.queryBuilder = (QueryBuilder) clone.queryBuilder.clone();
		return clone;
	}

	void setBuilder(final QueryBuilder builder) {
		super.setBuilder(builder);
		queryBuilder.setParent(builder);
	}

	@Override
	public String render() {
		final StringBuilder result = new StringBuilder();

		result.append(lhsPredicate);
		result.append(" (");
		result.append(queryBuilder.render());
		result.append(")");

		return result.toString();
	}

	public QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

}
