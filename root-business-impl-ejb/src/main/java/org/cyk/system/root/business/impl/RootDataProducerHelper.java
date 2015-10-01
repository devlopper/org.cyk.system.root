package org.cyk.system.root.business.impl;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
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
	
	@Inject private GenericBusiness genericBusiness;
	@Inject private IntervalBusiness intervalBusiness;
	@Inject private FileBusiness fileBusiness;
	
	@Inject private GenericDao genericDao;
	
	@Getter @Setter private Package basePackage;
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> T create(T object){
		return (T) genericBusiness.create(object);
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
	
	public Interval createInterval(IntervalCollection collection,String code,String name,String low,String high){
		if(collection!=null && collection.getIdentifier()==null)
			genericBusiness.create(collection);
		Interval interval = new Interval();
		interval.setCollection(collection);
		interval.setCode(code);
		interval.setName(name);
		interval.setLow(new BigDecimal(low));
		interval.setHigh(new BigDecimal(high));
		return intervalBusiness.create(interval);
	}
	
	public File createFile(String relativePath,String name){
		return fileBusiness.create(fileBusiness.process(getResourceAsBytes(relativePath),name));
	}
	
	public byte[] getResourceAsBytes(String relativePath){
    	String path = "/"+StringUtils.replace( (basePackage==null?this.getClass().getPackage():basePackage).getName(), ".", "/")+"/";
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

}
