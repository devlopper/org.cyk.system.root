package org.cyk.system.root.persistence.impl.data;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractEnumerationTree;

@Getter @Setter @Entity @NoArgsConstructor
public class TypedPlace extends AbstractEnumerationTree<PlaceType> implements Serializable {
	
	public TypedPlace(TypedPlace parent, PlaceType type, String code) {
		super(parent, type, code);
	}

	public TypedPlace(TypedPlace parent, String code) {
		super(parent, code);
	}
	
	
	
}
