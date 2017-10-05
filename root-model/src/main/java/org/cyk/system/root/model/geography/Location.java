package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class Location extends Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne @JoinColumn(name=COLUMN_TYPE) private LocationType type;
	
	@ManyToOne @JoinColumn(name=COLUMN_LOCALITY) private Locality locality;

	@Embedded private GlobalPosition globalPosition;
	
	public Location() {}
	
	public Location(ContactCollection collection,Locality locality) {
		super(collection, null);
		this.locality = locality;
	}
	
	public GlobalPosition getGlobalPosition(){
		if(globalPosition==null)
			globalPosition = new GlobalPosition();
		return globalPosition;
	}
	
	@Override
	public String toString() {
		return getUiString();
	}
	
	@Override
	public String getUiString() {
		if(locality==null)
			if(StringUtils.isBlank(getOtherDetails()))
				return Constant.EMPTY_STRING;
			else
				return getOtherDetails();
		else
			if(StringUtils.isBlank(getOtherDetails()))
				return locality.getUiString();
			else
				return locality.getUiString()+Constant.CHARACTER_COMA+getOtherDetails();
	}
	
	/**/
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_LOCALITY = "locality";
	
	public static final String COLUMN_TYPE = "type_";
	public static final String COLUMN_LOCALITY = "locality";
}
