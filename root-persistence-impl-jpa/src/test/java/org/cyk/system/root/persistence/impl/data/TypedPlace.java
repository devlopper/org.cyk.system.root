package org.cyk.system.root.persistence.impl.data;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;

@Getter @Setter @Entity @NoArgsConstructor
public class TypedPlace extends AbstractDataTree<PlaceType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 748473615016876239L;

	public TypedPlace(TypedPlace parent, PlaceType type, String code) {
		super(parent, type, code);
	}

	public TypedPlace(TypedPlace parent, String code) {
		super(parent, code);
	}
	
	
	
}
