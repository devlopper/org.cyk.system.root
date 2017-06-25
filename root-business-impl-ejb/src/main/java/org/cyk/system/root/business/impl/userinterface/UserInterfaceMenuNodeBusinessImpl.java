package org.cyk.system.root.business.impl.userinterface;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.userinterface.UserInterfaceMenuNodeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNode;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNodeType;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceMenuNodeDao;

public class UserInterfaceMenuNodeBusinessImpl extends AbstractDataTreeBusinessImpl<UserInterfaceMenuNode,UserInterfaceMenuNodeDao,UserInterfaceMenuNodeType> implements UserInterfaceMenuNodeBusiness {
 
	private static final long serialVersionUID = 8843694832726482311L;

	@Inject
    public UserInterfaceMenuNodeBusinessImpl(UserInterfaceMenuNodeDao dao) {
        super(dao);
    }
	
	@Override
	protected Object[] getPropertyValueTokens(UserInterfaceMenuNode userInterfaceMenuNode, String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME}, name)){
			return new Object[]{userInterfaceMenuNode.getCommand()};
		}
		return super.getPropertyValueTokens(userInterfaceMenuNode, name);
	}
 
}
