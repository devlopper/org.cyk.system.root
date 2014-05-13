package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Entity
public class ContactManager extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 8675998527199168142L;
		
	public ContactManager() {}

}
