package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.validation.System;

@Getter @Setter
@Entity @ModelBean(crudStrategy=CrudStrategy.INTERNAL)
public class NestedSetNode extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 9135086950442356103L;

	public static final Integer FIRST_LEFT_INDEX = 0;
	public static final Integer FIRST_RIGHT_INDEX = 1;
	
	@ManyToOne
	@JoinColumn(name="theset")
	@NotNull(groups=System.class)
	private NestedSet set;
	
	@JoinColumn(name="parent")
	@ManyToOne
	private NestedSetNode parent;
	
	@Column(nullable=false)
	@NotNull(groups=System.class)
	private Integer leftIndex;
	
	@Column(nullable=false)
	@NotNull(groups=System.class)
	private Integer rightIndex;
	/*
	@JoinColumn(name="data")
	@ManyToOne
	private AbstractDataTreeNode data;
	*/
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
	
	public boolean isLeaf(){
		return leftIndex+1==rightIndex;
	}
	
	public void updateBoundariesGreaterThanOrEqualTo(boolean increase,int index){
		int sign = increase?+1:-1;
		if(leftIndex>=index)
			leftIndex=leftIndex+sign*2;
		if(rightIndex>=index)
			rightIndex=rightIndex+sign*2;
	}
	
	public void updateBoundaries(int step,Boolean left){
		if(left==null || left)
			leftIndex = leftIndex + step;
		if(left==null || !left)
			rightIndex = rightIndex + step;
	}
	
	@Override
	public String toString() {
		return "("+leftIndex+","+rightIndex+")";
	}
	/*
	public static NestedSetNode createChildOf(NestedSetNode root){
		NestedSetNode child = new NestedSetNode(root.getSet(), root, root.getRightIndex(), root.getRightIndex()+1);
		root.setRightIndex(root.getRightIndex()+2);
		return child;
	}*/
	
}
