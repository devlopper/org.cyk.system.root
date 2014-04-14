package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractEnumerationTreeType;

@Getter @Setter @NoArgsConstructor @Entity
public class LocalityType extends AbstractEnumerationTreeType implements Serializable  {

	public LocalityType(AbstractEnumerationTreeType parent, String code,String label) {
		super(parent, code,label);
	}

	
	
}
