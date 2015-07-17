package org.cyk.system.root.model.unit;


import java.util.Locale;

import org.cyk.system.root.model.EnumHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;

public class EnumHelperUT extends AbstractUnitTest {

	private static final long serialVersionUID = 1066827346943955468L;

	@Override
	protected void _execute_() {
		super._execute_();
		EnumHelper.getInstance().setDefaultLocale(Locale.FRENCH);
		assertEquals("Valeur 1", MyTestEnumHelper.V1.toString());
		EnumHelper.getInstance().setDefaultLocale(Locale.ENGLISH);
        assertEquals("Value 1", MyTestEnumHelper.V1.toString());
	}
	
	
	
}
