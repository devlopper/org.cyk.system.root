package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class PhoneNumber extends Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne @NotNull @JoinColumn(nullable=false)
	private PhoneNumberType type;
	
	@ManyToOne @NotNull @JoinColumn(nullable=false)
	private Country country;
	
	@ManyToOne
	private LocationType locationType;
	
	@NotNull @Column(nullable=false)
	private String number;
	
	@Override
	public String toString() {
		if(number==null || number.isEmpty())
			return null;
		return (country==null?"":("+"+country.getPhoneNumberCode()+" "))+number/*+(type==null?"":" ("+type.getName()+")")*/;
	}
	
	/**/
	
	public static final String FIELD_NUMBER = "number";
}
