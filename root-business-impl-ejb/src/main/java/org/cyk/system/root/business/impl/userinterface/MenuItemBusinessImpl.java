package org.cyk.system.root.business.impl.userinterface;

import javax.inject.Inject;

import org.cyk.system.root.business.api.userinterface.MenuItemBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.userinterface.MenuItem;
import org.cyk.system.root.persistence.api.userinterface.MenuItemDao;

public class MenuItemBusinessImpl extends AbstractDataTreeTypeBusinessImpl<MenuItem,MenuItemDao> implements MenuItemBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MenuItemBusinessImpl(MenuItemDao dao) {
        super(dao);
    } 

}
