package org.cyk.system.root.persistence.impl.data;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;

@Getter @Setter @Entity @NoArgsConstructor
public class PlaceType extends AbstractDataTreeType implements Serializable {
	
	public PlaceType(AbstractDataTreeType parent, String code) {
		super(parent, code);
	}
	
}
