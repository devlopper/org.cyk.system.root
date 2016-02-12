package org.cyk.system.root.service.impl.unit;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.markuplanguage.MarkupLanguageBusiness.UpdateTagArguments;
import org.cyk.system.root.business.impl.markuplanguage.MarkupLanguageBusinessBasedOnJDom2Impl;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MarkupLanguageBusinessUT extends AbstractBusinessUT {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private MarkupLanguageBusinessBasedOnJDom2Impl markupLanguageBusinessBasedOnJDom2;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(markupLanguageBusinessBasedOnJDom2);
	}
	
	//@Test
	public void findTagJDom2() {
		//File base = new File(System.getProperty("user.dir"));
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\xml");
		String text = null;
		try {
			text = IOUtils.toString(new FileInputStream(new File(directory, "arquillian.xml")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		UpdateTagArguments updateTagArguments = new UpdateTagArguments();
		updateTagArguments.getFindTagArguments().addTagName("container","configuration","property");
		updateTagArguments.getAttributes().put("name", "OLLLLAAAAAAAAAAAAAAAAAAMyNewValue");  
		updateTagArguments.setText("Olalaa new value again here");
		text = markupLanguageBusinessBasedOnJDom2.updateTag(text, updateTagArguments);
		
		System.out.println(text);
	}
	
	@Test
	public void updateJrxml() {
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\jrxml");
		String text = null;
		try {
			text = IOUtils.toString(new FileInputStream(new File(directory, "1.jrxml")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println(text);
		
		UpdateTagArguments updateTagArguments = new UpdateTagArguments();
		updateTagArguments.getFindTagArguments().addTagName("parameter");
		updateTagArguments.getFindTagArguments().setAttributes("name","CYK_RANKABLE");
		
		updateTagArguments.getAttributes().put("name", "OLLLLAAAAAAAAAAAAAAAAAAMyNewValue");  
		text = markupLanguageBusinessBasedOnJDom2.updateTag(text, updateTagArguments);
		
		//System.out.println(text);
	}

}
