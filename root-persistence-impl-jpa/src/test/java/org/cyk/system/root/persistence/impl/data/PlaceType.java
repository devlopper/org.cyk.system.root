 package org.cyk.system.root.persistence.impl.data;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.DataTreeType;

@Getter @Setter @Entity @NoArgsConstructor
public class PlaceType extends DataTreeType implements Serializable {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlaceType(DataTreeType parent, String code) {
		super(parent, code);
	}
	
}
