package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeNodeBusinessImpl;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.utility.common.ClassLocator;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.helper.ClassHelper;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class DetailsClassLocator extends ClassLocator implements Serializable {

	private static final long serialVersionUID = -3187769614985951029L;

	private static DetailsClassLocator INSTANCE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		setClassType("Details");
		Listener listener = new Listener.Adapter(){
			private static final long serialVersionUID = -979036256355287919L;

			@Override
			public Boolean isLocatable(Class<?> basedClass) {
				return AbstractModelElement.class.isAssignableFrom(basedClass);
			}
		};
		
		listener.setGetNameMethod(new Listener.AbstractGetOrgCykSystem() {
			private static final long serialVersionUID = -7213562588417233353L;
			@Override
			protected String getBaseClassPackageName() {
				return "model";
			}
			
			@Override
			protected String[] getModulePrefixes() {
				return new String[]{"business.impl"};
			}
			
			@Override
			protected String[] getModuleSuffixes() {
				return new String[]{"Details","BusinessImpl$Details"};
			}
			
		});
		getClassLocatorListeners().add(listener);
	}
		
	@Override
	protected Class<?> getDefault(Class<?> aClass) {
		if(ClassHelper.getInstance().isInstanceOf(AbstractDataTree.class, aClass))
			return AbstractDataTreeBusinessImpl.Details.class;
		if(ClassHelper.getInstance().isInstanceOf(AbstractDataTreeType.class, aClass))
			return AbstractDataTreeTypeBusinessImpl.Details.class;
		if(ClassHelper.getInstance().isInstanceOf(AbstractDataTreeNode.class, aClass))
			return AbstractDataTreeNodeBusinessImpl.Details.class;
		if(ClassHelper.getInstance().isInstanceOf(AbstractEnumeration.class, aClass))
			return AbstractEnumerationBusinessImpl.Details.class;
		if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass))
			return AbstractTypedBusinessService.Details.class;
		return super.getDefault(aClass);
	}
	
	public static DetailsClassLocator getInstance() {
		return INSTANCE;
	}
}
