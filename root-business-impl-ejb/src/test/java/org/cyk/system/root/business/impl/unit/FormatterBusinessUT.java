package org.cyk.system.root.business.impl.unit;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.cyk.system.root.business.impl.FormatterBusinessImpl;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.file.report.LabelValueCollectionReport;
import org.cyk.utility.common.Constant;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class FormatterBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private FormatterBusinessImpl formatterBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(formatterBusiness);
		
		formatterBusiness.registerFormatter(LabelValueCollectionReport.class, new LabelValueCollectionReport.Formatter());
	}
	
	@Test
	public void formatText() {
		
	}

	@Test
	public void formatHtml() {
		LabelValueCollectionReport labelValueCollectionReport = new LabelValueCollectionReport();
		labelValueCollectionReport.add(null, "Label 1", "Value 1");
		labelValueCollectionReport.add(null, "Label 2", "Value b");
		labelValueCollectionReport.add(null, "Label c", "Value 3");
		System.out.println(formatterBusiness.format(labelValueCollectionReport, ContentType.HTML));
	}
	
	@Test
	public void encodeUtf8(){
		try {
			System.out.println(new String("éè".getBytes(Constant.ENCODING_UTF8)));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
