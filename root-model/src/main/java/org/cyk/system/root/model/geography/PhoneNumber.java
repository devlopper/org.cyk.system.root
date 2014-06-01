package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.utility.common.annotation.UIField;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class PhoneNumber extends Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne @UIField @NotNull
	private PhoneNumberType type;
	
	@ManyToOne @UIField @NotNull
	private Locality country;
	
	@UIField(label="phone.number") @NotNull
	private String number;
	
	@ManyToOne @UIField(label="location") @NotNull
	private LocationType locationType;
	
	@Override
	public String toString() {
		if(number==null || number.isEmpty())
			return null;
		return (country==null?"":("+"+country.getCode()+" "))+number+(type==null?"":" ("+type.getName()+")");
	}
}
