package com.tri.persistence.jpql;

import com.tri.persistence.jpql.utils.StringUtils;

/**
 * AND operator for a {@literal SQL} Where Clause.
 */
public class WhereAnd extends WhereItems {

	/**
	 * Constructor
	 * 
	 * @param builder
	 *            {@link QueryBuilder} to associate
	 */
	public WhereAnd(final QueryBuilder builder) {
		super(builder);
	}

	@Override
	public String render() {
		final StringBuilder result = new StringBuilder();

		for (WhereItem item : items) {
			final String build = item.render();
			if (StringUtils.isBlank(build) == false) {

				if (result.length() > 0) {
					result.append(" AND ");
				} else {
					result.append("(");
				}

				result.append(build);

			}
		}

		if (result.length() > 0) {
			result.append(")");
		}

		return result.toString();
	}

}