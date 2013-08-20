package com.tri.persistence.jpql;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.tri.persistence.jpql.utils.StringUtils;

/**
 * <p>
 * {@literal JPQL} query builder for building complex dynamic database queries
 * and statements in {@literal JPQL} without getting cluttered with complex
 * String building-If-Else fragments. The builder has a fluent {@literal API} to
 * provide for more readable code. It is just an query/statement string builder
 * and has no knowledge of any {@literal SQL} semantic.
 * </p>
 * <p>
 * Usage
 * </p>
 * <p>
 * Simple query:
 * </p>
 * 
 * <pre>
 * QueryBuilder builder = new QueryBuilder();
 * builder.select.add("e");
 * builder.from.add("Entity e");
 * builder.where.add("e.fieldname=:fieldname");
 * builder.setParameter("fieldname", "xyz");
 * TypedQuery<Long> query = builder.createQuery(entityManager, Entity.class)
 * return query.getResultList();
 * </pre>
 * <p>
 * Same query using fluent api:
 * </p>
 * 
 * <pre>
 * QueryBuilder builder = new QueryBuilder().select.add("e");
 * 	.from.add("Entity e").where.add("e.fieldname1=:fieldname2")
 * 	.add("e.fieldname2=:fieldname2");
 * return builder.setParameter("fieldname1", "xyz")
 * 	.setParameter("fieldname2", "xyz")
 * 	.createQuery(entityManager, Entity.class).getResultList();
 * </pre>
 * 
 * <p>
 * A dynamic query:
 * </p>
 * 
 * <pre>
 * String searchCriteria = "xyz";
 * QueryBuilder builder = QueryBuilder().select.add("e");
 * 	.from.add("Entity e").where.add("e.fieldname1=:fieldname2");
 * builder.setParameter("fieldname1", "xyz");
 * if (searchCriteria != null) {
 * 	builder.where.add("e.fieldname2=:fieldName2")
 * 	builder.setParameter("fieldname2", searchCriteria);
 * }
 * return builder.createQuery(entityManager, Entity.class).getResultList();
 * </pre>
 * <p>
 * Also check the helper methods of class {@link WhereItems}, as these methods
 * are most needed to build the Where Clause of an {@literal SQL} statement.
 * </p>
 * 
 * @author khennig@pobox.com
 */
public class QueryBuilder implements Cloneable {

	public SelectQueryClause select;

	public StatementItem update;

	public StatementItem deleteFrom;

	public QueryClause set;

	public QueryClause from;

	public WhereAnd where;

	public QueryClause order;

	public QueryClause group;

	private List<Parameter<?>> parameters;

	private QueryBuilder parent;

	/**
	 * Constructor
	 */
	public QueryBuilder() {
		select = new SelectQueryClause(this);
		update = new StatementItem(this);
		deleteFrom = new StatementItem(this);
		set = new QueryClause(this);
		from = new QueryClause(this);
		where = new WhereAnd(this);
		order = new QueryClause(this);
		group = new QueryClause(this);
		parameters = new ArrayList<Parameter<?>>();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final QueryBuilder clone = (QueryBuilder) super.clone();
		// TODO: Evaluate: Implement: null-out parent

		// deep copy
		clone.select = (SelectQueryClause) select.clone();
		clone.select.setBuilder(this);

		clone.update = (StatementItem) update.clone();
		clone.update.setBuilder(this);

		clone.deleteFrom = (StatementItem) deleteFrom.clone();
		clone.deleteFrom.setBuilder(this);

		clone.set = (QueryClause) set.clone();
		clone.set.setBuilder(this);

		clone.from = (QueryClause) from.clone();
		clone.from.setBuilder(this);

		clone.where = (WhereAnd) where.clone();
		clone.where.setBuilder(this);

		clone.order = (QueryClause) order.clone();
		clone.order.setBuilder(this);

		clone.group = (QueryClause) group.clone();
		clone.group.setBuilder(this);

		clone.parameters = new ArrayList<Parameter<?>>(parameters);

		return clone;
	}

	protected void setParent(final QueryBuilder parent) {
		this.parent = parent;
	}

	/**
	 * Returns the parent builder.
	 * 
	 * @return parent builder or null if there is none
	 */
	public QueryBuilder parent() {
		return parent;
	}

	/**
	 * Returns a JPA Query with all previously specified parameters added.
	 * 
	 * @param manager
	 *            JPA EntityManager
	 * @return JPA Query
	 */
	public Query createQuery(final EntityManager manager) {
		if (manager == null) {
			throw new NullPointerException("Entity Manager required");
		}

		final Query query = manager.createQuery(render());

		for (Parameter<?> parameter : parameters) {
			parameter.apply(query);
		}

		return query;
	}

	/**
	 * Returns a JPA TypedQuery with all previously specified parameters added.
	 * 
	 * @param manager
	 *            JPA EntityManager
	 * @return JPA Query
	 */
	public <T> TypedQuery<T> createQuery(final EntityManager manager,
			Class<T> type) {
		if (manager == null) {
			throw new NullPointerException("Entity Manager required");
		}

		TypedQuery<T> result = manager.createQuery(render(), type);

		for (Parameter<?> parameter : parameters) {
			parameter.apply(result);
		}

		return result;
	}

	/**
	 * Adds the given parameter to the builder. All parameters will added to the
	 * JPA Query returned by a call to {@link #createQuery(EntityManager)} or
	 * {@link #createQuery(EntityManager, Class)}.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @param temporalType
	 *            temporal type
	 * @return builder reference, fluent api support
	 */
	public QueryBuilder setParameter(final String name, final Calendar value,
			TemporalType temporalType) {
		return setParameter(new ParameterCalendar(name, value, temporalType));
	}

	/**
	 * Adds the given parameter to the builder. All parameters will added to the
	 * JPA Query returned by a call to {@link #createQuery(EntityManager)} or
	 * {@link #createQuery(EntityManager, Class)}.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @param temporalType
	 *            temporal type
	 * @return builder reference, fluent api support
	 */
	public QueryBuilder setParameter(final String name, final Date value,
			final TemporalType temporalType) {
		return setParameter(new ParameterDate(name, value, temporalType));
	}

	/**
	 * Adds the given parameter to the builder. All parameters will added to the
	 * JPA Query returned by a call to {@link #createQuery(EntityManager)} or
	 * {@link #createQuery(EntityManager, Class)}.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @return builder reference, fluent api support
	 */
	public QueryBuilder setParameter(final String name, final Object value) {
		return setParameter(new ParameterObject(name, value));
	}

	/**
	 * Adds given parameter to the builder. All parameters will added to the JPA
	 * Query returned by a call to {@link #createQuery(EntityManager)} or
	 * {@link #createQuery(EntityManager, Class)}.
	 * 
	 * @param parameter
	 *            parameter to add
	 * @return builder reference, fluent api support
	 */
	public QueryBuilder setParameter(final Parameter<?> parameter) {
		if (parent == null) {
			parameters.add(parameter);
		} else {
			parent.setParameter(parameter);
		}
		return this;
	}

	/**
	 * Adds all given parameters to the builder. All parameters will added to
	 * the JPA Query returned by a call to {@link #createQuery(EntityManager)}
	 * or {@link #createQuery(EntityManager, Class)}.
	 * 
	 * @param parameters
	 *            parameters to add
	 * @return builder reference, fluent api support
	 */
	public QueryBuilder setParameters(final List<Parameter<?>> parameters) {
		if (parent == null) {
			this.parameters.addAll(parameters);
		} else {
			parent.setParameters(parameters);
		}
		return this;
	}

	/**
	 * Returns all previously set parameters
	 * 
	 * @return list of parameters
	 */
	public List<Parameter<?>> getParameters() {
		return parameters;
	}

	/**
	 * Renders this object as JPQL query string.
	 * 
	 * @return the query as string
	 */
	public String render() {

		StringBuilder query = new StringBuilder();

		if (!select.isEmpty()) {
			query.append("SELECT ");
			query.append(StringUtils.join(select.items));
		}

		if (!deleteFrom.isEmpty()) {
			query.append("DELETE FROM ");
			query.append(deleteFrom.item);
		}

		if (!update.isEmpty()) {
			query.append("UPDATE ");
			query.append(update.item);

			if (!set.isEmpty()) {
				query.append(" SET ");
				query.append(StringUtils.join(set.items));
			}
		}

		if (!from.isEmpty()) {
			query.append(" FROM ");
			query.append(StringUtils.join(from.items));
		}

		if (!where.isEmpty()) {
			query.append(" WHERE ");
			query.append(where.render());
		}

		if (!group.isEmpty()) {
			query.append(" GROUP BY ");
			query.append(StringUtils.join(group.items));
		}

		if (order.isEmpty() == false) {
			query.append(" ORDER BY ");
			query.append(StringUtils.join(order.items));
		}

		return query.toString();

	}

}