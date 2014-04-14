package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.validation.Client;

@Getter @Setter @NoArgsConstructor @MappedSuperclass
public abstract class AbstractEnumerationNode extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	
	@ManyToOne
	@NotNull(groups=Client.class)
	protected NestedSetNode node;
		
	public AbstractEnumerationNode(AbstractEnumerationNode parent,String code,String label) {
		super(code,label,null,null);
		this.node=parent==null?null:new NestedSetNode(parent.getNode().getSet(), parent.getNode());
	}
	
	public AbstractEnumerationNode(AbstractEnumerationNode parent,String code) {
		this(parent,code,code);
	}
	
}
