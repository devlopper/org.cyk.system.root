package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public abstract class AbstractDataTreeNodeDetails<NODE extends AbstractDataTreeNode> extends AbstractEnumerationDetails<NODE> implements Serializable {

	private static final long serialVersionUID = 7515356383413863619L;

	@Input @InputText protected String parent;
	
	public AbstractDataTreeNodeDetails(NODE node) {
		super(node);
		setMaster(node);
		
	}
	
	@Override
	public void setMaster(NODE master) {
		// TODO Auto-generated method stub
		super.setMaster(master);
		if(master!=null){
			parent = master.getNode().getParent() == null ? null : formatUsingBusiness(master.getNode().getParent());
		}
	}
	
	/**/
	
	public static final String FIELD_PARENT = "parent";
	
	/**/
	
	
}
