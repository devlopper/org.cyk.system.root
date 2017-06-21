package org.cyk.system.root.business.impl.userinterface;

import javax.inject.Inject;

import org.cyk.system.root.business.api.userinterface.MenuItemBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.model.userinterface.MenuItem;
import org.cyk.system.root.model.userinterface.MenuItemType;
import org.cyk.system.root.persistence.api.userinterface.MenuItemDao;

public class MenuItemBusinessImpl extends AbstractDataTreeBusinessImpl<MenuItem,MenuItemDao,MenuItemType> implements MenuItemBusiness {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 8843694832726482311L;

	@Inject
    public MenuItemBusinessImpl(MenuItemDao dao) {
        super(dao);
    }
 
}
