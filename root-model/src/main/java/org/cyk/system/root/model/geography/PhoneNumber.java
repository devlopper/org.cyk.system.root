package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {PhoneNumber.FIELD_COUNTRY,PhoneNumber.FIELD_NUMBER})}) 
public class PhoneNumber extends Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne @JoinColumn(name=COLUMN_TYPE) @NotNull private PhoneNumberType type;
	
	@ManyToOne @JoinColumn(name=COLUMN_COUNTRY) @NotNull private Country country;
	
	@ManyToOne @JoinColumn(name=COLUMN_LOCATION_TYPE) private LocationType locationType;
	
	@NotNull @Column(nullable=false) private String number;
	
	@Override
	public String toString() {
		if(number==null || number.isEmpty())
			return null;
		return (country==null?"":("+"+country.getPhoneNumberCode()+" "))+number/*+(type==null?"":" ("+type.getName()+")")*/;
	}
	
	/**/
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_COUNTRY = "country";
	public static final String FIELD_LOCATION_TYPE = "locationType";
	public static final String FIELD_NUMBER = "number";
	
	public static final String COLUMN_TYPE = "type_";
	public static final String COLUMN_COUNTRY = "country";
	public static final String COLUMN_LOCATION_TYPE = "locationtype";
	
}
