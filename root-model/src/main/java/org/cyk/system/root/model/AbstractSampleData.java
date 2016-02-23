package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;
import org.cyk.system.root.model.file.report.LabelValueCollectionReport;
import org.cyk.utility.common.generator.RandomDataProvider;

public abstract class AbstractSampleData implements Serializable {

	private static final long serialVersionUID = -4226783729442698873L;

	protected static <TYPE> Collection<TYPE> generate(Class<TYPE> aClass,Integer count){
		return RandomDataProvider.generate(aClass, count);
	}
	
	protected static void setLabelValueCollection(AbstractReportTemplateFile<?> report,String name,String[] values){
		LabelValueCollectionReport labelValueCollectionReport = new LabelValueCollectionReport();
		report.getLabelValueCollections().add(labelValueCollectionReport);
		labelValueCollectionReport.setName(name);
		for(int i = 0; i< values.length ;  i = i + 2)
			labelValueCollectionReport.add(values[i], values[i+1]);
	}
	
}
