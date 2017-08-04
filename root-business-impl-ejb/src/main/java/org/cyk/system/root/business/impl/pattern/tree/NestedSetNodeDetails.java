package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class NestedSetNodeDetails extends AbstractOutputDetails<NestedSetNode> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String set,parent,leftIndex,rightIndex;
	
	public NestedSetNodeDetails(NestedSetNode nestedSetNode) {
		super(nestedSetNode);
		set = nestedSetNode.getSet().getIdentifier().toString();
		parent = nestedSetNode.getParent() == null ? Constant.EMPTY_STRING : nestedSetNode.getParent().getIdentifier().toString();
		leftIndex = formatNumber(nestedSetNode.getLeftIndex());
		rightIndex = formatNumber(nestedSetNode.getRightIndex());
	}
	
	public static final String FIELD_SET = "set";
	public static final String FIELD_PARENT = "parent";
	public static final String FIELD_LEFT_INDEX = "leftIndex";
	public static final String FIELD_RIGHT_INDEX = "rightIndex";
}