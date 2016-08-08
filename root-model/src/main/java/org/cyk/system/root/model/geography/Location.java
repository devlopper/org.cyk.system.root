package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
public class Location extends Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne private LocationType type;
	
	@ManyToOne private Locality locality;

	public Location() {}
	
	public Location(ContactCollection manager,Locality locality) {
		super(manager, null);
		this.locality = locality;
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
	public static final String FIELD_COMMENT = "comment";
}
