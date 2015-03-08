package org.cyk.system.root.model.file.report;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class ReportTableConfiguration<MODEL,REPORT extends ReportTable<MODEL>> extends AbstractReportConfiguration<MODEL,REPORT> {

	public ReportTableConfiguration(String identifier) {
		super(identifier);
	}
	
	public abstract REPORT build(Class<MODEL> aClass,String fileExtension,Boolean print);

}
