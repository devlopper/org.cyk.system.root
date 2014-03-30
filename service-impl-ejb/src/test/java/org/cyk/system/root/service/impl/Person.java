package org.cyk.system.root.service.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Entity @AllArgsConstructor @NoArgsConstructor
public class Person extends AbstractIdentifiable implements Serializable {

	/**
	 * 
	 */ 
	
	private static final long serialVersionUID = -7061794989292809428L;
	@Column(unique=true)
	private String matricule;
	
	private String name;
	
	private String lastName;

	
	
}
