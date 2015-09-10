package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.DataTreeType;

@Getter @Setter @NoArgsConstructor @Entity
public class PersonRelationshipType extends DataTreeType implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public PersonRelationshipType(DataTreeType parent, String code,String label) {
		super(parent, code,label);
	}
	
	
}
