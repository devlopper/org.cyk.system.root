package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.utility.common.annotation.UIField;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class PhoneNumber extends Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne @UIField
	private PhoneNumberType type;
	
	@ManyToOne @UIField
	private Locality country;
	
	@UIField
	private String number;
	
	@ManyToOne @UIField
	private LocationType locationType;
	
	@Override
	public String toString() {
		if(number==null || number.isEmpty())
			return null;
		return (country==null?"":("+"+country.getCode()+" "))+number+(type==null?"":" ("+type.getName()+")");
	}
}
