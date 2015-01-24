package org.cyk.system.root.model.party.person;

import java.io.Serializable;

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
	
	@Override
	public String toString() {
		return person.toString();
	}
}
