package org.cyk.system.root.dao.impl.unit;

import static org.junit.Assert.assertEquals;

import org.cyk.system.root.dao.impl.QueryStringBuilder;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.test.AbstractUnitTest;

public class QueryStringBuilderUT extends AbstractUnitTest {

	QueryStringBuilder builder = new QueryStringBuilder();
	
	@Override
	protected void _execute_() {
		super._execute_();
		assertEquals("select r from table r".toLowerCase(),builder.init().from("table").select().getValue().toLowerCase());
		assertEquals("select r from table r where r.age = :age".toLowerCase(),builder.init().from("table").select().where("age").getValue().toLowerCase());
		assertEquals("select count(r.identifier) from table r".toLowerCase(),builder.init().from("table").select(Function.COUNT).getValue().toLowerCase());
	}
	
}
