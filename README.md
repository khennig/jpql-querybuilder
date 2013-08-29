# jpql-querybuilder

Create dynamic JPQL queries with a fluent API without cluttering code with
String concatenations. If you don't like the JPA Criteria API you may like
the JPQL Querybuilder as it is much closer to SQL. 

## Using JPQL Querybuilder
Simple query

```java
QueryBuilder builder = new QueryBuilder();
builder.select.add("e");
builder.from.add("Entity e");
builder.where.add("e.fieldname=:fieldname");
builder.setParameter("fieldname", "xyz");
TypedQuery<Entity> query = builder.createQuery(entityManager, Entity.class);
return query.getResultList();
```

Same query using fluent api

```java
return new QueryBuilder().select.add("e").from.add("Entity e").where
    .add("e.fieldname=:fieldname").builder()
    .setParameter("fieldname", "xyz")
    .createQuery(entityManager, Entity.class).getResultList();
```

A dynamic query

```java
String searchCriteria = "xyz";
QueryBuilder builder = new QueryBuilder().select.add("e").from
    .add("Entity e").where.add("e.fieldname1=:fieldname1")
    .builder().setParameter("fieldname1", "xyz");
if (searchCriteria != null) {
    builder.where.add("e.fieldname2=:fieldName2");
    builder.setParameter("fieldname2", searchCriteria);
}
return builder.createQuery(entityManager, Entity.class).getResultList();
```

## Building it
JPQL Querybuilder uses Apache Maven as Build Tool 

```bash
git clone https://github.com/khennig/jpql-querybuilder.git
cd jpql-querybuilder
mvn install
```

## Adding it to your project
Add the following dependency to pom.xml:/project/dependencies

```xml
<dependency>
    <groupId>com.tri-systems.persistence</groupId>
    <artifactId>jpql-querybuilder</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## License
[MIT License](http://www.opensource.org/licenses/mit-license.php)
