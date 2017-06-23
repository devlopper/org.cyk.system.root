package org.cyk.system.root.business.impl.userinterface;

import javax.inject.Inject;

import org.cyk.system.root.business.api.userinterface.UserInterfaceMenuNodeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNode;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNodeType;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceMenuNodeDao;

public class UserInterfaceMenuNodeBusinessImpl extends AbstractDataTreeBusinessImpl<UserInterfaceMenuNode,UserInterfaceMenuNodeDao,UserInterfaceMenuNodeType> implements UserInterfaceMenuNodeBusiness {
 
	private static final long serialVersionUID = 8843694832726482311L;

	@Inject
    public UserInterfaceMenuNodeBusinessImpl(UserInterfaceMenuNodeDao dao) {
        super(dao);
    }
 
}
