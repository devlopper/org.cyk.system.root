package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.EnumerationTree;

@Getter @Setter @NoArgsConstructor @Entity
public class LocalityType extends EnumerationTree implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	/*
	public LocalityType(NestedSetNode node,String code, String libelle) {
		super(node,code, libelle, null, null);
	}*/
	
}
