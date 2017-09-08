package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;
import org.cyk.utility.common.helper.ClassHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractActorReportTemplateFile<MODEL,ACTOR_REPORT extends AbstractActorReport<?>> extends AbstractReportTemplateFile<MODEL> implements Serializable {

	private static final long serialVersionUID = -7349146237275151269L;

	protected ACTOR_REPORT actor;
	
	public AbstractActorReportTemplateFile() {
		actor = createActorReport();
	}
	
	@Override
	public void generate() {
		super.generate();
		actor.generate();
	}
	
	@SuppressWarnings("unchecked")
	protected ACTOR_REPORT createActorReport(){
		try {
			return (ACTOR_REPORT) ClassHelper.getInstance().getParameterAt(getClass(), 1,AbstractActorReport.class).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
