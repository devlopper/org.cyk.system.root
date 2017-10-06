package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
@Deprecated
public abstract class AbstractDataTreeTypeDetails<TYPE extends AbstractDataTreeType> extends AbstractDataTreeNodeDetails<TYPE> implements Serializable {

	private static final long serialVersionUID = 7515356383413863619L;

	public AbstractDataTreeTypeDetails(TYPE type) {
		super(type);
	}
	
	@Override
	public void setMaster(TYPE master) {
		super.setMaster(master);
		if(master!=null){
			
		}
	}
	
	/**/
	
	/**/
	
	
}
