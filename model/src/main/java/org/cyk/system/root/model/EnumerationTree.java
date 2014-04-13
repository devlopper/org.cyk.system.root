package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.utility.common.validation.Client;

@Getter @Setter @NoArgsConstructor @Entity
public class EnumerationTree extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	
	@ManyToOne
	//@JoinColumn(nullable=false)
	@NotNull(groups=Client.class)
	protected NestedSetNode node;

	@ManyToOne
	protected EnumerationTree type;
		
	public EnumerationTree(EnumerationTree parent,String code) {
		super(code,code,null,null);
		this.node=parent==null?null:new NestedSetNode(parent.getNode().getSet(), parent.getNode());
	}
	
	public EnumerationTree(EnumerationTree parent,EnumerationTree type,String code) {
		this(parent,code);
		this.type=type;
	}
	
}
