package org.cyk.system.root.persistence.impl.data;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractEnumerationNode;

@Getter @Setter @Entity @NoArgsConstructor
public class UnTypedPlace extends AbstractEnumerationNode implements Serializable {
	
	public UnTypedPlace(AbstractEnumerationNode parent, String code) {
		super(parent, code);
	}
	
}
