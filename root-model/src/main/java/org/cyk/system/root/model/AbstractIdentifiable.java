package org.cyk.system.root.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.userinterface.style.CascadeStyleSheet;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.TimeHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/*lombok*/

/*mapping - jpa*/
@MappedSuperclass @Getter @Setter @Accessors(chain=true)
public abstract class AbstractIdentifiable extends org.cyk.utility.common.model.identifiable.Common/*org.cyk.utility.common.model.identifiable.IdentifiablePersistable.ByLong.BaseClass.JavaPersistenceEntity*/ implements Identifiable<java.lang.Long>, Serializable{
	private static final long serialVersionUID = 1L;
	
	public static AbstractMethod<Boolean,AbstractIdentifiable> GLOBAL_IDENTIFIER_BUILDABLE;
	public static AbstractMethod<Object,GlobalIdentifier> CREATE_GLOBAL_IDENTIFIER;
	public static AbstractMethod<Object,GlobalIdentifier> UPDATE_GLOBAL_IDENTIFIER;
	
	/* Persisted */
	
	//TODO we are using the local interface , this why we need this. If local insterface is removed then remove this property
	@Id @GeneratedValue @Column(name=COLUMN_IDENTIFIER) protected java.lang.Long identifier;// Generation is customizable using mapping file
	
	/**
	 * Used to join subsystem
	 */
	@OneToOne @JoinColumn(name=COLUMN_GLOBAL_IDENTIFIER) protected GlobalIdentifier globalIdentifier;
	
	/* Transients */
	
	@Transient @Accessors(chain=true) protected Boolean cascadeOperationToMaster;
	/**
	 * null means no restriction
	 */
	@Transient @Accessors(chain=true) protected Collection<String> cascadeOperationToMasterFieldNames;
	
	@Transient @Accessors(chain=true) protected Boolean cascadeOperationToChildren;
	/**
	 * null means no restriction
	 */
	@Transient @Accessors(chain=true) protected Collection<String> cascadeOperationToChildrenFieldNames;
	
	@Transient @Accessors(chain=true) protected Boolean checkIfExistOnDelete = Boolean.FALSE;
	
	/**
	 * Used in user interface to reference one and only one parent
	 */
	@Transient @Accessors(chain=true) private AbstractIdentifiable __parent__;
	@Transient private Collection<AbstractIdentifiable> parents;
	@Transient private Collection<AbstractIdentifiable> children;
	
	@Transient private IdentifiableRuntimeCollection<MetricCollectionIdentifiableGlobalIdentifier> metricCollectionIdentifiableGlobalIdentifiers;
	@Transient private IdentifiableRuntimeCollection<PartyIdentifiableGlobalIdentifier> partyIdentifiableGlobalIdentifiers;
	
	@Transient @Accessors(chain=true) private IdentifiablePeriod __identifiablePeriod__;
	@Transient private IdentifiableRuntimeCollection<AbstractIdentifiable> identifiables;
	
	@Transient protected Properties joinedIdentifiableRuntimeCollectionMap;
	
	@SuppressWarnings("rawtypes")
	@Transient protected InstanceHelper.ActionListener actionListener;
	
	public AbstractIdentifiable addCascadeOperationToChildrenFieldNames(Collection<String> fieldNames) {
		cascadeOperationToChildrenFieldNames = CollectionHelper.getInstance().add(Set.class, cascadeOperationToChildrenFieldNames, Boolean.TRUE, fieldNames);
		return this;
	}
	
	public AbstractIdentifiable addCascadeOperationToChildrenFieldNames(String...fieldNames) {
		cascadeOperationToChildrenFieldNames = CollectionHelper.getInstance().add(Set.class, cascadeOperationToChildrenFieldNames, Boolean.TRUE, fieldNames);
		return this;
	}
	
	public AbstractIdentifiable addCascadeOperationToMasterFieldNames(Collection<String> fieldNames) {
		cascadeOperationToMasterFieldNames = CollectionHelper.getInstance().add(Set.class, cascadeOperationToMasterFieldNames, Boolean.TRUE, fieldNames);
		return this;
	}
	
	public AbstractIdentifiable addCascadeOperationToMasterFieldNames(String...fieldNames) {
		cascadeOperationToMasterFieldNames = CollectionHelper.getInstance().add(Set.class, cascadeOperationToMasterFieldNames, Boolean.TRUE, fieldNames);
		return this;
	}
	
	public Boolean isCascadeOperationToMaster(){
		return getCascadeOperationToMaster() == null ?
				CollectionHelper.getInstance().isNotEmpty(getCascadeOperationToMasterFieldNames()) : getCascadeOperationToMaster();
	}
	
	public AbstractIdentifiable addIdentifiables(Collection<AbstractIdentifiable> identifiables){
		if(CollectionHelper.getInstance().isNotEmpty(identifiables)){
			if(this.identifiables == null)
				this.identifiables = new IdentifiableRuntimeCollection<>();
			this.identifiables.addMany(identifiables);
		}
		return this;
	}
	
	public AbstractIdentifiable addIdentifiables(AbstractIdentifiable...identifiables){
		if(ArrayHelper.getInstance().isNotEmpty(identifiables))
			addIdentifiables(Arrays.asList(identifiables));
		return this;
	}
	
	public AbstractIdentifiable addIdentifiablesPartyIdentifiableGlobalIdentifierFromField(String fieldName,String businessRoleCode){
		Object value = FieldHelper.getInstance().read(this, fieldName);
		if(value!=null){
			addIdentifiables(instanciateOne(PartyIdentifiableGlobalIdentifier.class).setPartyAndBusinessRoleFromCode(this, fieldName, businessRoleCode));	
		}
		return this;
	}
	
	public Collection<AbstractIdentifiable> getIdentifiablesElements(){
		return this.identifiables == null ? null : this.identifiables.getElements();
	}
	
	@SuppressWarnings("unchecked")
	public <T> IdentifiableRuntimeCollection<T> getJoinedIdentifiableRuntimeCollection(Class<T> aClass){
		if(joinedIdentifiableRuntimeCollectionMap == null)
			return null;
		return (IdentifiableRuntimeCollection<T>) joinedIdentifiableRuntimeCollectionMap.get(aClass);
	}
	
	public <T> T getJoinedIdentifiableOne(Class<T> aClass){
		IdentifiableRuntimeCollection<T> collection = getJoinedIdentifiableRuntimeCollection(aClass);
		return collection == null ? null : CollectionHelper.getInstance().getFirst(collection.getElements());
	}
	
	public <T> Collection<T> getJoinedIdentifiableMany(Class<T> aClass){
		IdentifiableRuntimeCollection<T> collection = getJoinedIdentifiableRuntimeCollection(aClass);
		return collection == null ? null : collection.getElements();
	}
	
	public Collection<AbstractIdentifiable> getJoinedIdentifiables(){
		Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
		if(joinedIdentifiableRuntimeCollectionMap!=null && joinedIdentifiableRuntimeCollectionMap.__getMap__()!=null)
			for(Map.Entry<?, ?> entry : joinedIdentifiableRuntimeCollectionMap.__getMap__().entrySet()){
				@SuppressWarnings("unchecked")
				IdentifiableRuntimeCollection<AbstractIdentifiable> identifiableRuntimeCollection = (IdentifiableRuntimeCollection<AbstractIdentifiable>) entry.getValue();
				identifiables.addAll(identifiableRuntimeCollection.getElements());
			}
		return identifiables;
	}
	
	public <T> AbstractIdentifiable __setJoinedIdentifiables__(Class<T> aClass,Collection<T> identifiables){
		if(CollectionHelper.getInstance().isNotEmpty(identifiables)){
			IdentifiableRuntimeCollection<T> collection = new IdentifiableRuntimeCollection<T>();
			collection.addMany(identifiables);
			if(joinedIdentifiableRuntimeCollectionMap == null)
				joinedIdentifiableRuntimeCollectionMap = new Properties();
			joinedIdentifiableRuntimeCollectionMap.set(aClass, collection);
		}
		return this;
	}
	
	public <T> AbstractIdentifiable __setJoinedIdentifiables__(Class<T> aClass,@SuppressWarnings("unchecked") T...identifiables){
		if(ArrayHelper.getInstance().isNotEmpty(identifiables)){
			__setJoinedIdentifiables__(aClass, Arrays.asList(identifiables));
		}
		return this;
	}
	
	public <T> AbstractIdentifiable __setJoinedIdentifiablesFromCodes__(Class<T> aClass,Collection<String> codes){
		if(CollectionHelper.getInstance().isNotEmpty(codes)){
			Collection<T> identifiables = new ArrayList<T>();
			for(String index : codes)
				identifiables.add(InstanceHelper.getInstance().getByIdentifier(aClass, index, ClassHelper.Listener.IdentifierType.BUSINESS));
			__setJoinedIdentifiables__(aClass,identifiables);
		}
		return this;
	}
	
	public <T> AbstractIdentifiable __setJoinedIdentifiablesFromCodes__(Class<T> aClass,String...codes){
		if(ArrayHelper.getInstance().isNotEmpty(codes)){
			__setJoinedIdentifiablesFromCodes__(aClass, Arrays.asList(codes));
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> void __setJoinedIdentifiable__(T identifiable){
		if(identifiable!=null){
			__setJoinedIdentifiables__((Class<T>)identifiable.getClass(), Arrays.asList(identifiable));
		}
	}
	
	public IdentifiableRuntimeCollection<MetricCollectionIdentifiableGlobalIdentifier> getMetricCollectionIdentifiableGlobalIdentifiers(){
		if(metricCollectionIdentifiableGlobalIdentifiers == null)
			metricCollectionIdentifiableGlobalIdentifiers = new IdentifiableRuntimeCollection<>();
		return metricCollectionIdentifiableGlobalIdentifiers;
	}
	
	public AbstractIdentifiable set__identifiablePeriod__fromCode(String code){
		this.__identifiablePeriod__ = getFromCode(IdentifiablePeriod.class, code);
		return this;
	}
	
	/**/
	
	public AbstractIdentifiable __set__(String fieldName,Object value){
		FieldHelper.getInstance().set(getGlobalIdentifierCreateIfNull(), value,fieldName);
		return this;
	}
	public Object __get__(String fieldName){
		return globalIdentifier == null ? null : FieldHelper.getInstance().read(globalIdentifier, fieldName);
	}
	
	/**/
	
	public AbstractIdentifiable setText(String text){
		__set__(GlobalIdentifier.FIELD_TEXT, text);
		return this;
	}
	public String getText(){
		return (String) __get__(GlobalIdentifier.FIELD_TEXT);
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
	
	public AbstractIdentifiable setImage(File image){
		getGlobalIdentifierCreateIfNull().setImage(image);
		return this;
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
	
	public AbstractIdentifiable setBirthDate(Date date){
		getGlobalIdentifierCreateIfNull().getExistencePeriod().setFromDate(date);
		return this;
	}
	public Date getBirthDate(){
		return globalIdentifier == null ? null : globalIdentifier.getExistencePeriod().getFromDate();
	}
	public AbstractIdentifiable setBirthDateFromString(String date) {
		return setBirthDate(TimeHelper.getInstance().getDateFromString(date));
	}
	
	public AbstractIdentifiable setDeathDate(Date date){
		getGlobalIdentifierCreateIfNull().getExistencePeriod().setToDate(date);
		return this;
	}
	public Date getDeathDate(){
		return globalIdentifier == null ? null : globalIdentifier.getExistencePeriod().getToDate();
	}
	public AbstractIdentifiable setDeathDateFromString(String date) {
		return setDeathDate(TimeHelper.getInstance().getDateFromString(date));
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
	
	public void setCreationOrderNumber(Long orderNumber){
		getGlobalIdentifierCreateIfNull().setCreationOrderNumber(orderNumber);
	}
	public Long getCreationOrderNumber(){
		return globalIdentifier == null ? null : globalIdentifier.getCreationOrderNumber();
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
	
	public void setExpirable(Boolean expirable){
		getGlobalIdentifierCreateIfNull().setExpirable(expirable);
	}
	public Boolean getExpirable(){
		return globalIdentifier == null ? null : globalIdentifier.getExpirable();
	}
	
	public void setExpired(Boolean expired){
		getGlobalIdentifierCreateIfNull().setExpired(expired);
	}
	public Boolean getExpired(){
		return globalIdentifier == null ? null : globalIdentifier.getExpired();
	}
	
	public AbstractIdentifiable setClosed(Boolean closed){
		getGlobalIdentifierCreateIfNull().setClosed(closed);
		return this;
	}
	public Boolean getClosed(){
		return globalIdentifier == null ? null : globalIdentifier.getClosed();
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

	@PostLoad
	private void onPostLoad() {
		__listenPostLoad__();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void __listenPostLoad__(){
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
		protected List<GlobalIdentifier> masterIdentifiableGlobalIdentifiers;
		
		public Filter() {
			addCriterias(globalIdentifier);
		}
		
		public Filter(Filter<T> criterias) {
			super(criterias);
		}
		
		@Override
		public Filter<T> addMaster(Object master) {
			return (Filter<T>) super.addMaster(master);
		}
		
		@Override
		public Filter<T> addMaster(Class<?> aClass, Object identifier) {
			return (Filter<T>) super.addMaster(aClass, identifier);
		}
		
		public Filter<T> addMasterIdentifiableGlobalIdentifiers(Collection<GlobalIdentifier> masterIdentifiableGlobalIdentifiers){
			if(CollectionHelper.getInstance().isNotEmpty(masterIdentifiableGlobalIdentifiers)){
				if(this.masterIdentifiableGlobalIdentifiers==null)
					this.masterIdentifiableGlobalIdentifiers = new ArrayList<>();
				for(GlobalIdentifier index : masterIdentifiableGlobalIdentifiers)
					if(index!=null)
						this.masterIdentifiableGlobalIdentifiers.add(index);	
			}
			return this;
		}
		
		public Filter<T> addMasterIdentifiableGlobalIdentifier(GlobalIdentifier...masterIdentifiableGlobalIdentifiers){
			if(ArrayHelper.getInstance().isNotEmpty(masterIdentifiableGlobalIdentifiers))
				addMasterIdentifiableGlobalIdentifiers(Arrays.asList(masterIdentifiableGlobalIdentifiers));
			return this;
		}
		
		public Filter<T> addMasterIdentifiableGlobalIdentifier(Class<? extends AbstractIdentifiable> aClass,Object identifier,ClassHelper.Listener.IdentifierType identifierType){
			return addMasterIdentifiableGlobalIdentifier(InstanceHelper.getInstance().getByIdentifier(aClass, identifier, identifierType).getGlobalIdentifier());
		}
		
		public Filter<T> addMasterIdentifiableGlobalIdentifier(Class<? extends AbstractIdentifiable> aClass,Object identifier){
			return addMasterIdentifiableGlobalIdentifier(aClass, identifier, ClassHelper.Listener.IdentifierType.BUSINESS);
		}
		
		public FilterHelper.Filter<T> setClosed(Collection<Boolean> values) {
			getGlobalIdentifier().getClosed().setValues(values);
			return this;
		}
		
		public FilterHelper.Filter<T> setClosed(Boolean...values) {
			getGlobalIdentifier().getClosed().setValues(values);
			return this;
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
	public static final String FIELD___PARENT__ = "__parent__";
	public static final String FIELD___IDENTIFIABLE___PERIOD = "__identifiablePeriod__";
	public static final String FIELD_IDENTIFIABLES = "identifiables";
	
	//TODO very strange , those followings instructions make strange errors appearing on integration test
	//public static final String __FIELD_GLOBAL_IDENTIFIER_CODE__ = FieldHelper.getInstance().buildPath(FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE);
	//public static final String __FIELD_GLOBAL_IDENTIFIER_NAME__ = FieldHelper.getInstance().buildPath(FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME);
	
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
