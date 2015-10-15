package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.inject.Inject;

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

	public IntervalCollection createIntervalCollection(String[][] values,Boolean create){
		return rootDataProducerHelper.createIntervalCollection(values,create);
	}
	public IntervalCollection createIntervalCollection(String[][] values){
		return rootDataProducerHelper.createIntervalCollection(values);
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

	public File createFile(String relativePath, String name) {
		return rootDataProducerHelper.createFile(relativePath, name);
	}

	public byte[] getResourceAsBytes(String relativePath) {
		return rootDataProducerHelper.getResourceAsBytes(relativePath);
	}

	public <T extends AbstractEnumeration> T getEnumeration(Class<T> aClass,String code) {
		return rootDataProducerHelper.getEnumeration(aClass, code);
	}

	public abstract void produce();
	
}
