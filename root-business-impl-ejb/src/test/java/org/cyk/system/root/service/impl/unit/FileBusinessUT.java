package org.cyk.system.root.service.impl.unit;

import java.util.Collection;

import org.cyk.system.root.business.impl.file.FileBusinessImpl;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class FileBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private FileBusinessImpl fileBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(fileBusiness);
		
	}
	
	@Test
	public void findMime() {
		assertEquals("image/jpeg", fileBusiness.findMime("jpg"));
	}

	
	
}
