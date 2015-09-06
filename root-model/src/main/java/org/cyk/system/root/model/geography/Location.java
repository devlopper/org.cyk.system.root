package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

@Getter @Setter @Entity
public class Location extends Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	@ManyToOne
	private LocationType type;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	@ManyToOne
	private Locality locality;
	
	@Input
	@InputTextarea
	private String comment;
    
	public Location() {}
	
	public Location(ContactCollection manager,Locality locality ,String comment) {
		super(manager, null);
		this.locality = locality;
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return locality==null?comment:(locality.toString()+(StringUtils.isEmpty(comment)?"":" , "+comment));
	}
	
}
