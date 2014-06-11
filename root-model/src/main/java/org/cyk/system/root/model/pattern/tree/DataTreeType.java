package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class DataTreeType extends AbstractDataTreeNode implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	
	public DataTreeType(DataTreeType parent,String code,String label) {
		super(parent,code,label);
	}
	
	public DataTreeType(DataTreeType parent,String code) {
		this(parent,code,code);
	}
	
}
