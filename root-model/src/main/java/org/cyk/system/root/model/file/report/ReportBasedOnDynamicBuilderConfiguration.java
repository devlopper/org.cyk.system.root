package org.cyk.system.root.model.file.report;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class ReportBasedOnDynamicBuilderConfiguration<MODEL,REPORT extends ReportBasedOnDynamicBuilder<MODEL>> extends AbstractReportConfiguration<MODEL,REPORT> {

	public ReportBasedOnDynamicBuilderConfiguration(String identifier) {
		super(identifier);
	}
	
	public abstract REPORT build(Class<MODEL> aClass,String fileExtension,Boolean print,Map<String,String[]> parameters);

}
