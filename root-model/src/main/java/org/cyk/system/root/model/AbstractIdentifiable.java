package org.cyk.system.root.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
import org.cyk.system.root.model.globalidentification.GlobalIdentifier.Processing;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.userinterface.style.CascadeStyleSheet;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/*lombok*/

/*mapping - jpa*/
@MappedSuperclass @Getter @Setter
public abstract class AbstractIdentifiable extends AbstractModelElement implements Identifiable<Long>, Serializable{

	private static final long serialVersionUID = 1L;
	
	public static AbstractMethod<Boolean,AbstractIdentifiable> GLOBAL_IDENTIFIER_BUILDABLE;
	public static AbstractMethod<Object,GlobalIdentifier> CREATE_GLOBAL_IDENTIFIER;
	public static AbstractMethod<Object,GlobalIdentifier> UPDATE_GLOBAL_IDENTIFIER;
	
	/* Persisted */
	
	@Id @GeneratedValue @Column(name=COLUMN_IDENTIFIER) protected Long identifier;// Generation is customizable using mapping file
	
	/**
	 * Used to join subsystem
	 */
	@OneToOne @JoinColumn(name=COLUMN_GLOBAL_IDENTIFIER) protected GlobalIdentifier globalIdentifier;
	
	/* Transients */
	
	@Transient protected Boolean cascadeOperationToMaster = Boolean.FALSE;
	@Transient protected Boolean cascadeOperationToChildren = Boolean.FALSE;
	@Transient protected Boolean checkIfExistOnDelete = Boolean.FALSE;
	
	/**
	 * Used in user interface to reference one and only one parent
	 */
	@Transient @Accessors(chain=true) private AbstractIdentifiable __parent__;
	@Transient private Collection<AbstractIdentifiable> parents;
	@Transient private Collection<AbstractIdentifiable> children;
	
	@Transient private IdentifiableRuntimeCollection<MetricCollectionIdentifiableGlobalIdentifier> metricCollectionIdentifiableGlobalIdentifiers;
	
	public IdentifiableRuntimeCollection<MetricCollectionIdentifiableGlobalIdentifier> getMetricCollectionIdentifiableGlobalIdentifiers(){
		if(metricCollectionIdentifiableGlobalIdentifiers == null)
			metricCollectionIdentifiableGlobalIdentifiers = new IdentifiableRuntimeCollection<>();
		return metricCollectionIdentifiableGlobalIdentifiers;
	}
	
	public AbstractIdentifiable setCode(String code){
		getGlobalIdentifierCreateIfNull().setCode(code);
		return this;
	}
	public String getCode(){
		return globalIdentifier == null ? null : globalIdentifier.getCode();
	}
	
	public AbstractIdentifiable setName(String name){
		getGlobalIdentifierCreateIfNull().setName(name);
		return this;
	}
	public String getName(){
		return globalIdentifier == null ? null : globalIdentifier.getName();
	}
	
	public AbstractIdentifiable setDescription(String description){
		getGlobalIdentifierCreateIfNull().setDescription(description);
		return this;
	}
	public String getDescription(){
		return globalIdentifier == null ? null : globalIdentifier.getDescription();
	}
	
	public AbstractIdentifiable setAbbreviation(String abbreviation){
		getGlobalIdentifierCreateIfNull().setAbbreviation(abbreviation);
		return this;
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
	
	public void setRequired(Boolean required){
		getGlobalIdentifierCreateIfNull().setRequired(required);
	}
	public Boolean getRequired(){
		return globalIdentifier == null ? null : globalIdentifier.getRequired();
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
	
	public void setDefaulted(Boolean defaulted){
		getGlobalIdentifierCreateIfNull().setDefaulted(defaulted);
	}
	public Boolean getDefaulted(){
		return globalIdentifier == null ? null : globalIdentifier.getDefaulted();
	}
	
	public void setCascadeStyleSheet(CascadeStyleSheet cascadeStyleSheet){
		getGlobalIdentifierCreateIfNull().setCascadeStyleSheet(cascadeStyleSheet);
	}
	public CascadeStyleSheet getCascadeStyleSheet(){
		return globalIdentifier == null ? null : globalIdentifier.getCascadeStyleSheet();
	}

	public void setDerived(Boolean derived){
		getGlobalIdentifierCreateIfNull().setDerived(derived);
	}
	public Boolean getDerived(){
		return globalIdentifier == null ? null : globalIdentifier.getDerived();
	}
	
	public Collection<AbstractIdentifiable> getParents(){
		if(parents==null)
			parents =  new ArrayList<>();
		return parents;
	}
	
	/**
	 * Set the parent.
	 * Clear existing parents and add this parent to the list
	 * @param parent
	 */
	public AbstractIdentifiable set__parent__(AbstractIdentifiable parent){
		getParents().clear();
		if(parent!=null)
			getParents().add(parent);
		this.__parent__ = parent;
		return this;
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

	public String getShortName(){
		return StringUtils.isBlank(getAbbreviation()) ? getCode() : getAbbreviation();
	}
	
	protected String getMemoryAddress(){
		return getClass().getSimpleName()+Constant.CHARACTER_AT+String.valueOf(System.identityHashCode(this)); 
		//StringUtils.substringBefore(ToStringBuilder.reflectionToString(this, ToStringStyle.NO_FIELD_NAMES_STYLE),"[");
	}
	
	public Processing getProcessing(){
		return getGlobalIdentifierCreateIfNull().getProcessing();
	}
	
	public void setProcessing(Processing processing){
		getGlobalIdentifierCreateIfNull().setProcessing(processing);
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
		if(globalIdentifier!=null && !StringHelper.getInstance().isBlank(globalIdentifier.getCode()))
			return globalIdentifier.getCode();
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
	
	public void _set(AbstractIdentifiable component,String...fieldNames){
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
				LoggingHelper.getInstance().getLogger().getMessageBuilder(Boolean.TRUE).addManyParameters("before persist",new Object[]{"object",this}).getLogger()
				.execute(getClass(),LoggingHelper.Logger.Level.TRACE,null);
				entry.getValue().onPrePersist(this);
			}
		}
		if(globalIdentifier==null && GLOBAL_IDENTIFIER_BUILDABLE!=null && Boolean.TRUE.equals(GLOBAL_IDENTIFIER_BUILDABLE.execute(this)) 
				/*&& BUILD_GLOBAL_IDENTIFIER_VALUE!=null*/ && CREATE_GLOBAL_IDENTIFIER!=null){
			globalIdentifier = getGlobalIdentifierCreateIfNull();
		}else{
			
		}
		if(globalIdentifier!=null){
			if(globalIdentifier.getIdentifiable()==null)
				globalIdentifier.setIdentifiable(this);
			/*if(BUILD_GLOBAL_IDENTIFIER_VALUE!=null && StringUtils.isBlank(globalIdentifier.getIdentifier()))
				globalIdentifier.setIdentifier(BUILD_GLOBAL_IDENTIFIER_VALUE.execute(this));
			
			if(BUILD_GLOBAL_IDENTIFIER_CREATION_DATE!=null)
				globalIdentifier.setCreationDate(BUILD_GLOBAL_IDENTIFIER_CREATION_DATE.execute(this));
			if(BUILD_GLOBAL_IDENTIFIER_CREATED_BY!=null)
				globalIdentifier.setCreatedBy(BUILD_GLOBAL_IDENTIFIER_CREATED_BY.execute(this));
			*/
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
				LoggingHelper.getInstance().getLogger().getMessageBuilder(Boolean.TRUE).addManyParameters("before persist",new Object[]{"object",this}).getLogger()
				.execute(getClass(),LoggingHelper.Logger.Level.TRACE,null);
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
	
	@Getter @Setter
	public static class Filter<T extends AbstractIdentifiable> extends FilterHelper.Filter<T> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;

		protected GlobalIdentifier.Filter globalIdentifier = new GlobalIdentifier.Filter();
		
		public Filter() {
			addCriterias(globalIdentifier);
		}
		
		public Filter(Filter<T> criterias) {
			super(criterias);
		}
		
		@Override
		public FilterHelper.Filter<T> set(String string) {
			globalIdentifier.set(string);
			return super.set(string);
		}
		
		@Override
		public FilterHelper.Filter<T> setExcluded(Collection<T> excluded) {
			FilterHelper.Filter<T> filter =  super.setExcluded(excluded);
			if(CollectionHelper.getInstance().isEmpty(excluded))
				globalIdentifier.setExcluded(null);
			else
				for(AbstractIdentifiable identifiable : excluded){
					if(identifiable.getGlobalIdentifier()!=null)
						//globalIdentifier.addExcluded(identifiable.getGlobalIdentifier());
						this.globalIdentifier.getCode().addExcluded(identifiable.getCode());
				}
			return filter;
		}

	}
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_GLOBAL_IDENTIFIER = "globalIdentifier";
	public static final String FIELD_PARENT = "__parent__";
	
	public static final String COLUMN_IDENTIFIER = "identifier";
	public static final String COLUMN_GLOBAL_IDENTIFIER = "globalidentifier";
	
	/**/
	
	public static class Inputs extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	static {
		org.cyk.utility.common.helper.FilterHelper.Filter.ClassLocator.map(org.cyk.system.FilterHelper.ClassLocator.class);
	}
 
}
