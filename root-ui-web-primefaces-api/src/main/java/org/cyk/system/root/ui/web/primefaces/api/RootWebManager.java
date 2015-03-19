package org.cyk.system.root.ui.web.primefaces.api;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.web.api.AbstractWebManager;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class RootWebManager extends AbstractWebManager implements Serializable {

	private static final long serialVersionUID = 5478269349061504239L;

	private static RootWebManager INSTANCE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	@Override
	public SystemMenu systemMenu(AbstractUserSession userSession) {
		SystemMenu systemMenu = new SystemMenu();
		UICommandable group = uiProvider.createCommandable("contacts", null);
		group.addChild(menuManager.crudMany(Locality.class, null));
		systemMenu.getReferenceEntities().add(group);
		
		return systemMenu;
	}
	
	public static RootWebManager getInstance() {
		return INSTANCE;
	}

}
