package org.cyk.system.root.model.file.report;

import java.io.Serializable;

import org.cyk.system.root.model.globalidentification.GlobalIdentifierReport;
import org.cyk.utility.common.generator.AbstractGeneratable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractIdentifiableReport<MODEL> extends AbstractGeneratable<MODEL> implements Serializable {
	  
	private static final long serialVersionUID = 5632592320990657808L;

	protected GlobalIdentifierReport globalIdentifier = new GlobalIdentifierReport();
	
	@Override
	public void generate() {
		globalIdentifier.generate();
	}
	
}
