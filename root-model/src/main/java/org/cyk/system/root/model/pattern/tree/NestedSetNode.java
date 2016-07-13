package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.validation.System;

@Getter @Setter
@Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public class NestedSetNode extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 9135086950442356103L;

	public static final Integer FIRST_LEFT_INDEX = 0;
	public static final Integer FIRST_RIGHT_INDEX = 1;
	
	@ManyToOne @JoinColumn(name="theset") @NotNull(groups=System.class)
	private NestedSet set;
	
	@JoinColumn(name="parent") @ManyToOne
	private NestedSetNode parent;
	
	@Column(nullable=false) @NotNull(groups=System.class)
	private Integer leftIndex;
	
	@Column(nullable=false) @NotNull(groups=System.class)
	private Integer rightIndex;
	
	private String detachedIdentifier;
	
	@Transient private Collection<NestedSetNode> children;
	
	public NestedSetNode() {}
	
	public NestedSetNode(NestedSet set,NestedSetNode parent, int leftIndex, int rightIndex) {
		super();
		this.set = set;
		this.parent = parent;
		this.leftIndex = leftIndex;
		this.rightIndex = rightIndex;
	}

	public NestedSetNode(NestedSet set,NestedSetNode parent) {
		this(set,parent,FIRST_LEFT_INDEX,FIRST_RIGHT_INDEX) ;
	}
	
	public Boolean isLeaf(){
		return leftIndex+1==rightIndex;
	}
	
	public Collection<NestedSetNode> getChildren(){
		if(this.children==null)
			this.children = new ArrayList<>();
		return this.children;
	}
	
	@Override
	public String toString() {
		return String.format(TO_STRING_FORMAT, identifier,leftIndex,rightIndex,parent==null?Constant.EMPTY_STRING:parent.getIdentifier(),detachedIdentifier,set.getIdentifier());
	}
	
	@Override
	public String getLogMessage() {
		return String.format(LOG_MESSAGE_FORMAT, identifier,leftIndex,rightIndex,parent==null?Constant.EMPTY_STRING:parent.getIdentifier(),detachedIdentifier,set.getIdentifier());
	}
	
	/**/
	
	public static final String FIELD_SET = "set";
	public static final String FIELD_PARENT = "parent";
	public static final String FIELD_LEFT_INDEX = "leftIndex";
	public static final String FIELD_RIGHT_INDEX = "rightIndex";
	public static final String FIELD_DETACHED_IDENTIFIER = "detachedIdentifier";
	
	private static final String LOG_MESSAGE_FORMAT = NestedSetNode.class.getSimpleName()+"(I=%s,L=%s,R=%s,P=%s,DI=%s,S=%s)";
	private static final String TO_STRING_FORMAT = "(I=%s,L=%s,R=%s,P=%s,DI=%s,S=%s)";
}
