package org.cyk.system.root.business.impl;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton
public class RootDataProducerHelper extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 2282674526022995453L;
	
	private static RootDataProducerHelper INSTANCE;
	
	@Inject private GenericBusiness genericBusiness;
	@Inject private IntervalCollectionBusiness intervalCollectionBusiness;
	@Inject private FileBusiness fileBusiness;
	
	@Inject private GenericDao genericDao;
	
	@Getter private Package basePackage;
	private Deque<Package> basePackageQueue = new ArrayDeque<>();
	private Boolean basePackageQueueingEnabled = Boolean.FALSE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> T create(T object){
		return (T) genericBusiness.create(object);
	}
	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> T update(T object){
		return (T) genericBusiness.update(object);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> void createMany(T...objects){
		if(objects!=null){
			Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
			for(T t : objects)
				identifiables.add(t);
			genericBusiness.create(identifiables);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractEnumeration> T createEnumeration(Class<T> aClass,String code,String name){
		T data = newInstance(aClass);
		data.setCode(code);
		data.setName(name);
		return (T) genericBusiness.create(data);
	}
	
	public <T extends AbstractEnumeration> T createEnumeration(Class<T> aClass,String name){
		return createEnumeration(aClass, StringUtils.remove(name, Constant.CHARACTER_SPACE), name);
	}
	
	public IntervalCollection createIntervalCollection(String code,String[][] values,Boolean create){
		IntervalCollection collection = new IntervalCollection(code);
		for(String[] v : values){
			collection.getCollection().add(new Interval(collection,v[0],v[1],new BigDecimal(v[2]),new BigDecimal(v[3])));
		}
		if(Boolean.TRUE.equals(create))
			return intervalCollectionBusiness.create(collection);
		return collection;
	}
	public IntervalCollection createIntervalCollection(String code,String[][] values){
		return createIntervalCollection(code,values, Boolean.TRUE);
	}
	
	public File createFile(Package basePackage,String relativePath,String name){
		return fileBusiness.create(fileBusiness.process(getResourceAsBytes(basePackage,relativePath),name));
	}
	
	public byte[] getResourceAsBytes(Package basePackage,String relativePath){
    	String path = "/"+StringUtils.replace( (basePackage==null?(this.basePackage==null?this.getClass().getPackage():this.basePackage):basePackage).getName(), ".", "/")+"/";
    	try {
    		logDebug("Getting resource as bytes {}", path+relativePath);
    		return IOUtils.toByteArray(this.getClass().getResourceAsStream(path+relativePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractEnumeration> T getEnumeration(Class<T> aClass,String code){
		return (T) genericDao.use(aClass).use(aClass).select().where("code", code).one();
	}
	
	/**/
	
	public void setBasePackage(Package basePackage) {
		if(Boolean.TRUE.equals(basePackageQueueingEnabled)){
			if(basePackageQueue.peek().equals(basePackage))
				;
			else
				basePackageQueue.push(this.basePackage);
		}
		__setBasePackage__(basePackage);
	}
	
	public void setToPreviousBasePackage(){
		if(Boolean.TRUE.equals(basePackageQueueingEnabled)){
			if(basePackageQueue.isEmpty())
				return;
			__setBasePackage__(basePackageQueue.pop());
		}else
			;
	}
	
	private void __setBasePackage__(Package basePackage) {
		this.basePackage = basePackage;
		logTrace("Base package set to {}", basePackage);
	}
	
	public static RootDataProducerHelper getInstance() {
		return INSTANCE;
	}

}
