package com.tri.persistence.jpql;

/**
 * Single textual SQL Where Clause predicate. E.g. a comparison predicate like
 * {@code field=:whatever}.
 */
public class WhereTextual extends WhereItem {

	String predicate;

	/**
	 * Constructor
	 * 
	 * @param builder
	 *            {@link QueryBuilder} to associate
	 * @param predicate
	 *            textual comparison predicate, e.g. {@code field=:whatever}
	 */
	public WhereTextual(final QueryBuilder builder, final String predicate) {
		super(builder);
		this.predicate = predicate;
	}

	@Override
	public String render() {
		return predicate;
	}

}