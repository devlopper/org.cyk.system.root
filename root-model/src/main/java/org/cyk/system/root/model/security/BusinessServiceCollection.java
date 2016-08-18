package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;

@Getter @Setter @Entity @NoArgsConstructor
public class BusinessServiceCollection extends AbstractCollection<BusinessService> implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	public static final String EVENT = "EVENT";
	public static final String FILE = "FILE";
	public static final String GEOGRAPHY = "GEOGRAPHY";
	public static final String INFORMATION = "INFORMATION";
	public static final String LANGUAGE = "LANGUAGE";
	public static final String MATHEMATICS = "MATHEMATICS";
	public static final String MESSAGE = "MESSAGE";
	public static final String NETWORK = "NETWORK";
	public static final String PARTY = "PARTY";
	public static final String TREE = "TREE";
	public static final String SECURITY = "SECURITY";
	public static final String TIME = "TIME";
	
}