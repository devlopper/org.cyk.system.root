package org.cyk.system.root.model.geography;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.UIField;

@Getter @Setter @Entity @NoArgsConstructor
public class ContactCollection extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 8675998527199168142L;
	
	@Transient @UIField(label="phone.number-short")
	private Collection<PhoneNumber> phoneNumbers;
	
	@Transient @UIField
    private Collection<Location> locations;
	
	@Transient @UIField
    private Collection<PostalBox> postalBoxs;
	
	@Override
	public String toString() {
	    StringBuilder s = new StringBuilder(super.toString()+"\r\n");
	    s.append("Phone Numbers : "+StringUtils.join(phoneNumbers,",")+"\r\n");
	    s.append("Locations : "+StringUtils.join(locations,",")+"\r\n");
	    return s.toString();
	}
	
}
