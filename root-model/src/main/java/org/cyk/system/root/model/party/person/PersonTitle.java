package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @NoArgsConstructor @Entity
/**
 * A prefix or suffix added to someone's name in certain contexts
 * @author Christian Yao Komenan
 *
 */
public class PersonTitle extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public static final String MISTER = "MISTER1";
	public static final String MADAM = "MADAM1";
	public static final String MISS = "MISS1";
	public static final String DOCTOR = "DOCTOR1";
	
	public PersonTitle(String code, String libelle) {
		super(code, libelle,null, null);
	}
	
	
}
