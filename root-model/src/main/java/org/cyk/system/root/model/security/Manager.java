package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
public class Manager extends Role implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	public Manager() {
		super(MANAGER, "Manager");
	}

	
}