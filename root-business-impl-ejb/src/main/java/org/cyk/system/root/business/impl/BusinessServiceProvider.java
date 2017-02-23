package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.computation.DataReadConfiguration;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER) @Deprecated
public class BusinessServiceProvider extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6526060809947143790L;

	public static enum Service{FIND,COUNT,FIND_ALL,COUNT_ALL};
	
	private static BusinessServiceProvider INSTANCE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> TypedBusiness<T> getBusiness(Class<T> identifiableClass){
		return (TypedBusiness<T>) BusinessInterfaceLocator.getInstance().injectLocated(identifiableClass);
	}
	
	public <T extends AbstractIdentifiable> Collection<T> find(Class<T> identifiableClass,final DataReadConfiguration configuration){
		Collection<T> collection = ListenerUtils.getInstance().getCollection(getListeners(identifiableClass), new ListenerUtils.CollectionMethod<Identifiable<T>, T>(){
			@Override
			public Collection<T> execute(Identifiable<T> listener) {
				return listener.find(configuration);
			}
		});
		
		if(collection==null)
			collection = findAll(identifiableClass, configuration);
		
		return collection;
	}
	
	public <T extends AbstractIdentifiable> Long count(Class<T> identifiableClass, final DataReadConfiguration configuration){
		Long value = ListenerUtils.getInstance().getValue(Long.class, getListeners(identifiableClass), new ListenerUtils.LongMethod<Identifiable<T>>(){
			@Override
			public Long execute(Identifiable<T> listener) {
				return listener.count(configuration);
			}
		});
		
		if(value==null)
			value = countAll(identifiableClass, configuration);
		
		return value;
	}
	
	@SuppressWarnings({ })
	public <T extends AbstractIdentifiable> Collection<T> findAll(Class<T> identifiableClass,final DataReadConfiguration configuration){
		Collection<T> collection = ListenerUtils.getInstance().getCollection(getListeners(identifiableClass), new ListenerUtils.CollectionMethod<Identifiable<T>, T>(){
			@Override
			public Collection<T> execute(Identifiable<T> listener) {
				return listener.findAll(configuration);
			}
		});
		
		if(collection==null){
			TypedBusiness<T> business = (TypedBusiness<T>) BusinessInterfaceLocator.getInstance().injectTyped(identifiableClass);
			if(business==null)
				;
			else
				collection = business.findAll() ;
		}
		
		return collection;
	}
	
	public <T extends AbstractIdentifiable> Long countAll(Class<T> identifiableClass,final DataReadConfiguration configuration){
		Long value = ListenerUtils.getInstance().getLong(getListeners(identifiableClass), new ListenerUtils.LongMethod<Identifiable<T>>(){
			@Override
			public Long execute(Identifiable<T> listener) {
				return listener.countAll(configuration);
			}
		});
		
		if(value==null){
			TypedBusiness<T> business = (TypedBusiness<T>) BusinessInterfaceLocator.getInstance().injectTyped(identifiableClass);
			if(business==null)
				;
			else
				value = business.countAll() ;
		}
		
		return value;
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	private <T extends AbstractIdentifiable> Collection<Identifiable<T>> getListeners(Class<T> aClass){
		Collection<Identifiable<T>> collection = new ArrayList<>();
		for(Identifiable<? extends AbstractIdentifiable> listener : Identifiable.COLLECTION)
			if(aClass.equals(listener.getClazz()))
				collection.add((Identifiable<T>) listener);
		return collection;
	}
	
	public static BusinessServiceProvider getInstance() {
		return INSTANCE;
	}
	
	/**/
	
	public interface Identifiable<T extends AbstractIdentifiable> {

		Collection<Identifiable<? extends AbstractIdentifiable>> COLLECTION = new ArrayList<>();
		
		Class<T> getClazz();
		
		TypedBusiness<T> getBusiness();
		
		Collection<T> find(DataReadConfiguration configuration);		
		Long count(DataReadConfiguration configuration);
		
		Collection<T> findAll(DataReadConfiguration configuration);		
		Long countAll(DataReadConfiguration configuration);
		
		/**/
		
		@Getter
		public static class Adapter<T extends AbstractIdentifiable> extends BeanAdapter implements Identifiable<T>,Serializable {
			private static final long serialVersionUID = -6980159194301961222L;

			protected Class<T> clazz;
			
			public Adapter(Class<T> clazz) {
				this.clazz = clazz;
			}
			
			@Override
			public TypedBusiness<T> getBusiness() {
				return null;
			}
			
			@Override
			public Collection<T> find(DataReadConfiguration configuration) {
				return null;
			}
			
			@Override
			public Long count(DataReadConfiguration configuration) {
				return null;
			}
			
			@Override
			public Collection<T> findAll(DataReadConfiguration configuration) {
				return null;
			}
			
			@Override
			public Long countAll(DataReadConfiguration configuration) {
				return null;
			}

			/**/
			
			public static class Default<T extends AbstractIdentifiable> extends Adapter<T> implements Serializable {
				private static final long serialVersionUID = -7111137502842639297L;
				
				public Default(Class<T> clazz) {
					super(clazz);
				}
				
				public TypedBusiness<T> getBusiness(){
					return (TypedBusiness<T>) BusinessInterfaceLocator.getInstance().injectTyped(clazz);
				}
			}

		}

	}
}
