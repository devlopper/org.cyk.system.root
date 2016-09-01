package org.cyk.system.root.model.party.person;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;
import org.cyk.utility.common.CommonUtils;

@Getter @Setter
public abstract class AbstractActorReportTemplateFile<MODEL,ACTOR_REPORT extends AbstractActorReport<?>> extends AbstractIdentifiableReport<MODEL> implements Serializable {

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
		return CommonUtils.getInstance().instanciate((Class<ACTOR_REPORT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]
				, null, null);
	}
}
