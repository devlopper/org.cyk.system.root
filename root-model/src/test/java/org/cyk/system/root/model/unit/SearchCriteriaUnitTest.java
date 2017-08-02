package org.cyk.system.root.model.unit;


import org.cyk.system.root.model.time.Instant;
import org.cyk.utility.test.unit.AbstractUnitTest;

public class SearchCriteriaUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = 8008545189557409317L;

	@Override
	protected void _execute_() {
		super._execute_();
		Instant.SearchCriteria instantSearchCriteria = new Instant.SearchCriteria();
		System.out.println(instantSearchCriteria);
		instantSearchCriteria.getYear().setValue(new Short("15"));
		System.out.println(instantSearchCriteria);
	}
	
	
	
}
