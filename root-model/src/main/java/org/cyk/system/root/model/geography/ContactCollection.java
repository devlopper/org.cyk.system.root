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
import org.cyk.system.root.model.ContentType;

@Getter @Setter @Entity @NoArgsConstructor
public class ContactCollection extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 8675998527199168142L;
	
	@Transient private Collection<PhoneNumber> phoneNumbers;
	@Transient private Collection<Location> locations;
	@Transient private Collection<PostalBox> postalBoxs;
	@Transient private Collection<ElectronicMail> electronicMails;
	@Transient private Collection<Website> websites;
	
	@Override
	public String toString() {
	    StringBuilder s = new StringBuilder(super.toString()+ContentType.DEFAULT.getNewLineMarker());
	    s.append("Phone Numbers : "+StringUtils.join(phoneNumbers,",")+ContentType.DEFAULT.getNewLineMarker());
	    s.append("Locations : "+StringUtils.join(locations,","));
	    return s.toString();
	}
	
	@Override
	public String getUiString() {
		StringBuilder s = new StringBuilder(super.toString()+ContentType.DEFAULT.getNewLineMarker());
	    s.append(StringUtils.join(phoneNumbers,",")+ContentType.DEFAULT.getNewLineMarker());
	    s.append(StringUtils.join(electronicMails,",")+ContentType.DEFAULT.getNewLineMarker());
	    s.append(StringUtils.join(locations,",")+ContentType.DEFAULT.getNewLineMarker());
	    s.append(StringUtils.join(postalBoxs,",")+ContentType.DEFAULT.getNewLineMarker());
	    s.append(StringUtils.join(websites,","));
	    return s.toString();
	}
	
}
