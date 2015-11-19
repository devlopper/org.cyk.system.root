package org.cyk.system.root.business.impl;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
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
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.system.root.model.geography.Website;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

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
	
	@Getter private Collection<RootDataProducerHelperListener> rootDataProducerHelperListeners = new ArrayList<>();
	
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
		for(RootDataProducerHelperListener listener : rootDataProducerHelperListeners)
			listener.set(data);
		return (T) genericBusiness.create(data);
	}
	
	public <T extends AbstractEnumeration> T createEnumeration(Class<T> aClass,String name){
		return createEnumeration(aClass, getCode(name), name);
	}
	
	private String getCode(String name){
		return StringUtils.remove(name, Constant.CHARACTER_SPACE);
	}
	
	public <T extends AbstractEnumeration> void createEnumerations(Class<T> aClass,Object[] values){
		Collection<AbstractIdentifiable> collection = new ArrayList<>();
		for(Object value : values){
			T data = newInstance(aClass);
			data.setCode(getCode((String)value));
			data.setName((String)value);
			for(RootDataProducerHelperListener listener : rootDataProducerHelperListeners)
				listener.set(data);
			collection.add(data);
		}
		genericBusiness.create(collection);
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
	
	public PhoneNumber addPhoneNumber(ContactCollection collection,Country country,PhoneNumberType type,String number){
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setCollection(collection);
		phoneNumber.setCountry(country);
		phoneNumber.setType(type);
		phoneNumber.setNumber(number);
		if(collection.getPhoneNumbers()==null)
			collection.setPhoneNumbers(new ArrayList<PhoneNumber>());
		collection.getPhoneNumbers().add(phoneNumber);
		return phoneNumber;
	}
	
	public PhoneNumber addPhoneNumber(ContactCollection collection,PhoneNumberType type,String number){
		return addPhoneNumber(collection,RootBusinessLayer.getInstance().getCountryCoteDivoire(), type, number);
	}
	public PhoneNumber addLandPhoneNumber(ContactCollection collection,String number){
		return addPhoneNumber(collection,RootBusinessLayer.getInstance().getCountryCoteDivoire(), RootBusinessLayer.getInstance().getLandPhoneNumberType(), number);
	}
	public PhoneNumber addMobilePhoneNumber(ContactCollection collection,String number){
		return addPhoneNumber(collection,RootBusinessLayer.getInstance().getCountryCoteDivoire(), RootBusinessLayer.getInstance().getMobilePhoneNumberType(), number);
	}
	public void addContacts(ContactCollection collection,String[] addresses,String[] landNumbers,String[] mobileNumbers,String[] postalBoxes,String[] emails,String[] websites){
		if(addresses!=null)
			for(String address : addresses){
				Location location = new Location(collection, RootBusinessLayer.getInstance().getCountryCoteDivoire().getLocality(), address);
				if(collection.getLocations()==null)
					collection.setLocations(new ArrayList<Location>());
				collection.getLocations().add(location);
			}
		if(landNumbers!=null)
			for(String number : landNumbers)
				addLandPhoneNumber(collection, number);
		if(mobileNumbers!=null)
			for(String number : mobileNumbers)
				addMobilePhoneNumber(collection, number);
		if(postalBoxes!=null)
			for(String postalbox : postalBoxes){
				PostalBox postalBox = new PostalBox(postalbox);
				if(collection.getPostalBoxs()==null)
					collection.setPostalBoxs(new ArrayList<PostalBox>());
				collection.getPostalBoxs().add(postalBox);
			}
		if(emails!=null)
			for(String email : emails){
				ElectronicMail electronicMail = new ElectronicMail(collection, email);
				if(collection.getElectronicMails()==null)
					collection.setElectronicMails(new ArrayList<ElectronicMail>());
				collection.getElectronicMails().add(electronicMail);
			}
		if(websites!=null)
			for(String websitev : websites){
				Website website;
				try {
					website = new Website(websitev);
					if(collection.getWebsites()==null)
						collection.setWebsites(new ArrayList<Website>());
					collection.getWebsites().add(website);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
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

	/**/
	
	public static interface RootDataProducerHelperListener{
		void set(Object object);
		
		/**/
		
		public static class Adapter extends BeanAdapter implements RootDataProducerHelperListener,Serializable{
			private static final long serialVersionUID = 581887995233346336L;
			@Override
			public void set(Object object) {}
			
			/**/
			
			public static class Default extends Adapter implements Serializable{
				private static final long serialVersionUID = 581887995233346336L;
		
			}
		}
	}
	
}
