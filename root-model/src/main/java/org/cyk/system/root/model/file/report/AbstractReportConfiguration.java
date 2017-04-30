package org.cyk.system.root.model.file.report;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode(of="identifier")
public abstract class AbstractReportConfiguration<MODEL,REPORT extends AbstractReport<?>> {

	protected String identifier;
	
}
