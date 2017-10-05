package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractEnumeration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @MappedSuperclass
public abstract class AbstractDataTreeNode extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	
	@ManyToOne @JoinColumn(name=COLUMN_NODE) @NotNull protected NestedSetNode node;
	
	@Transient protected Boolean automaticallyMoveToNewParent;
	@Transient protected AbstractDataTreeNode newParent;
	
	public AbstractDataTreeNode(AbstractDataTreeNode parent,String code,String label) {
		super(code,label,null,null);
		setParentNode(parent);
	}
	
	public AbstractDataTreeNode(AbstractDataTreeNode parent,String code) {
		this(parent,code,code);
	}
	
	public AbstractDataTreeNode setParentNode(AbstractDataTreeNode parent){
		this.node=parent==null?null:new NestedSetNode(parent.getNode().getSet(), parent.getNode());
		setParent(parent);
		return this;
	}
	
	public static final String FIELD_NODE = "node";
	
	public static final String COLUMN_NODE = "node";
	
}
