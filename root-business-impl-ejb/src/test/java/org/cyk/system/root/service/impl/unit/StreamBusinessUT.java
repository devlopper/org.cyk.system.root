package org.cyk.system.root.service.impl.unit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.cyk.system.root.business.impl.file.StreamBusinessImpl;
import org.cyk.system.root.model.Mime;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class StreamBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private StreamBusinessImpl streamBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(streamBusiness);
		
	}
	
	@Test
	public void mergePdf() {
		File base = new File(System.getProperty("user.dir"));
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\pdf");
		Collection<InputStream> inputStreams = new ArrayList<>();
		try {
			inputStreams.add(new FileInputStream(new File(directory, "1.pdf")));
			inputStreams.add(new FileInputStream(new File(directory, "2.pdf")));
			ByteArrayOutputStream outputStream = streamBusiness.merge(inputStreams, Mime.PDF);
			FileUtils.writeByteArrayToFile(new File(base, "target/"+System.currentTimeMillis()+".pdf"), outputStream.toByteArray());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}