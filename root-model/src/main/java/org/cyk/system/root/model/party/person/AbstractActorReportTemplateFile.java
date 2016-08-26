package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractActorReportTemplateFile<MODEL> extends AbstractReportTemplateFile<MODEL> implements Serializable {

	private static final long serialVersionUID = -7349146237275151269L;

	protected CommonActorReport commonActor = new CommonActorReport();
	
	@Override
	public void generate() {
		super.generate();
		commonActor.generate();
	}
	
}
