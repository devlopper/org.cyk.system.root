package org.cyk.system.root.model.file.report;

import java.util.Collection;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode(of="identifier")
public abstract class AbstractReportConfiguration<MODEL,REPORT extends AbstractReport<?>> {

	protected String identifier;
	
	public abstract REPORT build(Class<MODEL> aClass,Collection<MODEL> models,String fileExtension,Boolean print,Map<String,String[]> parameters);
	
}
