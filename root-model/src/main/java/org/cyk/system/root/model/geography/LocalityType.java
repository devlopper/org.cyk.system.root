package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;

@Getter @Setter @NoArgsConstructor @Entity 
public class LocalityType extends AbstractDataTreeType implements Serializable  {
	
	private static final long serialVersionUID = -6838401709866343401L;

	public LocalityType(LocalityType parent, String code,String label) {
		super(parent, code,label);
	}

}
