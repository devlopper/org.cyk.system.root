package org.cyk.system;

import java.io.Serializable;

import javax.inject.Singleton;

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
public class FilterClassLocator extends ClassLocator implements Serializable {
	private static final long serialVersionUID = -1L;

	private static FilterClassLocator INSTANCE;
	
	public FilterClassLocator() {
		setClassType("Filter");
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
			protected String getModulePrefix() {
				return "model";
			}
			
			@Override
			protected String[] getModuleSuffixes() {
				return new String[]{"Filter","$Filter"};
			}
			
		});
		getClassLocatorListeners().add(listener);
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
		
	@Override
	protected Class<?> getDefault(Class<?> aClass) {
		if(ClassHelper.getInstance().isInstanceOf(AbstractDataTree.class, aClass))
			return AbstractDataTree.Filter.class;
		if(ClassHelper.getInstance().isInstanceOf(AbstractDataTreeType.class, aClass))
			return AbstractDataTreeType.Filter.class;
		if(ClassHelper.getInstance().isInstanceOf(AbstractDataTreeNode.class, aClass))
			return AbstractDataTreeNode.Filter.class;
		if(ClassHelper.getInstance().isInstanceOf(AbstractEnumeration.class, aClass))
			return AbstractEnumeration.Filter.class;
		if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass))
			return AbstractIdentifiable.Filter.class;
		return super.getDefault(aClass);
	}
	
	public static FilterClassLocator getInstance() {
		if(INSTANCE == null)
			INSTANCE = new FilterClassLocator();
		return INSTANCE;
	}
}
