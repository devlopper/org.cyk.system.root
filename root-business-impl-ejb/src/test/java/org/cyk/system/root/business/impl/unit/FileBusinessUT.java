package org.cyk.system.root.business.impl.unit;

import java.io.FileInputStream;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.impl.file.FileBusinessImpl;
import org.cyk.system.root.model.file.File;
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

	@Test
	public void fileName() {
		java.io.File directory = new java.io.File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\pdf");
		File file = null;
    	try {
    		file = fileBusiness.process(IOUtils.toByteArray(new FileInputStream(new java.io.File(directory, "1.pdf"))), "this is my file.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("this is my file", file.getName());
	}
	
}
