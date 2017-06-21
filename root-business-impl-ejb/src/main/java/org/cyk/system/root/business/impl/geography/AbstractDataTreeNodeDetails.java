package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public abstract class AbstractDataTreeNodeDetails<NODE extends AbstractDataTreeNode> extends AbstractEnumerationDetails<NODE> implements Serializable {

	private static final long serialVersionUID = 7515356383413863619L;

	@Input @InputText protected String parent;
	
	public AbstractDataTreeNodeDetails(NODE node) {
		super(node);
	}
	
	@Override
	public void setMaster(NODE master) {
		super.setMaster(master);
		
		if(master!=null){
			@SuppressWarnings("unchecked")
			NODE parent = (NODE) inject(BusinessInterfaceLocator.class).injectTyped(clazz).findParent(master);
			this.parent = parent == null ? null : formatUsingBusiness(parent);
		}
	}
	
	/**/
	
	public static final String FIELD_PARENT = "parent";
	
	/**/
	
	
}
