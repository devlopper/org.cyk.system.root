package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.utility.common.cdi.AbstractStartupBean;

@Stateless @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BusinessManagerImpl extends AbstractStartupBean implements
		BusinessManager, Serializable {

	private static final long serialVersionUID = 6180427700636598680L;

	//private static Collection<BusinessEntityInfos> entitiesInfos;
	//private static DataSource SHIRO_DATASOURCE;

	//@Inject private PersistenceManager persistenceManager;
	//@Inject private LanguageBusiness languageBusiness;

	private static Collection<BusinessLayer> BUSINESS_LAYERS;

	
	@Override
	protected void initialisation() {
		super.initialisation();
		BUSINESS_LAYERS = new ArrayList<>();
		for (Object object : startupBeanExtension.getReferences())
			if (BusinessLayer.class.isAssignableFrom(object.getClass()))
				BUSINESS_LAYERS.add((BusinessLayer) object);
	}

	@Override
	public Collection<BusinessLayer> findBusinessLayers() {
		/*
		for (Object object : startupBeanExtension.getReferences())
			if (BusinessLayer.class.isAssignableFrom(object.getClass()))
				businessLayers.add((BusinessLayer) object);
		*/
		return BUSINESS_LAYERS;
	}

}
