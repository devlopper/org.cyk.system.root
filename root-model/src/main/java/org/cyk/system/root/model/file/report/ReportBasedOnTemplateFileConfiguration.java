package org.cyk.system.root.model.file.report;

import java.util.Collection;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter
public abstract class ReportBasedOnTemplateFileConfiguration<IDENTIFIABLE extends AbstractIdentifiable,REPORT extends ReportBasedOnTemplateFile<?>> extends AbstractReportConfiguration<IDENTIFIABLE,REPORT> {

	public ReportBasedOnTemplateFileConfiguration(String identifier) {
		super(identifier);
	}
	
	public abstract <MODEL> REPORT build(Class<MODEL> aClass,Collection<MODEL> models,String fileExtension,Boolean print,Map<String,String[]> parameters);
	
}
