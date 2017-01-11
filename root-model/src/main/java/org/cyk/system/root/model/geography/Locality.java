package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.utility.common.annotation.FieldOverride;

@Getter @Setter @NoArgsConstructor @Entity @FieldOverride(name=AbstractDataTree.FIELD_TYPE,type=LocalityType.class)
public class Locality extends AbstractDataTree<LocalityType> implements Serializable  {

	private static final long serialVersionUID = -6128937819261060725L;

	public static final String AFRICA = "AFRICA";
	public static final String AMERICA = "AMERICA";
	public static final String ASIA = "ASIA";
	public static final String AUSTRALIA = "AUSTRALIA";
	public static final String EUROPE = "EUROPA";
	
	private String residentName;
	
	public Locality(Locality parent, LocalityType type, String code,String name) {
		super(parent, type, code);
		setName(name);
	}
	
	public Locality(Locality parent, LocalityType type, String code) {
		super(parent,type,code);
	}
}
