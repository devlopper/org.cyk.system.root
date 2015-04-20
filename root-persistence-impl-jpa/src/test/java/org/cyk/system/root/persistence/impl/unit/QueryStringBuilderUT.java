package org.cyk.system.root.persistence.impl.unit;

import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.test.AbstractUnitTest;

public class QueryStringBuilderUT extends AbstractUnitTest {

	private static final long serialVersionUID = 3681555921786058917L;
	QueryStringBuilder builder = new QueryStringBuilder();
	
	@Override
	protected void _execute_() {
		super._execute_();
		queryStringEquals("select r from table r",builder.init().from("table").select());
		queryStringEquals("select r from table r where r.age = :age",builder.init().from("table").select().where("age"));
		queryStringEquals("select count(r.identifier) from table r",builder.init().from("table").select(Function.COUNT));
	}
	
	private void queryStringEquals(String query,QueryStringBuilder builder){
		assertEquals(query.toLowerCase(),builder.getValue().toLowerCase());
	}
	
}
