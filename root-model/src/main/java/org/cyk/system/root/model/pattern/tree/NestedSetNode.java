package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.cyk.utility.common.validation.System;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
@Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class NestedSetNode extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 9135086950442356103L;

	public static final Integer FIRST_LEFT_INDEX = 0;
	public static final Integer FIRST_RIGHT_INDEX = 1;
	
	@Accessors(chain=true)
	@ManyToOne @JoinColumn(name=COLUMN_SET) @NotNull(groups=System.class)
	private NestedSet set;
	
	@Accessors(chain=true)
	@JoinColumn(name=COLUMN_PARENT) @ManyToOne
	private NestedSetNode parent;
	
	@Column(nullable=false) @NotNull(groups=System.class)
	private Integer leftIndex;
	
	@Column(nullable=false) @NotNull(groups=System.class)
	private Integer rightIndex;
	
	@Column(name=COLUMN_NUMBER_OF_CHILDREN)
	private Integer numberOfChildren;
	
	@Column(name=COLUMN_NUMBER_OF_DIRECT_CHILDREN)
	private Integer numberOfDirectChildren;
	
	private String detachedIdentifier;
	
	//@Transient private Collection<NestedSetNode> children;
	
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
	
	@Override
	public NestedSetNode setCode(String code) {
		return (NestedSetNode) super.setCode(code);
	}
	
	/*
	@Override
	public String toString() {
		return getLogMessage();// super.toString()+String.format(TO_STRING_FORMAT, identifier,leftIndex,rightIndex,parent==null?Constant.EMPTY_STRING:parent.getIdentifier(),detachedIdentifier,set.getIdentifier());
	}*/
	
	/*@Override
	public String getLogMessage() {
		return String.format(LOG_MESSAGE_FORMAT, globalIdentifier==null ? Constant.EMPTY_STRING:globalIdentifier.getName(),identifier,leftIndex,rightIndex,parent==null?Constant.EMPTY_STRING:parent.getIdentifier(),detachedIdentifier,set.getIdentifier());
	}*/
	
	/**/
	
	public static final String FIELD_SET = "set";
	public static final String FIELD_PARENT = "parent";
	public static final String FIELD_LEFT_INDEX = "leftIndex";
	public static final String FIELD_RIGHT_INDEX = "rightIndex";
	public static final String FIELD_DETACHED_IDENTIFIER = "detachedIdentifier";
	
	public static final String COLUMN_SET = "set_";
	public static final String COLUMN_PARENT = "parent_";
	
	public static final String FIELD_NUMBER_OF_CHILDREN = "numberOfChildren";
	public static final String FIELD_NUMBER_OF_DIRECT_CHILDREN = "numberOfDirectChildren";
	
	public static final String COLUMN_NUMBER_OF_CHILDREN = "numberOfChildren";
	public static final String COLUMN_NUMBER_OF_DIRECT_CHILDREN = "numberOfDirectChildren";
}
