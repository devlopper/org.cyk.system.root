package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;
import org.cyk.utility.common.generator.RandomDataProvider;

public abstract class AbstractSampleData implements Serializable {

	private static final long serialVersionUID = -4226783729442698873L;

	protected static <TYPE> Collection<TYPE> generate(Class<TYPE> aClass,Integer count){
		return RandomDataProvider.generate(aClass, count);
	}
	
	protected void addLabelValues(AbstractReportTemplateFile<?> reportTemplateFile,String name,String[][] values){
		reportTemplateFile.addLabelValues(name, values);
	}
	
}
