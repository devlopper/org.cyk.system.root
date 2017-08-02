package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @NoArgsConstructor @Entity
/**
 * Name of a position within an organization filled by a person
 * @author Christian Yao Komenan
 *
 */
public class JobTitle extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public JobTitle(String code, String libelle) {
		super(code, libelle,null, null);
	}
	
	
	
}
