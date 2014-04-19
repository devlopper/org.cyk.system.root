package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;

@Getter @Setter @NoArgsConstructor @Entity
public class Locality extends AbstractDataTree<LocalityType> implements Serializable  {

	public Locality(AbstractDataTree<LocalityType> parent, LocalityType type, String code) {
		super(parent, type, code);
	}

	
	
}
