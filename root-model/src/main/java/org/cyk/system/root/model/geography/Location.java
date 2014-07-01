package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.annotation.UIField;

@Getter @Setter @Entity
public class Location extends Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne @UIField @NotNull
	private Locality locality;
	
	@UIField(textArea=true,textRowCount=3)
	private String comment;
    
	public Location() {}
	
	public Location(ContactCollection manager,Locality locality ,String comment) {
		super(manager, null);
		this.locality = locality;
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return locality==null?super.toString():(locality.toString()+(StringUtils.isEmpty(comment)?"":" , "+comment));
	}
	
}
