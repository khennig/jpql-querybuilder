package com.tri.persistence.jpql;

import com.tri.persistence.jpql.utils.StringUtils;

/**
 * Internal string list to hold a {@literal SQL} Select Clause items with
 * support for Constructor Expressions.
 */
public class SelectQueryClause extends QueryClause {

	/**
	 * Constructor
	 * 
	 * @param queryBuilder
	 */
	SelectQueryClause(final QueryBuilder builder) {
		super(builder);
	}

	/**
	 * Adds an expression to this Select Clause with support for Constructor
	 * Expressions.
	 * 
	 * @param constructorClass
	 *            class to be use for constructor expression, make shure it has
	 *            a constructor that fits to the given {@literal SQL} Select
	 *            Items
	 * @param expression
	 *            expression used as constructor parameter, will be passed
	 *            unchanged to the appropriated constructor of the given class
	 * @return the query builder
	 */
	public <T> QueryBuilder add(final Class<T> constructorClass,
			final String expression) {
		return add((String) null, constructorClass, expression);
	}

	/**
	 * Adds an expression to this Select Clause with support for Constructor
	 * Expressions.
	 * 
	 * @param lhsStatement
	 *            left hand side of the statement, e.g. {@code DISTINCT}
	 * @param constructorClass
	 *            class to be use for Constructor Expression, make sure it has a
	 *            constructor that fits to the given {@literal SQL} Select Items
	 * @param expression
	 *            expression used as constructor parameter, will be passed
	 *            unchanged to the appropriated constructor of the given class
	 * @return the query builder
	 */
	public <T> QueryBuilder add(final String lhsStatement,
			final Class<T> constructorClass, final String expression) {

		StringBuilder itemBuilder = new StringBuilder();
		if (lhsStatement != null) {
			itemBuilder.append(lhsStatement);
			itemBuilder.append(" ");
		}
		itemBuilder.append("NEW ");
		itemBuilder.append(constructorClass.getName());
		itemBuilder.append("(");
		itemBuilder.append(expression);
		itemBuilder.append(")");

		items.add(itemBuilder.toString());

		return builder;

	}

	/**
	 * Adds expressions to this Select Clause with support for Constructor
	 * Expressions.
	 * 
	 * @param constructorClass
	 *            class to be use for constructor expression, make shure it has
	 *            a constructor that fits to the given {@literal SQL} Select
	 *            Items
	 * @param expressions
	 *            constructor parameters, will be passed as comma separated list
	 *            to the appropriated constructor of the given class
	 * @return the query builder
	 */
	public <T> QueryBuilder add(final Class<T> constructorClass,
			final String... expressions) {
		return add((String) null, constructorClass, expressions);
	}

	/**
	 * Adds expressions to this Select Clause with support for Constructor
	 * Expressions.
	 * 
	 * @param lhsStatement
	 *            left hand side of the statement, e.g. {@code DISTINCT}
	 * @param constructorClass
	 *            class to be use for constructor expression, make shure it has
	 *            a constructor that fits to the given {@literal SQL} Select
	 *            items
	 * @param expressions
	 *            constructor parameters, will be passed as comma separated list
	 *            to the appropriated constructor of the given class
	 * @return the query builder
	 */
	public <T> QueryBuilder add(final String lhsStatement,
			final Class<T> constructorClass, final String... expressions) {
		return add(lhsStatement, constructorClass,
				StringUtils.join(expressions));
	}

}