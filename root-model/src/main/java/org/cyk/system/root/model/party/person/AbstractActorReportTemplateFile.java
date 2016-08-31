package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;

@Getter @Setter
public abstract class AbstractActorReportTemplateFile<MODEL> extends AbstractIdentifiableReport<MODEL> implements Serializable {

	private static final long serialVersionUID = -7349146237275151269L;

	protected CommonActorReport commonActor = new CommonActorReport();
	
	@Override
	public void generate() {
		super.generate();
		commonActor.generate();
	}
	
}
