package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractEnumerationTree;

@Getter @Setter @NoArgsConstructor @Entity
public class Locality extends AbstractEnumerationTree<LocalityType> implements Serializable  {

	public Locality(AbstractEnumerationTree<LocalityType> parent, LocalityType type, String code) {
		super(parent, type, code);
	}

	
	
}
