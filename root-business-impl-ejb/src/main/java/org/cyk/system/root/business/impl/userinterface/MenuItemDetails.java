package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.geography.AbstractDataTreeDetails;
import org.cyk.system.root.model.userinterface.MenuItem;
import org.cyk.system.root.model.userinterface.MenuItemType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuItemDetails extends AbstractDataTreeDetails<MenuItem,MenuItemType> implements Serializable {

	private static final long serialVersionUID = -4747519269632371426L;

	private FieldValue uniformResourceLocator;
	
	public MenuItemDetails(MenuItem dataTree) {
		super(dataTree);
	}
	
	@Override
	public void setMaster(MenuItem master) {
		super.setMaster(master);
		if(master!=null) {
			uniformResourceLocator = new FieldValue(master.getUniformResourceLocator());
		}
	}
	
	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
	
}
