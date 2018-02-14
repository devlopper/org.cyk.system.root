package org.cyk.system.root.business.impl.unit;

import org.cyk.system.root.business.impl.helper.FieldHelper;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class FieldHelperUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@Test
	public void copy() {
		Sex sexSource = new Sex();
		sexSource.setCode("sex1");
		Sex sexDestination = new Sex();
		FieldHelper.getInstance().copy(sexSource, sexDestination);
		assertEquals("sex1", sexDestination.getCode());
	}

	@Test
	public void copyCode() {
		Sex sexSource = new Sex();
		sexSource.setCode("sex1");
		Sex sexDestination = new Sex();
		FieldHelper.getInstance().copy(sexSource, sexDestination,Boolean.FALSE,org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
				AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE));
		assertEquals("sex1", sexDestination.getCode());
	}
	
	@Test
	public void copyCodeAndName() {
		Sex sexSource = new Sex();
		sexSource.setCode("sex1");
		Sex sexDestination = new Sex();
		FieldHelper.getInstance().copy(sexSource, sexDestination,Boolean.FALSE,org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
				AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE),org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
						AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
		assertEquals("sex1", sexDestination.getCode());
	}
}
