package org.cyk.system.root.business.impl.helper;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.ClassHelper.Listener.IdentifierType;

public class InstanceHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Listener extends org.cyk.utility.common.helper.InstanceHelper.Listener.Adapter.Default{
    	private static final long serialVersionUID = 1L;
		
    	@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
    	public <T> Collection<T> getHierarchyRoots(Class<T> aClass) {
    		if(ClassHelper.getInstance().isInstanceOf(AbstractDataTreeNode.class, aClass))
				return (Collection<T>) ((AbstractDataTreeNodeBusiness)inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractDataTreeNode>)aClass)).findHierarchies();
    		return super.getHierarchyRoots(aClass);
    	}
    	
    	@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
    	public <T> Collection<T> getHierarchyChildren(Object parent) {
    		if(parent instanceof AbstractDataTreeNode)
				return (Collection<T>) ((AbstractDataTreeNodeBusiness)inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractDataTreeNode>)parent.getClass())).findByParent((AbstractEnumeration) parent);
    		return super.getHierarchyChildren(parent);
    	}
    	
    	@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
    	public Long getHierarchyNumberOfChildren(Object parent) {
    		if(parent instanceof AbstractDataTreeNode)
				return ((AbstractDataTreeNodeBusiness)inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractDataTreeNode>)parent.getClass())).countDirectChildrenByParent((AbstractEnumeration) parent);
    		return super.getHierarchyNumberOfChildren(parent);
    	}
    	
    	@Override
    	public Object getIdentifier(Object instance, ClassHelper.Listener.IdentifierType identifierType) {
    		if(instance instanceof AbstractIdentifiable){
    			if(ClassHelper.Listener.IdentifierType.SYSTEM.equals(identifierType))
    				return ((AbstractIdentifiable)instance).getIdentifier();
    			else if(ClassHelper.Listener.IdentifierType.BUSINESS.equals(identifierType))
    				return ((AbstractIdentifiable)instance).getCode();
    		}else if(instance instanceof GlobalIdentifier)
    			return ((GlobalIdentifier)instance).getIdentifier();
    		return super.getIdentifier(instance, identifierType);
    	}
    	
    	@Override
    	public Object getIdentifier(Object instance) {
    		if(instance instanceof AbstractIdentifiable)
    			return ((AbstractIdentifiable)instance).getCode();
    		else if(instance instanceof GlobalIdentifier)
    			return ((GlobalIdentifier)instance).getIdentifier();
    		return super.getIdentifier(instance);
    	}
		
		@SuppressWarnings("unchecked")
		@Override
		public <T> Collection<T> get(Class<T> aClass) {
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass))
				return (Collection<T>) inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass).findAll();
			if(GlobalIdentifier.class.equals(aClass))
				return (Collection<T>) inject(GlobalIdentifierBusiness.class).findAll();
			return super.get(aClass);
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public <T> Collection<T> get(Class<T> aClass, Object master) {
			if(ClassHelper.getInstance().isInstanceOf(AbstractCollectionItem.class, aClass))
				if(master instanceof AbstractIdentifiable)
					return ((AbstractCollectionItemBusiness)inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass)).findByCollection((AbstractCollection<?>)master);			
			return super.get(aClass, master);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <T> Collection<T> get(Class<T> aClass, DataReadConfiguration dataReadConfiguration) {
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass))
				return (Collection<T>) inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass).findAll(dataReadConfiguration);
			if(GlobalIdentifier.class.equals(aClass))
				return (Collection<T>) inject(GlobalIdentifierBusiness.class).findAll(dataReadConfiguration);
			return super.get(aClass, dataReadConfiguration);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <T> Long count(Class<T> aClass, DataReadConfiguration dataReadConfiguration) {
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass))
				return inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass).countAll();
			if(GlobalIdentifier.class.equals(aClass))
				inject(GlobalIdentifierBusiness.class).countAll();
			return super.count(aClass, dataReadConfiguration);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <T> Collection<T> get(Class<T> aClass,FilterHelper.Filter<T> filter, DataReadConfiguration dataReadConfiguration) {
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass))
				return (Collection<T>) inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass).findByFilter((Filter<AbstractIdentifiable>) filter,dataReadConfiguration);
			if(GlobalIdentifier.class.equals(aClass))
				inject(GlobalIdentifierBusiness.class).findByFilter((Filter<GlobalIdentifier>) filter,dataReadConfiguration);
			return super.get(aClass,filter, dataReadConfiguration);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <T> Long count(Class<T> aClass,FilterHelper.Filter<T> filter, DataReadConfiguration dataReadConfiguration) {
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass))
				return inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass).countByFilter((Filter<AbstractIdentifiable>) filter, dataReadConfiguration);
			if(GlobalIdentifier.class.equals(aClass))
				inject(GlobalIdentifierBusiness.class).countByFilter((Filter<GlobalIdentifier>) filter,dataReadConfiguration);
			return super.count(aClass,filter, dataReadConfiguration);
		}
    
		@SuppressWarnings("unchecked")
		@Override
		public <T> T getByIdentifier(Class<T> aClass, Object identifier,ClassHelper.Listener.IdentifierType identifierType) {
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass))
				if(ClassHelper.Listener.IdentifierType.SYSTEM.equals(identifierType))
					return (T) inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass).find(NumberHelper.getInstance().get(Long.class, identifier));
				else if(ClassHelper.Listener.IdentifierType.BUSINESS.equals(identifierType))
					return (T) inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass).find((String) identifier);
			if(GlobalIdentifier.class.equals(aClass))
				return (T) inject(GlobalIdentifierBusiness.class).find(identifier.toString());
			return super.getByIdentifier(aClass, identifier,identifierType);
		}
		
		@Override
		public Object act(Action action, Object instance) {
			if(instance instanceof AbstractIdentifiable){
				AbstractIdentifiable identifiable = (AbstractIdentifiable) instance;
				if(Constant.Action.CREATE.equals(action))
					return inject(GenericBusiness.class).create(identifiable);
				if(Constant.Action.UPDATE.equals(action))
					return inject(GenericBusiness.class).update(identifiable);
				if(Constant.Action.DELETE.equals(action))
					return inject(GenericBusiness.class).delete(identifiable);
			}else if(instance instanceof GlobalIdentifier){
				GlobalIdentifier globalIdentifier = (GlobalIdentifier) instance;
				if(Constant.Action.CREATE.equals(action))
					return inject(GlobalIdentifierBusiness.class).create(globalIdentifier);
				if(Constant.Action.UPDATE.equals(action))
					return inject(GlobalIdentifierBusiness.class).update(globalIdentifier);
				if(Constant.Action.DELETE.equals(action))
					return inject(GlobalIdentifierBusiness.class).delete(globalIdentifier);
			}
			
			return super.act(action, instance);
		}
	
		@SuppressWarnings("unchecked")
		@Override
		public <T> T computeChanges(T instance) {
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, instance.getClass())){
				inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)instance.getClass()).computeChanges((AbstractIdentifiable) instance);
				return instance;
			}
			return super.computeChanges(instance);
		}
	}
	
	public static class BuilderOneDimensionArray<T extends AbstractIdentifiable> extends org.cyk.utility.common.helper.InstanceHelper.Builder.OneDimensionArray.Adapter.Default<T> implements Serializable{
    	private static final long serialVersionUID = 1L;
    	
    	public BuilderOneDimensionArray(Class<T> outputClass) {
			super(outputClass);
		}
    	
    	public BuilderOneDimensionArray<T> addFieldCode() {
			addParameterArrayElementString(FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE));
			return this;
		}
    	
    	public BuilderOneDimensionArray<T> addFieldCodeName() {
    		addFieldCode();
			addParameterArrayElementString(FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
			return this;
		}
    	
    	@Override
    	public BuilderOneDimensionArray<T> addParameterArrayElementString(String... strings) {
    		return (BuilderOneDimensionArray<T>) super.addParameterArrayElementString(strings);
    	}
    	
    	@Override
    	public BuilderOneDimensionArray<T> addParameterArrayElementString(Integer index, String string) {
    		return (BuilderOneDimensionArray<T>) super.addParameterArrayElementString(index, string);
    	}
    	
    	@Override
    	public BuilderOneDimensionArray<T> addParameterArrayElementStringIndexInstance(Object... arg0) {
    		return (BuilderOneDimensionArray<T>) super.addParameterArrayElementStringIndexInstance(arg0);
    	}
    	
    	/**/
    	
	}
		
	public static class Lookup extends org.cyk.utility.common.helper.InstanceHelper.Lookup.Source.Adapter.Default.ResultMethod {
		
		private static final long serialVersionUID = 1L;

		@Override
		protected java.lang.Object __execute__(java.lang.Object instance) {
			if(instance==null){
				if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, getOutputClass())){
					@SuppressWarnings("unchecked")
					AbstractIdentifiable identifiable =  inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>) ClassHelper.getInstance().getByName(getOutputClass().getName()))
							.read((java.lang.String)getInput());
					return identifiable;
				}else{
					
				}
			}
			return super.__execute__(instance);
		}
	}
	
	public static class Pool extends org.cyk.utility.common.helper.InstanceHelper.Pool.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		@Override
		public <T> Collection<T> load(Class<T> aClass) {
			if(AbstractIdentifiable.class.isAssignableFrom(aClass))
				return (Collection<T>) inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>) ClassHelper.getInstance().getByName(aClass)).readAll();
			return super.load(aClass);
		}
		
		@SuppressWarnings("unchecked")
		public <T extends Object> T get(java.lang.Class<T> aClass, Object identifier) {
			T instance = super.get(aClass, identifier);
			if(instance==null)
				if(AbstractIdentifiable.class.isAssignableFrom(aClass))
					return (T) inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>) ClassHelper.getInstance().getByName(aClass)).read((String)identifier);
			return instance;
		}
	}
	
	public static class Label extends org.cyk.utility.common.helper.InstanceHelper.Stringifier.Label.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected String __execute__() {
			Object object = getInput();
			if(object instanceof AbstractOutputDetails<?>)
				if(((AbstractOutputDetails<?>)object).getMaster()!=null)
					object = ((AbstractOutputDetails<?>)object).getMaster();
			if( object instanceof AbstractEnumeration )
				return StringHelper.getInstance().isBlank(((AbstractEnumeration)object).getName()) ? ((AbstractEnumeration)object).getCode() 
						: ((AbstractEnumeration)object).getName();
				if( object instanceof AbstractIdentifiable ){
					if( StringHelper.getInstance().isNotBlank(((AbstractIdentifiable)object).getName()) )
						return ((AbstractIdentifiable)object).getName(); 
					if( StringHelper.getInstance().isNotBlank(((AbstractIdentifiable)object).getCode()) )
						return ((AbstractIdentifiable)object).getCode(); 
				}
			if( object instanceof AbstractModelElement )
				return ((AbstractModelElement)object).getUiString();
			return super.__execute__();
		}
		
	}
	
	public static class Mapping extends org.cyk.utility.common.helper.InstanceHelper.Mapping.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected Object __execute__() {
			if( getInput() instanceof AbstractIdentifiable )
				return ((AbstractIdentifiable)getInput()).getIdentifier();
			return super.__execute__();
		}
		
	}
}
