package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @MappedSuperclass
public abstract class AbstractEnumerationTree<TYPE extends AbstractEnumerationTreeType> extends AbstractEnumerationNode implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	
	@ManyToOne protected TYPE type;
		
	public AbstractEnumerationTree(AbstractEnumerationTree<TYPE> parent,String code) {
		super(parent,code);
		this.node=parent==null?null:new NestedSetNode(parent.getNode().getSet(), parent.getNode());
	}
	
	public AbstractEnumerationTree(AbstractEnumerationTree<TYPE> parent,TYPE type,String code) {
		this(parent,code);
		this.type=type;
	}
	
}
