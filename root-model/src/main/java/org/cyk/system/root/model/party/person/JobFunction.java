package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @NoArgsConstructor @Entity
/**
 * Routine set of tasks or activities undertaken by a person
 * @author Christian Yao Komenan
 *
 */
public class JobFunction extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public JobFunction(String code, String libelle) {
		super(code, libelle,null, null);
	}
	
	
}
