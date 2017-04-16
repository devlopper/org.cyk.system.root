package org.cyk.system.root.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.StringMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	/* Persisted */
	
	@Id @GeneratedValue protected Long identifier;// Generation is customizable using mapping file
	
	/**
	 * Used to join subsystem
	 */
	@OneToOne protected GlobalIdentifier globalIdentifier;
	
	/* Transients */
	
	@Transient protected Processing processing;
	@Transient protected Boolean cascadeOperationToMaster = Boolean.FALSE;
	@Transient protected Boolean cascadeOperationToChildren = Boolean.FALSE;
	
	@Transient private Collection<AbstractIdentifiable> parents;
	@Transient private Collection<AbstractIdentifiable> children;
	
	@Transient private IdentifiableRuntimeCollection<MetricCollectionIdentifiableGlobalIdentifier> metricCollectionIdentifiableGlobalIdentifiers;
	
	public IdentifiableRuntimeCollection<MetricCollectionIdentifiableGlobalIdentifier> getMetricCollectionIdentifiableGlobalIdentifiers(){
		if(metricCollectionIdentifiableGlobalIdentifiers == null)
			metricCollectionIdentifiableGlobalIdentifiers = new IdentifiableRuntimeCollection<>();
		return metricCollectionIdentifiableGlobalIdentifiers;
	}
	
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
		return globalIdentifier == null ? null : globalIdentifier.getExistencePeriod().getFromDate();
	}
	
	public void setDeathDate(Date date){
		getGlobalIdentifierCreateIfNull().getExistencePeriod().setToDate(date);
	}
	public Date getDeathDate(){
		return globalIdentifier == null ? null : globalIdentifier.getExistencePeriod().getToDate();
	}
	
	public void setBirthLocation(Location location){
		getGlobalIdentifierCreateIfNull().setBirthLocation(location);
	}
	public Location getBirthLocation(){
		return globalIdentifier == null ? null : globalIdentifier.getBirthLocation();
	}
	
	public void setWeight(BigDecimal weight){
		getGlobalIdentifierCreateIfNull().setWeight(weight);
	}
	public BigDecimal getWeight(){
		return globalIdentifier == null ? null : globalIdentifier.getWeight();
	}
	
	public void setOrderNumber(Long orderNumber){
		getGlobalIdentifierCreateIfNull().setOrderNumber(orderNumber);
	}
	public Long getOrderNumber(){
		return globalIdentifier == null ? null : globalIdentifier.getOrderNumber();
	}
	
	public void setOtherDetails(String otherDetails){
		getGlobalIdentifierCreateIfNull().setOtherDetails(otherDetails);
	}
	public String getOtherDetails(){
		return globalIdentifier == null ? null : globalIdentifier.getOtherDetails();
	}
	
	public void setInitialized(Boolean initialized){
		getGlobalIdentifierCreateIfNull().setInitialized(initialized);
	}
	public Boolean getInitialized(){
		return globalIdentifier == null ? null : globalIdentifier.getInitialized();
	}
	
	public void setSupportingDocument(File supportingDocument){
		getGlobalIdentifierCreateIfNull().setSupportingDocument(supportingDocument);
	}
	public File getSupportingDocument(){
		return globalIdentifier == null ? null : globalIdentifier.getSupportingDocument();
	}
	
	public void setUsable(Boolean usable){
		getGlobalIdentifierCreateIfNull().setUsable(usable);
	}
	public Boolean getUsable(){
		return globalIdentifier == null ? null : globalIdentifier.getUsable();
	}
	
	public Collection<AbstractIdentifiable> getParents(){
		if(parents==null)
			parents =  new ArrayList<>();
		return parents;
	}
	
	public AbstractIdentifiable getParent(){
		return (AbstractIdentifiable) (parents == null || parents.isEmpty() ? null : ((List<AbstractIdentifiable>)parents).get(((List<AbstractIdentifiable>)parents).size()-1));
	}
	
	/**
	 * Set the parent.
	 * Clear existing parents and add this parent to the list
	 * @param parent
	 */
	public void setParent(AbstractIdentifiable parent){
		getParents().clear();
		getParents().add(parent);
	}
	
	public Collection<AbstractIdentifiable> getChildren(){
		if(children==null)
			children =  new ArrayList<>();
		return children;
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
	
	public String getShortName(){
		return StringUtils.isBlank(getAbbreviation()) ? getCode() : getAbbreviation();
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
	
	public void set(AbstractIdentifiable component,String...fieldNames){
		for(String fieldName : fieldNames){
			if(GlobalIdentifier.FIELD_CODE.equals(fieldName)){
				if(StringUtils.isBlank(component.getCode()))
					component.setCode(getCode());
			}else if(GlobalIdentifier.FIELD_NAME.equals(fieldName)){
				if(StringUtils.isBlank(component.getName()))
					component.setName(getName());
			}else if(GlobalIdentifier.FIELD_EXISTENCE_PERIOD.equals(fieldName)){
				if(component.getExistencePeriod().getFromDate()==null)
					component.getExistencePeriod().setFromDate(getExistencePeriod().getFromDate());
				if(component.getExistencePeriod().getToDate()==null)
					component.getExistencePeriod().setToDate(getExistencePeriod().getToDate());
			}
		}
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
			if(BUILD_GLOBAL_IDENTIFIER_VALUE!=null && StringUtils.isBlank(globalIdentifier.getIdentifier()))
				globalIdentifier.setIdentifier(BUILD_GLOBAL_IDENTIFIER_VALUE.execute(this));
			if(BUILD_GLOBAL_IDENTIFIER_CREATION_DATE!=null)
				globalIdentifier.setCreationDate(BUILD_GLOBAL_IDENTIFIER_CREATION_DATE.execute(this));
			if(BUILD_GLOBAL_IDENTIFIER_CREATED_BY!=null)
				globalIdentifier.setCreatedBy(BUILD_GLOBAL_IDENTIFIER_CREATED_BY.execute(this));
			
			if(CREATE_GLOBAL_IDENTIFIER==null)
				globalIdentifier = null;
			else{ 
				if(GlobalIdentifier.EXCLUDED.contains(getClass())){
					globalIdentifier = null;
				}else{
					CREATE_GLOBAL_IDENTIFIER.execute(globalIdentifier);
				}
			}
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