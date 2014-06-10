package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @MappedSuperclass
public abstract class AbstractDataTree<TYPE extends DataTreeType> extends AbstractDataTreeNode implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	
	@ManyToOne @NotNull protected TYPE type;
	
	//@Transient protected Class<TYPE> __typeClass__;
	
	{
	    //__typeClass__ = (Class<TYPE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public AbstractDataTree(AbstractDataTree<TYPE> parent,String code) {
		super(parent,code);
		this.node=parent==null?null:new NestedSetNode(parent.getNode().getSet(), parent.getNode());
	}
	
	public AbstractDataTree(AbstractDataTree<TYPE> parent,TYPE type,String code) {
		this(parent,code);
		this.type=type;
	}
	
}
