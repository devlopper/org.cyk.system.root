package org.cyk.system.root.ui.web.primefaces.api;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.web.primefaces.AbstractPrimefacesManager;
import org.cyk.ui.web.primefaces.UserSession;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER,order=RootWebManager.DEPLOYMENT_ORDER)
public class RootWebManager extends AbstractPrimefacesManager implements Serializable {

	public static final int DEPLOYMENT_ORDER = RootBusinessLayer.DEPLOYMENT_ORDER+1;
	private static final long serialVersionUID = 5478269349061504239L;

	private static RootWebManager INSTANCE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		identifier = "root";
	}
	
	@Override
	public SystemMenu systemMenu(UserSession userSession) {
		SystemMenu systemMenu = new SystemMenu();
		/*
		UICommandable group = Builder.instanciateOne().setLabelFromId("contacts").create();
		group.addChild(Builder.createList(Locality.class, null));
		systemMenu.getReferenceEntities().add(group);
		
		group = Builder.instanciateOne().setLabelFromId("event").create();
		group.addChild(Builder.createList(EventType.class, null));
		systemMenu.getReferenceEntities().add(group);
		*/
		return systemMenu;
	}
	
	public static RootWebManager getInstance() {
		return INSTANCE;
	}

}
