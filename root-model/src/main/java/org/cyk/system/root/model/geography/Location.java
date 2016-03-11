package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

@Getter @Setter @Entity
public class Location extends Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne private LocationType type;
	
	@ManyToOne private Locality locality;
	
	private String comment;//TODO should be renamed to otherDetails
    
	public Location() {}
	
	public Location(ContactCollection manager,Locality locality ,String comment) {
		super(manager, null);
		this.locality = locality;
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return getUiString();
	}
	
	@Override
	public String getUiString() {
		return locality==null?comment:(locality.getUiString()+(StringUtils.isEmpty(comment)?"":" , "+comment));
	}
	
	/**/
	
	public static final String FIELD_COMMENT = "comment";
}
