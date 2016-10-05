package org.cyk.system.root.business.impl.unit;

import java.util.Collection;
import java.util.regex.Matcher;

import org.cyk.system.root.business.impl.validation.DatabaseManagementSystemMessageProvider;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class DatabaseManagementSystemMessageProviderUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		
	}
	
	@Test
	public void duplicate(){
		Matcher matcher = DatabaseManagementSystemMessageProvider.Adapter.MySql.DUPLICATE_ENTRY_FOR_KEY.getMatcher("Hello Duplicate entry 'a@m.com' for key 'ADDRESS'");
		assertEquals(2, matcher.groupCount());
		assertEquals("a@m.com", matcher.group(1));
		assertEquals("ADDRESS", matcher.group(2));
	}

	@Test
	public void cannotDelete(){
		String string = "Cannot delete or update a parent row: a foreign key constraint fails (`test_guidb`.`personrelationship`, CONSTRAINT "
				+ "`FK_PERSONRELATIONSHIP_PERSON2_IDENTIFIER` FOREIGN KEY (`PERSON2_IDENTIFIER`) REFERENCES `party` (`IDENTIFIER`))";
		Matcher matcher = DatabaseManagementSystemMessageProvider.Adapter.MySql.CANNOT_DELETE_OR_UPDATE_PARENT_ROW
				.getMatcher(string);
		assertEquals(Boolean.TRUE, DatabaseManagementSystemMessageProvider.Adapter.MySql.CANNOT_DELETE_OR_UPDATE_PARENT_ROW.isStartMarkerPresent(string));
		assertEquals(6, matcher.groupCount());
		assertEquals("test_guidb", matcher.group(1));
		assertEquals("personrelationship", matcher.group(2));	
		assertEquals("FK_PERSONRELATIONSHIP_PERSON2_IDENTIFIER", matcher.group(3));	
		assertEquals("PERSON2_IDENTIFIER", matcher.group(4));	
		assertEquals("party", matcher.group(5));	
	}
}
