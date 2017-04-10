package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public abstract class AbstractDataTreeDetails<NODE extends AbstractDataTree<TYPE>,TYPE extends AbstractDataTreeType> extends AbstractDataTreeNodeDetails<NODE> implements Serializable {

	private static final long serialVersionUID = 7515356383413863619L;

	@Input @InputText protected String type;
	
	public AbstractDataTreeDetails(NODE node) {
		super(node);
		setMaster(node);
		
	}
	
	@Override
	public void setMaster(NODE master) {
		super.setMaster(master);
		if(master!=null){
			type = formatUsingBusiness(master.getType());
		}
	}
	
	/**/
	
	public static final String FIELD_TYPE = "type";
	
	/**/
	
	
}
