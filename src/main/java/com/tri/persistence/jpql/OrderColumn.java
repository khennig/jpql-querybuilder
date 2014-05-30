package com.tri.persistence.jpql;

/**
 * {@code OrderColumn} objects are immutable.
 */
public class OrderColumn {

	private final String column;

	private final Order order;

	public OrderColumn(final String column) {
		this(column, null);
	}

	public OrderColumn(final String column, final Order order) {
		if (column == null) {
			throw new NullPointerException("Column required");
		}
		this.column = column;
		this.order = order;
	}

	public String getColumn() {
		return column;
	}

	public Order getOrder() {
		return order;
	}

	/**
	 * Renders this object as {@literal JPQL} fragment.
	 * 
	 * @return OrderColumn rendered as String 
	 */
	public String render() {
		StringBuilder builder = new StringBuilder(column);
		if (order != null) {
			builder.append(" ").append(getOrder().name());
		}
		return builder.toString();
	}

}