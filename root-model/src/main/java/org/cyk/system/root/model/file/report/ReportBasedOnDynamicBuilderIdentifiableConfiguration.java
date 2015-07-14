package org.cyk.system.root.model.file.report;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public abstract class ReportBasedOnDynamicBuilderIdentifiableConfiguration<IDENTIFIABLE extends AbstractIdentifiable,MODEL> implements Serializable {

	private static final long serialVersionUID = -8751105383094925100L;

	private String reportBasedOnDynamicBuilderIdentifier;
	private Class<? extends AbstractIdentifiable> identifiableClass;
	private Class<?> modelClass;
	
	public Boolean useCustomIdentifiableCollection(){
		return Boolean.FALSE;
	}
	
	public Collection<? extends IDENTIFIABLE> identifiables(ReportBasedOnDynamicBuilderParameters<MODEL> parameters){
		return null;
	}
	public abstract MODEL model(IDENTIFIABLE identifiable);
	
}
