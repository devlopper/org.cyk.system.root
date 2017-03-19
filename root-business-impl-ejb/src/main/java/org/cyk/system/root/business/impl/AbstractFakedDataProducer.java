package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

//TODO Faked to be removed from class name
public abstract class AbstractFakedDataProducer extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6589529849414444956L;

	@Inject protected RootDataProducerHelper rootDataProducerHelper;
	@Inject protected RootBusinessLayer rootBusinessLayer;
	@Inject protected GenericBusiness genericBusiness;
	protected Listener listener;
	
	@Accessors(chain=true) @Getter @Setter protected Boolean structurationEnabled = Boolean.TRUE;
	@Accessors(chain=true) @Getter @Setter protected Boolean synchronizationEnabled = Boolean.FALSE;
	@Accessors(chain=true) @Getter @Setter protected Boolean doBusiness = Boolean.FALSE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		rootDataProducerHelper.setBasePackage(this.getClass().getPackage());
	}
	
	/**/
	protected abstract void structure(Listener listener);
	protected abstract void doBusiness(Listener listener);
	protected abstract void synchronize(Listener listener);
	protected abstract Package getBasePackage();
	
	public void produce(Listener listener){
		this.listener =listener;
		rootDataProducerHelper.setBasePackage(getBasePackage());		
		if(Boolean.TRUE.equals(structurationEnabled))
			structure(listener);
    	if(Boolean.TRUE.equals(synchronizationEnabled))
    		synchronize(listener);
    	if(Boolean.TRUE.equals(doBusiness))
    		doBusiness(listener);
	}
	
	
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected <T extends AbstractIdentifiable> void create(Collection<T> objects){
        genericBusiness.create((Collection<AbstractIdentifiable>) objects);
    }
	
	@Deprecated
	public <T extends AbstractEnumeration> T createEnumeration(Class<T> aClass,String code, String name) {
		return rootDataProducerHelper.createEnumeration(aClass, code, name);
	}

	public <T extends AbstractEnumeration> T createEnumeration(Class<T> aClass,String name) {
		return rootDataProducerHelper.createEnumeration(aClass, name);
	}
	public <T extends AbstractEnumeration> void createEnumerations(Class<T> aClass,Object[] values) {
		rootDataProducerHelper.createEnumerations(aClass, values);
	}

	public IntervalCollection createIntervalCollection(String code, String[][] values, String codeSeparator,Boolean create) {
		return rootDataProducerHelper.createIntervalCollection(code, values, codeSeparator, create);
	}

	public IntervalCollection createIntervalCollection(String code, String[][] values, String codeSeparator) {
		return rootDataProducerHelper.createIntervalCollection(code, values, codeSeparator);
	}

	public IntervalCollection createIntervalCollection(String code, String[][] values) {
		return rootDataProducerHelper.createIntervalCollection(code, values);
	}

	public <T extends AbstractIdentifiable> T create(T object) {
		return rootDataProducerHelper.create(object);
	}
	public <T extends AbstractIdentifiable> T update(T object) {
		return rootDataProducerHelper.update(object);
	}

	public <T extends AbstractIdentifiable> void createMany(@SuppressWarnings("unchecked") T...objects) {
		rootDataProducerHelper.createMany(objects);
	}

	public <T extends AbstractEnumeration> T getEnumeration(Class<T> aClass,String code) {
		return rootDataProducerHelper.getEnumeration(aClass, code);
	}
	
	public void addContacts(ContactCollection collection, String[] addresses, String[] landNumbers,
			String[] mobileNumbers, String[] postalBoxes, String[] emails, String[] websites) {
		rootDataProducerHelper.addContacts(collection, addresses, landNumbers, mobileNumbers, postalBoxes, emails,
				websites);
	}

	
	
	protected void flush(String message){
		if(listener==null)
			return;
		listener.flush();
		System.out.println("Flushing"+(message==null?"":" - "+message));
	}
	
	/**/
	
	protected <IDENTIFIABLE extends AbstractIdentifiable> void flush(Class<IDENTIFIABLE> identifiableClass,TypedBusiness<IDENTIFIABLE> business,Collection<IDENTIFIABLE> identifiables,Long minimumSize){
		if(identifiables.isEmpty() || ( minimumSize!=null && identifiables.size() < minimumSize))
			return; 
		System.out.println("Creating "+identifiables.size()+" "+identifiableClass.getSimpleName());
		if(business==null){
			@SuppressWarnings("unchecked")
			Collection<AbstractIdentifiable> collection = (Collection<AbstractIdentifiable>) identifiables;
			genericBusiness.create(collection);
		}else
			business.create(identifiables);
		flush(identifiableClass.getSimpleName());	
		identifiables.clear();
	}
	
	protected <IDENTIFIABLE extends AbstractIdentifiable> void flush(Class<IDENTIFIABLE> identifiableClass,TypedBusiness<IDENTIFIABLE> business,Collection<IDENTIFIABLE> identifiables){
		flush(identifiableClass, business, identifiables, null);
	}
	
	protected <IDENTIFIABLE extends AbstractIdentifiable> void flush(Class<IDENTIFIABLE> identifiableClass,Collection<IDENTIFIABLE> identifiables){
		flush(identifiableClass, null, identifiables, null);
	}
		
	/**/
	
	public static interface Listener{
		
		void flush();
		
		/**/
		
		public static class Adapter implements Listener,Serializable{

			private static final long serialVersionUID = -2345108310426625945L;

			@Override
			public void flush() {}
			
			/**/
			
			public static class Default extends Adapter implements Serializable{

				private static final long serialVersionUID = -2345108310426625945L;

				@Override
				public void flush() {}
				
			}
			
		}
	}
	
}
