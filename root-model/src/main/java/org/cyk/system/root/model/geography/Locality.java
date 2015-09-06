package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.utility.common.annotation.user.interfaces.FieldOverride;

@Getter @Setter @NoArgsConstructor @Entity @FieldOverride(name="type",type=LocalityType.class)
public class Locality extends AbstractDataTree<LocalityType> implements Serializable  {

	private static final long serialVersionUID = -6128937819261060725L;

	public Locality(AbstractDataTree<LocalityType> parent, LocalityType type, String code,String name) {
		super(parent, type, code);
		this.name = name;
	}
	
	public Locality(AbstractDataTree<LocalityType> parent, LocalityType type, String code) {
		super(parent,type,code);
	}
}
