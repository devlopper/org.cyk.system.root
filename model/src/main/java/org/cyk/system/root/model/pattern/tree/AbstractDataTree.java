package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @MappedSuperclass
public abstract class AbstractDataTree<TYPE extends AbstractDataTreeType> extends AbstractDataTreeNode implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	
	@ManyToOne protected TYPE type;
		
	public AbstractDataTree(AbstractDataTree<TYPE> parent,String code) {
		super(parent,code);
		this.node=parent==null?null:new NestedSetNode(parent.getNode().getSet(), parent.getNode());
	}
	
	public AbstractDataTree(AbstractDataTree<TYPE> parent,TYPE type,String code) {
		this(parent,code);
		this.type=type;
	}
	
}
