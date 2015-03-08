package org.cyk.system.root.model.file.report;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter
public abstract class ReportConfiguration<IDENTIFIABLE extends AbstractIdentifiable,REPORT extends Report<?>> extends AbstractReportConfiguration<IDENTIFIABLE,REPORT> {

	public ReportConfiguration(String identifier) {
		super(identifier);
	}
	
}
