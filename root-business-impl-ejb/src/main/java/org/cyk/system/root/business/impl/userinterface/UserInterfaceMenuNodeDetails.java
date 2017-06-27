package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.geography.AbstractDataTreeDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNode;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNodeType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserInterfaceMenuNodeDetails extends AbstractDataTreeDetails<UserInterfaceMenuNode,UserInterfaceMenuNodeType> implements Serializable {

	private static final long serialVersionUID = -4747519269632371426L;

	private FieldValue command;
	
	public UserInterfaceMenuNodeDetails(UserInterfaceMenuNode dataTree) {
		super(dataTree);
	}
	
	@Override
	public void setMaster(UserInterfaceMenuNode master) {
		super.setMaster(master);
		if(master!=null) {
			command = createFieldValue(master.getCommand());
		}
	}
	
	public static final String FIELD_COMMAND = "command";
	
}
