package org.cyk.system.root.service.impl.unit;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.markuplanguage.MarkupLanguageBusiness.UpdateTagArguments;
import org.cyk.system.root.business.impl.markuplanguage.MarkupLanguageBusinessBasedOnJDom2Impl;
import org.cyk.utility.common.Constant;
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
		updateTagArguments.getFindTagArguments().addTag("container").addTag("configuration").addTag("property");
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
		
		/*FindTagArguments findTagArguments = new FindTagArguments();
		findTagArguments.addTag("parameter","name","CYK_RANKABLE").addTag("defaultValueExpression");
		MarkupLanguageTag tag = markupLanguageBusinessBasedOnJDom2.findTag(text, findTagArguments);
		*/
		//debug(tag); 
		
		//System.out.println(text);
		
		UpdateTagArguments updateTagArguments;
		
		updateTagArguments = new UpdateTagArguments();
		updateTagArguments.getFindTagArguments().addTag("parameter",new String[]{"name","CYK_RANKABLE"}).addTag("defaultValueExpression");
		updateTagArguments.setText("false");  
		text = markupLanguageBusinessBasedOnJDom2.updateTag(text, updateTagArguments);
		
		updateTagArguments = new UpdateTagArguments();
		updateTagArguments.getFindTagArguments().addTag("detail").addTag("band",2).addTag("frame",0).addTag("componentElement",0)
			.addTag("table","http://jasperreports.sourceforge.net/jasperreports/components",0)
			.addTag("column","http://jasperreports.sourceforge.net/jasperreports/components",11);
		updateTagArguments.setAttributes("width","124"); 
		text = markupLanguageBusinessBasedOnJDom2.updateTag(text, updateTagArguments);
		
		updateTagArguments = new UpdateTagArguments();
		updateTagArguments.getFindTagArguments().addTag("detail").addTag("band",2).addTag("frame",0).addTag("componentElement",0)
			.addTag("table","http://jasperreports.sourceforge.net/jasperreports/components",0)
			.addTag("column","http://jasperreports.sourceforge.net/jasperreports/components",12);
		updateTagArguments.setAttributes("width","150"); 
		text = markupLanguageBusinessBasedOnJDom2.updateTag(text, updateTagArguments);
		
		updateTagArguments = new UpdateTagArguments();
		updateTagArguments.getFindTagArguments().addTag("style",new String[]{"name","title"});
		updateTagArguments.setAttributes("backcolor","yellow"); 
		text = markupLanguageBusinessBasedOnJDom2.updateTag(text, updateTagArguments);
		
		updateTagArguments = new UpdateTagArguments();
		updateTagArguments.getFindTagArguments().addTag("style",new String[]{"name","block header"});
		updateTagArguments.setAttributes("backcolor","yellow"); 
		text = markupLanguageBusinessBasedOnJDom2.updateTag(text, updateTagArguments);
		
		//System.out.println(text);
		try {
			FileUtils.writeStringToFile(new File(System.getProperty("user.dir")+"\\target\\generated\\1.jrxml"), text, Constant.ENCODING_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
