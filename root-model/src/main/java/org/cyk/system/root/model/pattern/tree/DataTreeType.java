package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@MappedSuperclass
@Entity
@Getter @Setter @NoArgsConstructor
public /*abstract*/ class DataTreeType extends AbstractDataTreeNode implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	
	@Transient private Collection<DataTreeType> children;
	
	public DataTreeType(DataTreeType parent,String code,String label) {
		super(parent,code,label);
	}
	
	public DataTreeType(DataTreeType parent,String code) {
		this(parent,code,code);
	}
	
}
