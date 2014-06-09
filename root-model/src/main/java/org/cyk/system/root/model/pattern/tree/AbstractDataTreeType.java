package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @MappedSuperclass
public abstract class AbstractDataTreeType extends AbstractDataTreeNode implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	
	@Transient private Collection<AbstractDataTreeType> children;
	
	public AbstractDataTreeType(AbstractDataTreeType parent,String code,String label) {
		super(parent,code,label);
	}
	
	public AbstractDataTreeType(AbstractDataTreeType parent,String code) {
		this(parent,code,code);
	}
	
}
