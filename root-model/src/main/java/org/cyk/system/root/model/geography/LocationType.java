package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @NoArgsConstructor @Entity 
//FIXME is that necessary? can be model? Home , office are location type which can be attached to another like party
//We can think it as a Quick Supplementaries Contacts of Some LocationType
public class LocationType extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public LocationType(String code, String libelle) {
		super(code, libelle,null, null);
	}
	
	
}
