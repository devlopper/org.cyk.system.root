package org.cyk.system.root.business.impl.userinterface;

import javax.inject.Inject;

import org.cyk.system.root.business.api.userinterface.MenuItemTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.userinterface.MenuItemType;
import org.cyk.system.root.persistence.api.userinterface.MenuItemTypeDao;

public class MenuItemTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<MenuItemType,MenuItemTypeDao> implements MenuItemTypeBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MenuItemTypeBusinessImpl(MenuItemTypeDao dao) {
        super(dao);
    } 

}
