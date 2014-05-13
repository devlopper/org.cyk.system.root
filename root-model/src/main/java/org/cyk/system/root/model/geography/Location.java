package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
public class Location extends Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Locality locality;
	
	private String comment;
    
	public Location() {}
	
	public Location(ContactManager manager,Locality locality ,String comment) {
		super(manager, null);
		this.locality = locality;
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return locality.toString();
	}
	
}
