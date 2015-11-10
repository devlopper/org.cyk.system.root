package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractFakedDataProducer extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6589529849414444956L;

	@Inject protected RootDataProducerHelper rootDataProducerHelper;
	@Inject protected RootBusinessLayer rootBusinessLayer;
	@Inject protected RootRandomDataProvider rootRandomDataProvider;
	@Inject protected GenericBusiness genericBusiness;
	protected FakedDataProducerListener listener;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		rootDataProducerHelper.setBasePackage(this.getClass().getPackage());
	}
	
	public <T extends AbstractEnumeration> T createEnumeration(Class<T> aClass,String code, String name) {
		return rootDataProducerHelper.createEnumeration(aClass, code, name);
	}

	public <T extends AbstractEnumeration> T createEnumeration(Class<T> aClass,String name) {
		return rootDataProducerHelper.createEnumeration(aClass, name);
	}

	public IntervalCollection createIntervalCollection(String code,String[][] values,Boolean create){
		return rootDataProducerHelper.createIntervalCollection(code,values,create);
	}
	public IntervalCollection createIntervalCollection(String code,String[][] values){
		return rootDataProducerHelper.createIntervalCollection(code,values);
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

	public File createFile(Package basePackage,String relativePath, String name) {
		return rootDataProducerHelper.createFile(basePackage,relativePath, name);
	}
	public File createFile(String relativePath, String name) {
		return createFile(rootDataProducerHelper.getBasePackage(),relativePath, name);
	}

	public byte[] getResourceAsBytes(Package basePackage,String relativePath) {
		return rootDataProducerHelper.getResourceAsBytes(basePackage,relativePath);
	}
	
	public byte[] getResourceAsBytes(String relativePath) {
		return getResourceAsBytes(rootDataProducerHelper.getBasePackage(),relativePath);
	}

	public <T extends AbstractEnumeration> T getEnumeration(Class<T> aClass,String code) {
		return rootDataProducerHelper.getEnumeration(aClass, code);
	}

	public abstract void produce(FakedDataProducerListener listener);
	
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
	
	public static interface FakedDataProducerListener{
		void flush();
	}
	
	public static class FakedDataProducerAdapter implements FakedDataProducerListener{

		@Override
		public void flush() {}
		
	}
}
