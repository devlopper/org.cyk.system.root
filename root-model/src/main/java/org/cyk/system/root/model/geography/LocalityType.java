package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.DataTreeType;

@Getter @Setter @NoArgsConstructor @Entity 
public class LocalityType extends DataTreeType implements Serializable  {

	public static final String COUNTRY = "COUNTRY";
	public static final String CITY = "CITY";
	public static final String CONTINENT = "CONTINENT";
	
	private static final long serialVersionUID = -6838401709866343401L;

	public LocalityType(DataTreeType parent, String code,String label) {
		super(parent, code,label);
	}

	
	
}
