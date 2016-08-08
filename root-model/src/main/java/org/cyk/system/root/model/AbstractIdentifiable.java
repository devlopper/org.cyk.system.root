package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map.Entry;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.StringMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

/*lombok*/

/*mapping - jpa*/
@MappedSuperclass @Getter @Setter
public abstract class AbstractIdentifiable extends AbstractModelElement implements Identifiable<Long>, Serializable{

	private static final long serialVersionUID = 1L;
	
	public static StringMethod<AbstractIdentifiable> BUILD_GLOBAL_IDENTIFIER_VALUE;
	public static AbstractMethod<Party,AbstractIdentifiable> BUILD_GLOBAL_IDENTIFIER_CREATED_BY;
	public static AbstractMethod<Date,AbstractIdentifiable> BUILD_GLOBAL_IDENTIFIER_CREATION_DATE;
	public static AbstractMethod<Boolean,AbstractIdentifiable> GLOBAL_IDENTIFIER_BUILDABLE;
	public static AbstractMethod<Object,GlobalIdentifier> CREATE_GLOBAL_IDENTIFIER;
	public static AbstractMethod<Object,GlobalIdentifier> UPDATE_GLOBAL_IDENTIFIER;
	
	@Id @GeneratedValue protected Long identifier;// Generation is customizable using mapping file

	@OneToOne protected GlobalIdentifier globalIdentifier;
	
	@Transient protected Processing processing;
	
	public void setCode(String code){
		getGlobalIdentifierCreateIfNull().setCode(code);
	}
	public String getCode(){
		return globalIdentifier == null ? null : globalIdentifier.getCode();
	}
	
	public void setName(String name){
		getGlobalIdentifierCreateIfNull().setName(name);
	}
	public String getName(){
		return globalIdentifier == null ? null : globalIdentifier.getName();
	}
	
	public void setDescription(String description){
		getGlobalIdentifierCreateIfNull().setDescription(description);
	}
	public String getDescription(){
		return globalIdentifier == null ? null : globalIdentifier.getDescription();
	}
	
	public void setAbbreviation(String abbreviation){
		getGlobalIdentifierCreateIfNull().setAbbreviation(abbreviation);
	}
	public String getAbbreviation(){
		return globalIdentifier == null ? null : globalIdentifier.getAbbreviation();
	}
	
	public void setImage(File image){
		getGlobalIdentifierCreateIfNull().setImage(image);
	}
	public File getImage(){
		return globalIdentifier == null ? null : globalIdentifier.getImage();
	}
	
	public void setExistencePeriod(Period period){
		getGlobalIdentifierCreateIfNull().setExistencePeriod(period);
	}
	public Period getExistencePeriod(){
		return globalIdentifier == null ? null : globalIdentifier.getExistencePeriod();
	}
	
	public void setBirthDate(Date date){
		getGlobalIdentifierCreateIfNull().getExistencePeriod().setFromDate(date);
	}
	public Date getBirthDate(){
		return getGlobalIdentifier().getExistencePeriod().getFromDate();
	}
	
	public void setDeathDate(Date date){
		getGlobalIdentifierCreateIfNull().getExistencePeriod().setToDate(date);
	}
	public Date getDeathDate(){
		return getGlobalIdentifier().getExistencePeriod().getToDate();
	}
	
	public void setBirthLocation(Location location){
		getGlobalIdentifierCreateIfNull().setBirthLocation(location);
	}
	public Location getBirthLocation(){
		return getGlobalIdentifier().getBirthLocation();
	}
	
	public void setOtherDetails(String otherDetails){
		getGlobalIdentifierCreateIfNull().setOtherDetails(otherDetails);
	}
	public String getOtherDetails(){
		return globalIdentifier == null ? null : globalIdentifier.getOtherDetails();
	}
	
	public GlobalIdentifier getGlobalIdentifierCreateIfNull(){
		if(globalIdentifier==null)
			globalIdentifier = new GlobalIdentifier(this);
		return globalIdentifier;
	}
	
	public Processing getProcessing(){
		if(processing==null)
			processing = new Processing();
		return  processing;
	}
	
	protected String getMemoryAddress(){
		return getClass().getSimpleName()+Constant.CHARACTER_AT+String.valueOf(System.identityHashCode(this)); 
		//StringUtils.substringBefore(ToStringBuilder.reflectionToString(this, ToStringStyle.NO_FIELD_NAMES_STYLE),"[");
	}
	
	private String __identifier__(){
		return //getMemoryAddress()
				getClass().getSimpleName()
				+Constant.CHARACTER_SLASH+StringUtils.defaultString(identifier==null?null:identifier.toString(),Constant.CHARACTER_QUESTION_MARK.toString());
	}
	
	@Override
	public int hashCode() {
		String id = __identifier__();
		return id==null?HashCodeBuilder.reflectionHashCode(this, false):id.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if(!(object instanceof AbstractIdentifiable))
			return Boolean.FALSE;
		String id1 = __identifier__() , id2 = ((AbstractIdentifiable) object).__identifier__();
		if(id1==null || id2==null)
			return Boolean.FALSE;
		return id1.equals(id2);
	}
	
	@Override
	public String toString() {
		//return __identifier__();
		return StringUtils.substringAfterLast(StringUtils.substringBefore(ToStringBuilder.reflectionToString(this, ToStringStyle.NO_FIELD_NAMES_STYLE),"["),Constant.CHARACTER_DOT.toString())
				+Constant.CHARACTER_SLASH+StringUtils.defaultString(identifier==null?null:identifier.toString(),Constant.CHARACTER_QUESTION_MARK.toString());
	}
	
	@Override
	public String getUiString() {
		if(globalIdentifier==null)
			return "?? NO UI STRING SPECIFIED ??" ;
		if(StringUtils.isNotBlank(globalIdentifier.getCode()))
			return globalIdentifier.getCode();
		if(StringUtils.isNotBlank(globalIdentifier.getName()))
			return globalIdentifier.getName();
		return  globalIdentifier.getIdentifier();
	}
	
	/**/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PrePersist
	private void onPrePersist() {
		for(Entry<Class<? extends AbstractIdentifiable>, IdentifiableLifeCyleEventListener.AbstractIdentifiable> entry : IdentifiableLifeCyleEventListener.AbstractIdentifiable.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				getLogger().trace("Pre persist called for {}",this);
				entry.getValue().onPrePersist(this);
			}
		}
		if(globalIdentifier==null && GLOBAL_IDENTIFIER_BUILDABLE!=null && Boolean.TRUE.equals(GLOBAL_IDENTIFIER_BUILDABLE.execute(this)) 
				&& BUILD_GLOBAL_IDENTIFIER_VALUE!=null && CREATE_GLOBAL_IDENTIFIER!=null){
			globalIdentifier = getGlobalIdentifierCreateIfNull();
		}else{
			
		}
		if(globalIdentifier!=null){
			if(globalIdentifier.getIdentifiable()==null)
				globalIdentifier.setIdentifiable(this);
			globalIdentifier.setIdentifier(BUILD_GLOBAL_IDENTIFIER_VALUE.execute(this));
			globalIdentifier.setCreationDate(BUILD_GLOBAL_IDENTIFIER_CREATION_DATE.execute(this));
			globalIdentifier.setCreatedBy(BUILD_GLOBAL_IDENTIFIER_CREATED_BY.execute(this));
			
			CREATE_GLOBAL_IDENTIFIER.execute(globalIdentifier);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostPersist
	private void onPostPersist() {
		for(Entry<Class<? extends AbstractIdentifiable>, IdentifiableLifeCyleEventListener.AbstractIdentifiable> entry : IdentifiableLifeCyleEventListener.AbstractIdentifiable.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				getLogger().trace("Post persist called for {}",this);
				entry.getValue().onPostPersist(this);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostLoad
	private void onPostLoad() {
		for(Entry<Class<? extends AbstractIdentifiable>, IdentifiableLifeCyleEventListener.AbstractIdentifiable> entry : IdentifiableLifeCyleEventListener.AbstractIdentifiable.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				entry.getValue().onPostLoad(this);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreUpdate
	private void onPreUpdate() {
		for(Entry<Class<? extends AbstractIdentifiable>, IdentifiableLifeCyleEventListener.AbstractIdentifiable> entry : IdentifiableLifeCyleEventListener.AbstractIdentifiable.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				entry.getValue().onPreUpdate(this);
			}
		}
		
		if(globalIdentifier!=null){
			UPDATE_GLOBAL_IDENTIFIER.execute(globalIdentifier);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostUpdate
	private void onPostUpdate() {
		for(Entry<Class<? extends AbstractIdentifiable>, IdentifiableLifeCyleEventListener.AbstractIdentifiable> entry : IdentifiableLifeCyleEventListener.AbstractIdentifiable.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				entry.getValue().onPostUpdate(this);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreRemove
	private void onPreRemove() {
		for(Entry<Class<? extends AbstractIdentifiable>, IdentifiableLifeCyleEventListener.AbstractIdentifiable> entry : IdentifiableLifeCyleEventListener.AbstractIdentifiable.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				entry.getValue().onPreRemove(this);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostRemove
	private void onPostRemove() {
		for(Entry<Class<? extends AbstractIdentifiable>, IdentifiableLifeCyleEventListener.AbstractIdentifiable> entry : IdentifiableLifeCyleEventListener.AbstractIdentifiable.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				entry.getValue().onPostRemove(this);
			}
		}
	}
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_GLOBAL_IDENTIFIER = "globalIdentifier";
	
	protected Logger getLogger(){
		return LoggerFactory.getLogger(getClass());
	}
	
	/**/
	
	@Getter @Setter
	/**
	 * Informations about client processing
	 * @author Christian Yao Komenan
	 *
	 */
	public static class Processing implements Serializable {
		private static final long serialVersionUID = -6123968511493504593L;
		
		private String identifier;
		private Party party;
		private Date date;
	}
 
}