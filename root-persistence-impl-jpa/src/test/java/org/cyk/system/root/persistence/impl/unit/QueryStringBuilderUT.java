package org.cyk.system.root.persistence.impl.unit;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.test.unit.AbstractUnitTest;

public class QueryStringBuilderUT extends AbstractUnitTest {

	private static final long serialVersionUID = 3681555921786058917L;
	QueryStringBuilder builder = new QueryStringBuilder();
	
	@Override
	protected void _execute_() {
		super._execute_();
		queryStringEquals("select r from table r",builder().from("table").select());
		queryStringEquals("select r from table r where r.age = :age",builder().from("table").select().where("age"));
		queryStringEquals("select r from table r where r.age = :age and r.name = :name",builder().from("table").select().where("age").and("name"));
		queryStringEquals("select r from table r where r.age = :age and r.name = :name",builder().from("table").select().where("age").and().where("name"));
		queryStringEquals("select o from table o where o.age = :age",builder("o").from("table").select().where("age"));
		queryStringEquals("select count(r.identifier) from table r",builder().from("table").select(Function.COUNT));
		queryStringEquals("SELECT r FROM table r WHERE TYPE(r) in :classes",builder().from("table").select().whereClassIn());
		queryStringEquals("SELECT MyVar FROM table MyVar WHERE TYPE(MyVar) in :classes",builder("MyVar").from("table").select().whereClassIn("MyVar"));
		
		queryStringEquals("select r from table r where i",builder().from("table").select().whereString("i"));
		queryStringEquals("select r from table r where i and r.name = :name",builder().from("table").select().whereString("i").and("name"));
		queryStringEquals("select r from table r where i and r.name = :name or COUNT(r)=XXX",
				builder().from("table").select().whereString("i").and("name").whereString("or COUNT(r)=XXX"));
		
		queryStringEquals("select r from table r where mypredicate",builder().from("table").select().whereString("mypredicate"));
		queryStringEquals("select r from table r where exists(subquery)",builder().from("table").select().whereString("exists(subquery)"));
		queryStringEquals("select r from table r where exists(subquery)",builder().from("table").select().exists("subquery"));
		queryStringEquals("select r from table r where exists((p1) AND (p2)) AND p3",builder().from("table").select().exists("(p1) AND (p2)").and().whereString("p3"));
		queryStringEquals("select r from table r where exists((p1) AND (p2)) AND p3",builder().from("table").select()
				.exists("(p1) AND (p2)").and()
				.whereString("p3"));
		
		queryStringEquals("select r from table r where r.date between :fromvalue and :tovalue",builder().from("table").select().where().between("date"));
		
		QueryStringBuilder subQueryStringBuilder = new QueryStringBuilder();
		subQueryStringBuilder.init("t").from("table2").select().where("master", "r",Boolean.FALSE);
		queryStringEquals("select r from table r where exists(select t from table2 t where t.master = r)",
				builder().from("table").select().where().exists(subQueryStringBuilder));
		
		queryStringEquals("select r from person r where exists(select sr from locality sr where sr = r)",
				builder().from(Person.class).select().where()
					.exists().openSubQueryStringBuilder(Locality.class).closeSubQueryStringBuilder());
		
		queryStringEquals("select r from person r where exists(select sr from locality sr where sr = r and sr.person = r)",
				builder().from(Person.class).select().where()
					.exists().openSubQueryStringBuilder(Locality.class).and("person", "r", Boolean.FALSE).closeSubQueryStringBuilder());
		
		
	}
	
	private QueryStringBuilder builder(String root){
		return builder.init(root);
	}
	
	private QueryStringBuilder builder(){
		return builder.init();
	}
	
	private void queryStringEquals(String query,QueryStringBuilder builder){
		assertEquals(query.toLowerCase(),builder.getValue().toLowerCase());
	}
	
}
