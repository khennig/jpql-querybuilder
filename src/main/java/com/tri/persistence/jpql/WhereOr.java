package com.tri.persistence.jpql;

import com.tri.persistence.jpql.utils.StringUtils;

/**
 * OR operator for SQL Where clause.
 */
public class WhereOr extends WhereItems {

	/**
	 * Constructor
	 * 
	 * @param builder
	 *            {@link QueryBuilder} to associate
	 */
	public WhereOr(final QueryBuilder builder) {
		super(builder);
	}

	@Override
	public String render() {
		final StringBuilder result = new StringBuilder();

		for (int i = 0; i < items.size(); i++) {

			final String build = items.get(i).render();
			if (StringUtils.isBlank(build) == false) {

				if (result.length() > 0) {
					result.append(" OR ");
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