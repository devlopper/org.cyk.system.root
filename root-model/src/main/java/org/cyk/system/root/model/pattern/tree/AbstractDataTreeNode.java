package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.validation.Client;

@Getter @Setter @NoArgsConstructor @MappedSuperclass
public abstract class AbstractDataTreeNode extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	
	@ManyToOne
	@NotNull(groups=Client.class)
	protected NestedSetNode node;
	
	@Transient private Collection<AbstractDataTreeNode> children;
	
	public AbstractDataTreeNode(AbstractDataTreeNode parent,String code,String label) {
		super(code,label,null,null);
		this.node=parent==null?null:new NestedSetNode(parent.getNode().getSet(), parent.getNode());
	}
	
	public AbstractDataTreeNode(AbstractDataTreeNode parent,String code) {
		this(parent,code,code);
	}
	
}
