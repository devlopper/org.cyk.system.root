package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractActorReport<MODEL> extends AbstractIdentifiableReport<MODEL> implements Serializable {

	private static final long serialVersionUID = -7349146237275151269L;

	protected PersonReport person = new PersonReport();
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		if(source==null){
			
		}else{
			//person.setSource(((AbstractActor)source).getPerson());
		}
	}
	
	@Override
	public void generate() {
		super.generate();
		person.generate();
	}
	
}
