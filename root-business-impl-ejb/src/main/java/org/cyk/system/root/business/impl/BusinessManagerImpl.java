package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.utility.common.annotation.Deployment;
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
		List<BusinessLayer> list = new ArrayList<>();
		for (Object object : startupBeanExtension.getReferences())
			if (BusinessLayer.class.isAssignableFrom(object.getClass()))
				list.add((BusinessLayer) object);
		
		Collections.sort(list, new Comparator<BusinessLayer>() {
			@Override
			public int compare(BusinessLayer o1, BusinessLayer o2) {
				Deployment d1 = o1.getClass().getAnnotation(Deployment.class);
				Deployment d2 = o2.getClass().getAnnotation(Deployment.class);
				return d1.order()-d2.order();
			}
		});
		
		BUSINESS_LAYERS = list;
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
