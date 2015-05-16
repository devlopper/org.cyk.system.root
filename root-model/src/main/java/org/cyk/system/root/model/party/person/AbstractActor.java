package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @MappedSuperclass
public abstract class AbstractActor extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2742833783679362737L;

	@ManyToOne
	protected Person person;
		
	@Embedded
	protected Registration registration = new Registration();
	
	@Override
	public String toString() {
		//return person.toString()+" | "+registration;
		return person.toString();
	}
	
	@Override
	public String getUiString() {
		//return registration+" - "+person.getUiString();
		return person.getUiString();
	}
}
