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
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.Party;
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
	
	public String getCode(){
		return globalIdentifier == null ? null : globalIdentifier.getCode();
	}
	public void setCode(String code){
		if(globalIdentifier==null)
			;
		else
			globalIdentifier.setCode(code);
	}
	
	public void setImage(File image){
		if(globalIdentifier==null)
			;
		else
			globalIdentifier.setImage(image);
	}
	
	public File getImage(){
		return globalIdentifier == null ? null : globalIdentifier.getImage();
	}
	
	public GlobalIdentifier getGlobalIdentifierCreateIfNull(){
		if(globalIdentifier==null)
			globalIdentifier = new GlobalIdentifier();
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
		return "?? NO UI STRING SPECIFIED ??";
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
		System.out.println(getClass().getSimpleName().toUpperCase());
		if(globalIdentifier==null && GLOBAL_IDENTIFIER_BUILDABLE!=null && Boolean.TRUE.equals(GLOBAL_IDENTIFIER_BUILDABLE.execute(this)) 
				&& BUILD_GLOBAL_IDENTIFIER_VALUE!=null && CREATE_GLOBAL_IDENTIFIER!=null){
			globalIdentifier = new GlobalIdentifier();
		}
		//System.out.println("   ***   ???????   *** : "+globalIdentifier);
		if(globalIdentifier!=null){
			globalIdentifier.setIdentifier(BUILD_GLOBAL_IDENTIFIER_VALUE.execute(this));
			globalIdentifier.setCreationDate(BUILD_GLOBAL_IDENTIFIER_CREATION_DATE.execute(this));
			globalIdentifier.setCreatedBy(BUILD_GLOBAL_IDENTIFIER_CREATED_BY.execute(this));
			//System.out.println("   ***   UPDATED   *** : "+globalIdentifier);
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
			System.out.println("AbstractIdentifiable.onPreUpdate() : "+globalIdentifier.getIdentifier());
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