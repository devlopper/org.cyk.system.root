package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.FileIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.business.api.file.report.ReportFileBusiness;
import org.cyk.system.root.business.api.file.report.RootReportProducer;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.party.PartyIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.time.IdentifiablePeriodIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.file.report.AbstractRootReportProducer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.system.root.model.file.report.ReportFile;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.system.root.persistence.api.file.FileIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.system.root.persistence.api.file.report.ReportTemplateDao;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;
import org.cyk.system.root.persistence.api.mathematics.MetricValueIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.party.PartyIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.value.ValueCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.converter.Converter;
import org.cyk.utility.common.converter.ManyConverter;
import org.cyk.utility.common.converter.OneConverter;
import org.cyk.utility.common.formatter.DateFormatter;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.CollectionHelper.Instance;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.MapHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.StackTraceHelper;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractTypedBusinessService<IDENTIFIABLE extends AbstractIdentifiable, TYPED_DAO extends TypedDao<IDENTIFIABLE>> extends AbstractIdentifiableBusinessServiceImpl<IDENTIFIABLE> implements
		TypedBusiness<IDENTIFIABLE>, Serializable {

	private static final long serialVersionUID = 6437552355933877400L;

	@Inject protected GenericDao genericDao;
	protected TYPED_DAO dao;

	public AbstractTypedBusinessService(TYPED_DAO dao) {
		super();
		this.dao = dao;
	}
	
	@Override
	public IDENTIFIABLE delete(String code) {
		return delete(dao.read(code));
	}

	@Override
	public Collection<IDENTIFIABLE> delete(Set<String> codes) {
		Collection<IDENTIFIABLE> collection = new ArrayList<>();
		for(String code : codes)
			collection.add(delete(code));
		return collection;
	}

	@Override
	protected PersistenceService<IDENTIFIABLE, Long> getPersistenceService() {
	    return dao;
	}
	
	protected String generateCode(Object...tokens){
		Collection<String> collection = new ArrayList<>();
		for(Object token : tokens)
			if(token==null){
			
			}else
				if(token instanceof AbstractIdentifiable)
					collection.add(((AbstractIdentifiable)token).getCode());
				else
					collection.add(token.toString());	
		return RootConstant.Code.generate(collection.toArray()); //StringUtils.join(collection, Constant.CHARACTER_UNDESCORE);
	}
	
	protected String generateName(Object...tokens){
		Collection<String> collection = new ArrayList<>();
		for(Object token : tokens)
			if(token==null){
			
			}else
				if(token instanceof AbstractIdentifiable)
					collection.add(((AbstractIdentifiable)token).getName());
				else
					collection.add(token.toString());	
		return StringUtils.join(collection, Constant.CHARACTER_SPACE);
	}
	
	protected Date generateExistencePeriodBirthDate(Object...tokens){
		if(tokens == null || tokens.length == 0){
			logWarning("No tokens for existence period date specified");
			return null;
		}else if(tokens.length > 1){
			logWarning("Too much tokens for existence period date specified", StringUtils.join(tokens,Constant.CHARACTER_COMA.toString()));
			return null;
		}
		return (Date)tokens[0];
	}
	
	protected Object[] getPropertyValueTokens(IDENTIFIABLE identifiable,String name){
		if(commonUtils.attributePath(GlobalIdentifier.FIELD_EXISTENCE_PERIOD, Period.FIELD_FROM_DATE).equals(name))
			return new Object[]{universalTimeCoordinated()};
		return null;
	}

	protected void setProperty(IDENTIFIABLE identifiable,String name){
		//beforeGetPropertyValueTokens(getListeners(), identifiable, name);
		Object[] tokens = getPropertyValueTokens(identifiable, name);
		//tokens = afterGetPropertyValueTokens(getListeners(), identifiable, name, tokens);
		if(tokens==null){
			logWarning("tokens , for automatically build value of property {} in object {}, should not be null", name,identifiable);
		}else{
			if(GlobalIdentifier.FIELD_CODE.equals(name) && StringUtils.isBlank(identifiable.getCode()))
				identifiable.setCode(generateCode(tokens));
			else if(GlobalIdentifier.FIELD_NAME.equals(name) && StringUtils.isBlank(identifiable.getName()))
				identifiable.setName(generateName(tokens));
			else if(commonUtils.attributePath(GlobalIdentifier.FIELD_EXISTENCE_PERIOD, Period.FIELD_FROM_DATE).equals(name) && identifiable.getExistencePeriod()!=null 
					&& identifiable.getGlobalIdentifierCreateIfNull().getExistencePeriod().getFromDate()==null)
				identifiable.getGlobalIdentifierCreateIfNull().getExistencePeriod().setFromDate(generateExistencePeriodBirthDate(tokens));
		}
	}
	
	protected void setAutoSettedProperties(final IDENTIFIABLE identifiable, Crud crud){
		if(Crud.isCreateOrUpdate(crud)){
			String property;
			if(isAutoSetPropertyValueClass(property = GlobalIdentifier.FIELD_CODE, identifiable.getClass()))
				setProperty(identifiable,property);
			if(isAutoSetPropertyValueClass(property = GlobalIdentifier.FIELD_NAME, identifiable.getClass()))
				setProperty(identifiable,property);
			if(isAutoSetPropertyValueClass(property = commonUtils.attributePath(GlobalIdentifier.FIELD_EXISTENCE_PERIOD, Period.FIELD_FROM_DATE), identifiable.getClass()))
				setProperty(identifiable,property);
			
			FieldHelper fieldHelper = FieldHelper.getInstance();
			for(String index : new String[]{GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME,fieldHelper.buildPath(GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE)}){
				String fieldName = fieldHelper.buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,index);
				Object value = fieldHelper.read(identifiable, fieldName);
				if(value == null){
					value = InstanceHelper.getInstance().generateFieldValue(identifiable, fieldName, fieldHelper.getType(clazz, fieldName),Boolean.FALSE);
					if(value!=null)
						fieldHelper.set(identifiable,value, fieldName);
				}
			}
			
			
			computeChanges(identifiable);	
		}
		
		if(Crud.CREATE.equals(crud)){
			if(Boolean.TRUE.equals(identifiable.isCascadeOperationToMaster())){
				new CollectionHelper.Iterator.Adapter.Default<String>(identifiable.getCascadeOperationToMasterFieldNames()){
					private static final long serialVersionUID = 1L;
					@Override
					protected void __executeForEach__(String fieldName) {
						java.lang.reflect.Field masterField = FieldHelper.getInstance().get(identifiable.getClass(), fieldName);
						AbstractIdentifiable master = (AbstractIdentifiable) FieldHelper.getInstance().read(identifiable, masterField);
						//master.getGlobalIdentifierCreateIfNull().setIdentifierOfOwner(identifierOfOwner);
						createMaster(identifiable,fieldName,master);
					}
				}.execute();
			}else{
				
			}	
		}		
	}
	
	protected void createMaster(IDENTIFIABLE identifiable,String fieldName,AbstractIdentifiable master){
		createIfNotIdentified(master);
	}
	
	protected <ITEM extends AbstractIdentifiable> void synchronise(Class<ITEM> itemClass,IDENTIFIABLE master,IdentifiableRuntimeCollection<ITEM> collection){
		if(collection.isSynchonizationEnabled()){
			TypedDao<ITEM> dao = inject(PersistenceInterfaceLocator.class).injectTyped(itemClass);
			@SuppressWarnings("unchecked")
			Collection<ITEM> database = (Collection<ITEM>) MethodHelper.getInstance().call(dao,Collection.class,"readBy"+master.getClass().getSimpleName()
					,MethodHelper.Method.Parameter.buildArray(master.getClass(),master));
			synchronise(itemClass, database, collection.getElements());
		}
	}
	
	protected <ITEM extends AbstractIdentifiable> void synchronise(Class<ITEM> itemClass,Collection<ITEM> database,Collection<ITEM> runtime){
		LoggingHelper.getInstance().getLogger().getMessageBuilder(Boolean.TRUE).addManyParameters("synchronise",new Object[]{"class",itemClass.getSimpleName()}
    	,new Object[]{"#runtime",runtime.size()},new Object[]{"#database",database.size()}).getLogger().execute(getClass(),LoggingHelper.Logger.Level.TRACE
    			,LoggingHelper.getInstance().getMarkerName(itemClass.getSimpleName(),"SYNCHRONISE"));
		delete(itemClass,database, runtime);
		inject(BusinessInterfaceLocator.class).injectTyped(itemClass).save(runtime);
	}
	
	protected <ITEM extends AbstractIdentifiable> void create(Class<ITEM> itemClass,IdentifiableRuntimeCollection<ITEM> collection){
		if(collection.isSynchonizationEnabled())
			inject(BusinessInterfaceLocator.class).injectTyped(itemClass).create(collection.getElements());
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<IDENTIFIABLE> instanciateMany(List<String[]> values) {
		Collection<IDENTIFIABLE> collection = new ArrayList<>();
		if(values==null){
			System.out.println("Cannot instanciate "+clazz.getSimpleName()+" from list of null values ");
		}else{
			for(String[] value : values){
				IDENTIFIABLE identifiable = instanciateOne(value);
				if(identifiable==null)
					System.out.println("Instanciation of "+clazz.getSimpleName()+" with values "+StringUtils.join(values,",")+" gives null");
				else
					collection.add(identifiable);
			}
		}
		return collection;
	}
	
	@Override
	public Collection<IDENTIFIABLE> instanciateMany(String[][] strings) {
		List<String[]> argumentList = new ArrayList<>();
		for(String[] array : strings)
			argumentList.add(array);
		return instanciateMany(argumentList);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IDENTIFIABLE instanciateOneRandomly() {
		return instanciateOne();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IDENTIFIABLE instanciateOneRandomly(String code) {
		IDENTIFIABLE identifiable = instanciateOneRandomly();
		identifiable.setCode(code);
		return identifiable;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<IDENTIFIABLE> instanciateManyRandomly(Integer count) {
		Collection<IDENTIFIABLE> collection = new ArrayList<>();
		for(int index = 0; index < count ; index++)
			collection.add(instanciateOneRandomly());
		return collection;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<IDENTIFIABLE> instanciateManyRandomly(Set<String> codes) {
		List<IDENTIFIABLE> list = new ArrayList<>(instanciateManyRandomly(codes.size()));
		List<String> codeList = new ArrayList<>(codes);
		for(int index = 0; index < codes.size() ; index++)
			list.get(index).setCode(codeList.get(index));
		return list;
	}
	
	protected void beforeCrud(final IDENTIFIABLE identifiable,Crud crud){
		new CollectionHelper.Iterator.Adapter.Default<AbstractIdentifiable>(identifiable.getJoinedIdentifiables()){
			private static final long serialVersionUID = 1L;
			@Override
			protected void __executeForEach__(AbstractIdentifiable index) {
				identifiable.addIdentifiables(instanciateJoinGlobalIdentifier(identifiable,index));
			}
		}.execute();
	}
	
	protected AbstractJoinGlobalIdentifier instanciateJoinGlobalIdentifier(IDENTIFIABLE identifiable,AbstractIdentifiable join){
		return ((AbstractJoinGlobalIdentifier) instanciateOne(ClassHelper.getInstance().getByName(identifiable.getClass().getName()+"IdentifiableGlobalIdentifier")))
				.setMaster(identifiable).setIdentifiableGlobalIdentifier(join.getGlobalIdentifier());
	}
	
	protected void afterCrud(IDENTIFIABLE identifiable,Crud crud){
		if(Boolean.TRUE.equals(IdentifiablePeriod.isManaged(identifiable)))
			createIdentifiablePeriod(identifiable, crud);
	}
	
	protected void createIdentifiablePeriod(IDENTIFIABLE identifiable,Crud crud){
		if(Crud.CREATE.equals(crud)){
			if(identifiable.get__identifiablePeriod__()!=null){
				IdentifiablePeriodIdentifiableGlobalIdentifier identifiablePeriodIdentifiableGlobalIdentifier = 
						inject(IdentifiablePeriodIdentifiableGlobalIdentifierBusiness.class).instanciateOne();
				identifiablePeriodIdentifiableGlobalIdentifier.setIdentifiablePeriod(identifiable.get__identifiablePeriod__());
				identifiablePeriodIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(identifiable.getGlobalIdentifier());
				inject(IdentifiablePeriodIdentifiableGlobalIdentifierBusiness.class).create(identifiablePeriodIdentifiableGlobalIdentifier);
			}	
		}
	}
	
	protected void beforeCreate(IDENTIFIABLE identifiable){
		setAutoSettedProperties(identifiable, Crud.CREATE);
	    inject(ValidationPolicy.class).validateCreate(identifiable);
	    createIfNotIdentified(identifiable.getSupportingDocument());
	    beforeCreate(getListeners(), identifiable);
	    beforeCrud(identifiable, Crud.CREATE);
	    inject(GenericBusiness.class).createIfNotIdentified(findRelatedInstances(identifiable));
	    InstanceHelper.getInstance().cascadeAct(Constant.Action.CREATE, identifiable, FieldHelper.Field.Relationship.PARENT);
	}
	
	protected void __create__(final IDENTIFIABLE identifiable){
		dao.create(identifiable);
	}
	
	@Override
	public IDENTIFIABLE create(final IDENTIFIABLE identifiable) {
		if(identifiable==null){
			
		}else{
			new LoggingHelper.Run.Adapter.Default(StackTraceHelper.getInstance().getAt(2),getClass()){
				private static final long serialVersionUID = 1L;
				
				@Override
				public void addParameters(org.cyk.utility.common.helper.LoggingHelper.Message.Builder builder, Boolean before) {
					super.addParameters(builder, before);
					builder.addNamedParameters("entity",identifiable.getClass().getSimpleName(),"code",identifiable.getCode());
					if(identifiable.getGlobalIdentifier()!=null)
						builder.addNamedParameters("gid",identifiable.getGlobalIdentifier().getIdentifier());
					if(Boolean.TRUE.equals(before)){
						
					}else{
						builder.addNamedParameters("identifier",identifiable.getIdentifier());
					}
				}
				
				@Override
				public Object __execute__() {
					identifiable.setLoggingMessageBuilder(null);
					beforeCreate(identifiable);
					if(identifiable.getActionListener()!=null)
						identifiable.getActionListener().actBefore(identifiable, Constant.Action.CREATE);
					
					__create__(identifiable);
					
					if(Boolean.TRUE.equals(isComputeCreationOrderNumber(identifiable))){
						if(identifiable.getGlobalIdentifier()!=null && identifiable.getGlobalIdentifier().getCreationOrderNumber()==null)
							identifiable.getGlobalIdentifier().setCreationOrderNumber(computeCreationOrderNumber(identifiable));	
					}
					if(Boolean.TRUE.equals(isComputeOrderNumber(identifiable))){
						if(identifiable.getOrderNumber() == null)
							identifiable.setOrderNumber(computeOrderNumber(identifiable));
					}
					if(identifiable.getActionListener()!=null)
						identifiable.getActionListener().actAfter(identifiable, Constant.Action.CREATE);
					
			        afterCreate(identifiable);	
			        logTrace(identifiable.getLoggingMessageBuilder());
					return null;
				}
				
			}.execute();
		}
		
        return identifiable;
	}
	
	protected Boolean isComputeCreationOrderNumber(IDENTIFIABLE identifiable){
		return Boolean.FALSE;
	}
	
	protected Long computeCreationOrderNumber(IDENTIFIABLE identifiable){
		return dao.countAll()-1;
	}
	
	protected Boolean isComputeOrderNumber(IDENTIFIABLE identifiable){
		return Boolean.FALSE;
	}
	
	protected Long computeOrderNumber(IDENTIFIABLE identifiable){
		return identifiable.getGlobalIdentifier() == null ? null : identifiable.getGlobalIdentifier().getCreationOrderNumber();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void afterCreate(IDENTIFIABLE identifiable){
		if(identifiable.getMetricCollectionIdentifiableGlobalIdentifiers().isSynchonizationEnabled())
			inject(MetricCollectionIdentifiableGlobalIdentifierBusiness.class).create(identifiable.getMetricCollectionIdentifiableGlobalIdentifiers().getElements());
		
		Collection<String> metricValueMetricCollectionCodes = null;
		/*metricValueMetricCollectionCodes = listenerUtils.getCollection(getListeners(), new ListenerUtils.CollectionMethod<Listener<AbstractIdentifiable>, String>(){

			@Override
			public Collection<String> execute(AbstractIdentifiableBusinessServiceImpl.Listener<AbstractIdentifiable> listener) {
				return listener.getMetricValueMetricCollectionCodes();
			}});*/
		Collection listeners =  getListeners();
		if(listeners!=null)
			for(Object listener : listeners){
				Collection<String> v = ((Listener)listener).getMetricValueMetricCollectionCodes();
				if(v!=null)
					metricValueMetricCollectionCodes = v;
			}
		if(metricValueMetricCollectionCodes!=null){
			Collection<MetricValue> metricValues = new ArrayList<>();
			Collection<MetricCollection> metricCollections = inject(MetricCollectionDao.class).read(metricValueMetricCollectionCodes);
			for(MetricCollection metricCollection : metricCollections){
				for(Metric metric : inject(MetricDao.class).readByCollection(metricCollection)){
					MetricValue metricValue = new MetricValue(metric,new Value());
					metricValues.add(metricValue);
				}
			}
			inject(MetricValueBusiness.class).create(metricValues);
		}
		saveIdentifiables(identifiable,Crud.CREATE);
		afterCreate(getListeners(), identifiable);
		afterCrud(identifiable, Crud.CREATE);
		
		//MapHelper.getInstance().cl
		if(identifiable.getFieldValueComputationTriggers()!=null && identifiable.getFieldValueComputationTriggers().getMap()!=null)
			identifiable.getFieldValueComputationTriggers().getMap().clear();//TODO is it the right way
	}
	
	protected void saveIdentifiables(IDENTIFIABLE identifiable,Crud crud){
		if(identifiable.getIdentifiables()!=null && CollectionHelper.getInstance().isNotEmpty(identifiable.getIdentifiables().getElements())){
			for(AbstractIdentifiable index : identifiable.getIdentifiables().getElements()){
				if(index.getBirthDate() == null && Boolean.TRUE.equals(index.isBirthDateComputedByUser()))
					index.setBirthDate(identifiable.getBirthDate());
				if(index.getDeathDate() == null && Boolean.TRUE.equals(index.isDeathDateComputedByUser()))
					index.setDeathDate(identifiable.getDeathDate());
			}
			if(Crud.CREATE.equals(crud))
				inject(GenericBusiness.class).create(identifiable.getIdentifiables().getElements());
			else if(Crud.UPDATE.equals(crud)) {
				inject(GenericBusiness.class).update(identifiable.getIdentifiables().getElements());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IDENTIFIABLE create(IDENTIFIABLE identifiable, Collection<? extends AbstractIdentifiable> identifiables) {
		create(identifiable);
		inject(GenericBusiness.class).create((Collection<AbstractIdentifiable>)identifiables);
		return identifiable;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IDENTIFIABLE update(IDENTIFIABLE identifiable, Collection<? extends AbstractIdentifiable> identifiables) {
		update(identifiable);
		inject(GenericBusiness.class).update((Collection<AbstractIdentifiable>)identifiables);
		return identifiable;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IDENTIFIABLE delete(IDENTIFIABLE identifiable, Collection<? extends AbstractIdentifiable> identifiables) {
		inject(GenericBusiness.class).delete((Collection<AbstractIdentifiable>)identifiables);
		delete(identifiable);
		return identifiable;
	}

	@Override
	public void create(Collection<IDENTIFIABLE> identifiables) {
	    for(IDENTIFIABLE identifiable : identifiables)
	    	create(identifiable);
	}
	
	protected void beforeUpdate(IDENTIFIABLE identifiable){
		setAutoSettedProperties(identifiable, Crud.UPDATE);
		inject(ValidationPolicy.class).validateUpdate(identifiable);
		beforeUpdate(getListeners(), identifiable);
		beforeCrud(identifiable, Crud.UPDATE);
		inject(GenericBusiness.class).updateIfIdentifiedElseCreate(findRelatedInstances(identifiable));
		InstanceHelper.getInstance().cascadeAct(Constant.Action.UPDATE, identifiable, FieldHelper.Field.Relationship.PARENT);
	}

	@Override
	public IDENTIFIABLE update(final IDENTIFIABLE identifiable) {
		if(identifiable==null){
			
		}else{
			new LoggingHelper.Run.Adapter.Default(StackTraceHelper.getInstance().getAt(2),getClass()){
				private static final long serialVersionUID = 1L;
				
				@Override
				public void addParameters(org.cyk.utility.common.helper.LoggingHelper.Message.Builder builder, Boolean before) {
					super.addParameters(builder, before);
					builder.addNamedParameters("entity",identifiable.getClass().getSimpleName(),"code",identifiable.getCode());
					builder.addNamedParameters("identifier",identifiable.getIdentifier());
					if(identifiable.getGlobalIdentifier()!=null)
						builder.addNamedParameters("gid",identifiable.getGlobalIdentifier().getIdentifier());
				}
				
				@Override
				public Object __execute__() {
					identifiable.setLoggingMessageBuilder(null);
					beforeUpdate(identifiable);
					//IDENTIFIABLE newObject = dao.update(identifiable);
					if(identifiable.getActionListener()!=null)
						identifiable.getActionListener().actBefore(identifiable, Constant.Action.UPDATE);
					dao.update(identifiable);
					if(identifiable.getGlobalIdentifier()!=null)
				    	inject(GlobalIdentifierBusiness.class).update(identifiable.getGlobalIdentifier());
					if(identifiable.getActionListener()!=null)
						identifiable.getActionListener().actAfter(identifiable, Constant.Action.UPDATE);
				    afterUpdate(identifiable);
				   //return newObject; We might lost some informations by returning the new managed object. better to keep the old one
				    logTrace(identifiable.getLoggingMessageBuilder());
				    return identifiable;
				}
				
			}.execute();
			/*
			beforeUpdate(identifiable);
			//IDENTIFIABLE newObject = dao.update(identifiable);
			dao.update(identifiable);
		    if(identifiable.getGlobalIdentifier()!=null)
		    	inject(GlobalIdentifierBusiness.class).update(identifiable.getGlobalIdentifier());
		    afterUpdate(identifiable);
			//return newObject; We might lost some informations by returning the new managed object. better to keep the old one
		    */
		}
		return identifiable;
	}
	
	protected void afterUpdate(IDENTIFIABLE identifiable){
		saveIdentifiables(identifiable,Crud.UPDATE);
		afterUpdate(getListeners(), identifiable);
		afterCrud(identifiable, Crud.UPDATE);
	}
	
	@Override
	public void update(Collection<IDENTIFIABLE> identifiables) {
	    for(IDENTIFIABLE identifiable : identifiables)
	    	update(identifiable);
	}
	
	protected void beforeDelete(IDENTIFIABLE identifiable){
		//setAutoSettedProperties(identifiable, Crud.DELETE);
		inject(ValidationPolicy.class).validateDelete(identifiable);
		deleteFileIdentifiableGlobalIdentifier(identifiable);
		deleteMetricValueIdentifiableGlobalIdentifier(identifiable);
		deleteMetricCollectionIdentifiableGlobalIdentifier(identifiable);
		deleteMovementCollectionIdentifiableGlobalIdentifier(identifiable);
		deleteIdentifiablePeriodIdentifiableGlobalIdentifier(identifiable);
		deletePartyIdentifiableGlobalIdentifier(identifiable);
		beforeDelete(getListeners(), identifiable);
		beforeCrud(identifiable, Crud.DELETE);
		
		deleteRelatedInstances(identifiable);
	}
	
	protected void deleteRelatedInstances(IDENTIFIABLE identifiable){
		inject(GenericBusiness.class).deleteIfIdentified(findRelatedInstances(identifiable,Boolean.TRUE,null));
	}
	
	protected void deleteFileIdentifiableGlobalIdentifier(IDENTIFIABLE identifiable){
		if(identifiable instanceof FileIdentifiableGlobalIdentifier || identifiable instanceof File || identifiable instanceof Location){
			
		}else{
			Collection<FileIdentifiableGlobalIdentifier> fileIdentifiableGlobalIdentifiers = inject(FileIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(identifiable);
			inject(FileIdentifiableGlobalIdentifierBusiness.class).delete(fileIdentifiableGlobalIdentifiers);	
		}
	}
	
	protected void deleteMetricCollectionIdentifiableGlobalIdentifier(IDENTIFIABLE identifiable){
		if(identifiable instanceof MetricCollectionIdentifiableGlobalIdentifier || identifiable instanceof MetricCollection || identifiable instanceof File || identifiable instanceof Location){
			
		}else{
			Collection<MetricCollectionIdentifiableGlobalIdentifier> metricCollectionIdentifiableGlobalIdentifiers = inject(MetricCollectionIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(identifiable);
			inject(MetricCollectionIdentifiableGlobalIdentifierBusiness.class).delete(metricCollectionIdentifiableGlobalIdentifiers);	
		}
	}
	
	protected void deleteMetricValueIdentifiableGlobalIdentifier(IDENTIFIABLE identifiable){
		if(identifiable instanceof MetricValueIdentifiableGlobalIdentifier || identifiable instanceof MetricValue || identifiable instanceof File || identifiable instanceof Location){
			
		}else{
			Collection<MetricValueIdentifiableGlobalIdentifier> metricValueIdentifiableGlobalIdentifiers = inject(MetricValueIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(identifiable);
			inject(MetricValueIdentifiableGlobalIdentifierBusiness.class).delete(metricValueIdentifiableGlobalIdentifiers);	
		}
	}
	
	protected void deleteMovementCollectionIdentifiableGlobalIdentifier(IDENTIFIABLE identifiable){
		if(identifiable instanceof MovementCollectionIdentifiableGlobalIdentifier || identifiable instanceof MovementCollection || identifiable instanceof File || identifiable instanceof Location){
			
		}else{
			Collection<MovementCollectionIdentifiableGlobalIdentifier> movementCollectionIdentifiableGlobalIdentifiers = inject(MovementCollectionIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(identifiable);
			inject(MovementCollectionIdentifiableGlobalIdentifierBusiness.class).delete(movementCollectionIdentifiableGlobalIdentifiers);	
		}
	}
	
	protected void deleteIdentifiablePeriodIdentifiableGlobalIdentifier(IDENTIFIABLE identifiable){
		if(identifiable instanceof IdentifiablePeriodIdentifiableGlobalIdentifier || identifiable instanceof IdentifiablePeriod || identifiable instanceof File || identifiable instanceof Location){
			
		}else{
			Collection<IdentifiablePeriodIdentifiableGlobalIdentifier> movementCollectionIdentifiableGlobalIdentifiers = inject(IdentifiablePeriodIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(identifiable);
			inject(IdentifiablePeriodIdentifiableGlobalIdentifierBusiness.class).delete(movementCollectionIdentifiableGlobalIdentifiers);	
		}
	}
	
	protected void deletePartyIdentifiableGlobalIdentifier(IDENTIFIABLE identifiable){
		if(identifiable instanceof PartyIdentifiableGlobalIdentifier /*|| identifiable instanceof Party*/ || identifiable instanceof File || identifiable instanceof Location){
			
		}else{
			Collection<PartyIdentifiableGlobalIdentifier> partyIdentifiableGlobalIdentifiers = inject(PartyIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(identifiable);
			if(identifiable instanceof Party)
				partyIdentifiableGlobalIdentifiers = CollectionHelper.getInstance().add(partyIdentifiableGlobalIdentifiers, Boolean.TRUE, inject(PartyIdentifiableGlobalIdentifierDao.class).readByParty((Party) identifiable));
			inject(PartyIdentifiableGlobalIdentifierBusiness.class).delete(partyIdentifiableGlobalIdentifiers);	
		}
	}
	
	protected void afterDelete(IDENTIFIABLE identifiable){
		afterDelete(getListeners(), identifiable);
		afterCrud(identifiable, Crud.DELETE);
		InstanceHelper.getInstance().cascadeAct(Constant.Action.DELETE, identifiable, FieldHelper.Field.Relationship.PARENT);
	}
	
	protected void __delete__(final IDENTIFIABLE identifiable){
		dao.delete(identifiable);
	}

	@Override
	public IDENTIFIABLE delete(final IDENTIFIABLE identifiable) {
		if(identifiable==null){
			
		}else{
			new LoggingHelper.Run.Adapter.Default(StackTraceHelper.getInstance().getAt(2),getClass()){
				private static final long serialVersionUID = 1L;
				
				@Override
				public void addParameters(org.cyk.utility.common.helper.LoggingHelper.Message.Builder builder, Boolean before) {
					super.addParameters(builder, before);
					builder.addNamedParameters("entity",identifiable.getClass().getSimpleName(),"code",identifiable.getCode());
					builder.addNamedParameters("identifier",identifiable.getIdentifier());
					if(identifiable.getGlobalIdentifier()!=null)
						builder.addNamedParameters("gid",identifiable.getGlobalIdentifier().getIdentifier());
				}
				
				@Override
				public Object __execute__() {
					identifiable.setLoggingMessageBuilder(null);
					if(Boolean.TRUE.equals(identifiable.getCheckIfExistOnDelete()) ? getPersistenceService().read(identifiable.getIdentifier())!=null : Boolean.TRUE){
						beforeDelete(identifiable);
						if(identifiable.getActionListener()!=null)
							identifiable.getActionListener().actBefore(identifiable, Constant.Action.DELETE);
						if(identifiable.getGlobalIdentifier()!=null){
							inject(GlobalIdentifierBusiness.class).delete(identifiable.getGlobalIdentifier());
							identifiable.setGlobalIdentifier(null);
						}		
						__delete__(identifiable);//TODO i think we should delete identifiable first then global identifier second
						if(identifiable.getActionListener()!=null)
							identifiable.getActionListener().actAfter(identifiable, Constant.Action.DELETE);
						afterDelete(identifiable);	
						logTrace(identifiable.getLoggingMessageBuilder());
					}
					return null;
				}
				
			}.execute();
			/*
			if(Boolean.TRUE.equals(identifiable.getCheckIfExistOnDelete()) ? getPersistenceService().read(identifiable.getIdentifier())!=null : Boolean.TRUE){
				beforeDelete(identifiable);
				if(identifiable.getGlobalIdentifier()!=null){
					inject(GlobalIdentifierBusiness.class).delete(identifiable.getGlobalIdentifier());
					identifiable.setGlobalIdentifier(null);
				}		
				dao.delete(identifiable);
				afterDelete(identifiable);		
			}
			*/
		}
		return identifiable;
	}
	
	@Override
	public void delete(Collection<IDENTIFIABLE> identifiables) {
	    for(IDENTIFIABLE identifiable : identifiables)
	    	delete(identifiable);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findAll(DataReadConfiguration dataReadConfig) {
		if(dataReadConfig==null)
			dao.getDataReadConfig().clear();
		else
			dao.getDataReadConfig().set(dataReadConfig);
		return dao.readAll();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findAll() {
		return findAll(null);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countAll() {
		return dao.countAll();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IDENTIFIABLE findDefaulted() {
		return dao.readDefaulted();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findAllExclude(Collection<IDENTIFIABLE> identifiables) {
		//FIXME find how to handle pagination
		//applyDataReadConfigToDao(getDataReadConfig());
		return dao.readAllExclude(identifiables);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countAllExclude(Collection<IDENTIFIABLE> identifiables) {
		return dao.countAllExclude(identifiables); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findByGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers) {
		return dao.readByGlobalIdentifiers(globalIdentifiers);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers) {
		return dao.countByGlobalIdentifiers(globalIdentifiers); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IDENTIFIABLE findByGlobalIdentifierValue(String globalIdentifier) {
		return dao.readByGlobalIdentifierValue(globalIdentifier);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findByGlobalIdentifierOrderNumber(Long orderNumber) {
		return dao.readByGlobalIdentifierOrderNumber(orderNumber);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IDENTIFIABLE find(String globalIdentifierCode) {
		return dao.read(globalIdentifierCode);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> find(Collection<String> globalIdentifierCodes) {
		return dao.read(globalIdentifierCodes);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findByClasses(Collection<Class<?>> classes) {
		return dao.readByClasses(classes);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByClasses(Collection<Class<?>> classes) {
		return dao.countByClasses(classes);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findByNotClasses(Collection<Class<?>> classes) {
		return dao.readByNotClasses(classes);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByNotClasses(Collection<Class<?>> classes) {
		return dao.countByNotClasses(classes);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public <T extends IDENTIFIABLE> Collection<T> findByClass(Class<T> aClass) {
		return dao.readByClass(aClass);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByClass(Class<?> aClass) {
		return dao.countByClass(aClass);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findByNotClass(Class<?> aClass) {
		return dao.readByNotClass(aClass);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByNotClass(Class<?> aClass) {
		return dao.countByNotClass(aClass);
	}
	
	protected Class<? extends AbstractFieldValueSearchCriteriaSet> getSearchCriteriaClass() {
		return AbstractIdentifiableSearchCriteriaSet.get(clazz);
	}
	
	protected AbstractFieldValueSearchCriteriaSet createSearchCriteriaInstance() {
		Class<? extends AbstractFieldValueSearchCriteriaSet> searchCriteriaClass = getSearchCriteriaClass();
		if(searchCriteriaClass==null || AbstractFieldValueSearchCriteriaSet.class.equals(searchCriteriaClass))
			return null;
		AbstractFieldValueSearchCriteriaSet searchCriteria = newInstance(searchCriteriaClass);
		return searchCriteria;
	}
	
	@Override
	public Collection<IDENTIFIABLE> findByString(StringSearchCriteria stringSearchCriteria,DataReadConfiguration dataReadConfiguration) {
		AbstractFieldValueSearchCriteriaSet searchCriteria = createSearchCriteriaInstance();
		if(searchCriteria==null){
			GlobalIdentifier.SearchCriteria globalSearchCriteria = new GlobalIdentifier.SearchCriteria();
			globalSearchCriteria.set(stringSearchCriteria);
			globalSearchCriteria.setReadConfig(dataReadConfiguration);
			return findByGlobalIdentifierSearchCriteria(globalSearchCriteria);
		}
		searchCriteria.set(stringSearchCriteria);
		searchCriteria.setReadConfig(dataReadConfiguration);
		return findBySearchCriteria(searchCriteria);
	}
	
	@Override
	public Long countByString(StringSearchCriteria stringSearchCriteria) {
		AbstractFieldValueSearchCriteriaSet searchCriteria = createSearchCriteriaInstance();
		if(searchCriteria==null){
			GlobalIdentifier.SearchCriteria globalSearchCriteria = new GlobalIdentifier.SearchCriteria();
			globalSearchCriteria.set(stringSearchCriteria);
			return countByGlobalIdentifierSearchCriteria(globalSearchCriteria);
		}
		searchCriteria.set(stringSearchCriteria);
		return countBySearchCriteria(searchCriteria);
	}
	
	@Deprecated
	protected void applyDataReadConfigToDao(DataReadConfiguration dataReadConfig){
		dao.getDataReadConfig().setFirstResultIndex(dataReadConfig.getFirstResultIndex());
		dao.getDataReadConfig().setMaximumResultCount(dataReadConfig.getMaximumResultCount());
	}
	
	protected void setDaoDataReadConfiguration(DataReadConfiguration dataReadConfiguration){
		if(dataReadConfiguration==null)
			dao.getDataReadConfig().clear();
		else
			dao.getDataReadConfig().set(dataReadConfiguration);
	}
	
	/**
	 * Remove what is in database but not in user
	 * @param databaseIdentifiables
	 * @param userIdentifiables
	 * @return
	 */
	protected <T extends AbstractIdentifiable> Collection<T> delete(Class<T> aClass,Collection<T> databaseIdentifiables,Collection<T> userIdentifiables) {
		LoggingHelper.Message.Builder loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
		loggingMessageBuilder.addManyParameters("delete");
		loggingMessageBuilder.addNamedParameters("#db",CollectionHelper.getInstance().getSize(databaseIdentifiables),"#user",CollectionHelper.getInstance().getSize(userIdentifiables));
		Set<T> deleted = new HashSet<>();
		for(T database : databaseIdentifiables){
			Boolean found = Boolean.FALSE;
			for(T user : userIdentifiables){
				if(database.getIdentifier().equals(user.getIdentifier())){
					found = Boolean.TRUE;
					break;
				}
			}
			if(Boolean.FALSE.equals(found))
				deleted.add(database);
		}
		loggingMessageBuilder.addNamedParameters("#found",deleted.size());
		inject(BusinessInterfaceLocator.class).injectTyped(aClass).delete(deleted);
		logTrace(loggingMessageBuilder);
		return deleted;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public File createReportFile(CreateReportFileArguments<IDENTIFIABLE> arguments) {
		if(arguments.getFile()==null)
			arguments.setFile(new File());
		if(arguments.getFile().getRepresentationType()==null)
			arguments.getFile().setRepresentationType(inject(FileRepresentationTypeDao.class).read(arguments.getReportTemplate().getCode()));
		if(arguments.getJoinFileToIdentifiable()==null)
			arguments.setJoinFileToIdentifiable(Boolean.TRUE);
		exceptionUtils().exception(arguments.getFile().getRepresentationType()==null,"filerepresensationtype.mustnotbenull");	
		RootReportProducer reportProducer = arguments.getReportProducer() == null ? AbstractRootReportProducer.DEFAULT : arguments.getReportProducer();
		@SuppressWarnings({ "rawtypes" })
		Class<AbstractReportTemplateFile> reportTemplateFileClass = (Class<AbstractReportTemplateFile>) reportProducer.getReportTemplateFileClass(arguments.getIdentifiable(),arguments.getReportTemplate().getCode());
		createReportFile(reportTemplateFileClass,arguments);
		return arguments.getFile();
	}
	
	@Override
	public File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode, Locale locale,Map<String, Boolean> fieldSortingMap) {
		CreateReportFileArguments<IDENTIFIABLE> createSaleReportFileArguments = new CreateReportFileArguments<IDENTIFIABLE>(identifiable);
		if(fieldSortingMap!=null)
			createSaleReportFileArguments.getFieldSortingMap().putAll(fieldSortingMap);
    	createSaleReportFileArguments.setLocale(locale);
    	createSaleReportFileArguments.setReportTemplate(inject(ReportTemplateDao.class).read(reportTemplateCode));	
    	Collection<FileIdentifiableGlobalIdentifier> fileIdentifiableGlobalIdentifiers =  inject(FileIdentifiableGlobalIdentifierDao.class)
    			.readByIdentifiableGlobalIdentifier(identifiable);
    	if(fileIdentifiableGlobalIdentifiers.size()==1)
    		createSaleReportFileArguments.setFile(fileIdentifiableGlobalIdentifiers.iterator().next().getFile());
    	return createReportFile(createSaleReportFileArguments);
	}
	
	@Override
	public File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode, Locale locale) {
		return createReportFile(identifiable,reportTemplateCode,locale,null);
	}
	
	@Override
	public File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode,Map<String, Boolean> fieldSortingMap) {
		return createReportFile(identifiable, reportTemplateCode, RootConstant.Configuration.ReportTemplate.LOCALE,fieldSortingMap);
	}
	
	@Override
	public File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode) {
		return createReportFile(identifiable, reportTemplateCode, RootConstant.Configuration.ReportTemplate.LOCALE,null);
	}
	
	@Override
	public Collection<File> createReportFiles(Collection<IDENTIFIABLE> identifiables, String reportTemplateCode,Map<String, Boolean> fieldSortingMap) {
		Collection<File> files = new ArrayList<>();
		for(IDENTIFIABLE identifiable : identifiables)
			files.add(createReportFile(identifiable, reportTemplateCode,fieldSortingMap));
		return files;
	}
	
	@Override
	public Collection<File> createReportFiles(Collection<IDENTIFIABLE> identifiables, String reportTemplateCode) {
		return createReportFiles(identifiables, reportTemplateCode,null);
	}

	protected <REPORT extends AbstractReportTemplateFile<REPORT>> void createReportFile(Class<REPORT> reportClass,CreateReportFileArguments<IDENTIFIABLE> arguments){
		RootReportProducer reportProducer = arguments.getReportProducer() == null ? AbstractRootReportProducer.DEFAULT : arguments.getReportProducer();
		REPORT producedReport = reportProducer.produce(reportClass,arguments);
		exceptionUtils().exception(producedReport==null,"produced report cannot be null");
		exceptionUtils().exception(arguments.getReportTemplate()==null,"report template cannot be null");
		exceptionUtils().exception(arguments.getReportTemplate().getTemplate()==null,"template file cannot be null");
		exceptionUtils().exception(arguments.getFile()==null,"output file cannot be null");
		
		if(arguments.getReportTemplate()!=null){
			for(ValueCollectionIdentifiableGlobalIdentifier valueCollectionIdentifiableGlobalIdentifier : inject(ValueCollectionIdentifiableGlobalIdentifierDao.class)
					.readByIdentifiableGlobalIdentifier(arguments.getReportTemplate()))
				arguments.getReportTemplateValueCollections().add(valueCollectionIdentifiableGlobalIdentifier.getValueCollection());
		}
		DateFormatter.String dateFormatter = new DateFormatter.String.Adapter.Default(arguments.getCreationDate() == null ? new Date() : arguments.getCreationDate(),null);
		dateFormatter.setPart(Constant.Date.Part.DATE_AND_TIME).setLength(Constant.Date.Length.LONG).setLocale(arguments.getLocale() == null ?
				inject(LanguageBusiness.class).findCurrentLocale() : arguments.getLocale());
		producedReport.setCreationDate(dateFormatter.execute());
		if(arguments.getCreatedBy()!=null)
			producedReport.setCreatedBy(arguments.getCreatedBy().getNames());
		/* Images */
		//File headerImage = arguments.get
		if(arguments.getReportTemplate().getHeaderImage()!=null)
			producedReport.setHeaderImage(inject(FileBusiness.class).findInputStream(arguments.getReportTemplate().getHeaderImage()));
		File backgroundImage = arguments.getBackgroundImageFile();
		if(backgroundImage==null)
			backgroundImage = arguments.getReportTemplate().getBackgroundImage();
		if(backgroundImage!=null)
			producedReport.setBackgroundImage(inject(FileBusiness.class).findInputStream(backgroundImage));
		
		ReportBasedOnTemplateFile<REPORT> reportBasedOnTemplateFile = inject(ReportBusiness.class).buildBinaryContent(producedReport, arguments.getReportTemplate().getTemplate()
				, arguments.getFile().getExtension());
		inject(FileBusiness.class).process(arguments.getFile(),reportBasedOnTemplateFile.getBytes(), ReportBusiness.DEFAULT_FILE_NAME_AND_EXTENSION);
		Boolean isNewFile = inject(FileBusiness.class).isNotIdentified(arguments.getFile());
		StringBuilder fileNameBuilder = new StringBuilder(/*arguments.getFile().getRepresentationType().getName()*/);
		//fileNameBuilder.append(Constant.CHARACTER_SPACE);
		fileNameBuilder.append(StringUtils.defaultIfBlank(arguments.getIdentifiableName(), inject(FormatterBusiness.class).format(arguments.getIdentifiable())));
		arguments.getFile().setName(fileNameBuilder.toString());	
		inject(GenericBusiness.class).save(arguments.getFile());
		if(Boolean.TRUE.equals(isNewFile)){
			inject(ReportFileBusiness.class).create(new ReportFile(arguments.getReportTemplate(), arguments.getFile()));
		}
		if(Boolean.TRUE.equals(arguments.getJoinFileToIdentifiable())){
			FileIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
			searchCriteria.addIdentifiableGlobalIdentifier(arguments.getIdentifiable());
			searchCriteria.addRepresentationType(arguments.getFile().getRepresentationType() == null 
					? inject(FileRepresentationTypeDao.class).read(arguments.getReportTemplate().getCode()) : arguments.getFile().getRepresentationType());
			Collection<FileIdentifiableGlobalIdentifier> fileIdentifiableGlobalIdentifiers = inject(FileIdentifiableGlobalIdentifierDao.class).readByCriteria(searchCriteria);
			if(fileIdentifiableGlobalIdentifiers.isEmpty())
				inject(GenericBusiness.class).create(new FileIdentifiableGlobalIdentifier(arguments.getFile(), arguments.getIdentifiable()));
		}
	}
	
	@Override
	public File findReportFile(IDENTIFIABLE identifiable,ReportTemplate reportTemplate,Boolean createIfNull) {
		FileIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
    	searchCriteria.addIdentifiableGlobalIdentifier(identifiable);
    	searchCriteria.addRepresentationType(inject(FileRepresentationTypeDao.class).read(reportTemplate.getCode()));
    	Collection<FileIdentifiableGlobalIdentifier> fileIdentifiableGlobalIdentifiers = inject(FileIdentifiableGlobalIdentifierBusiness.class).findByCriteria(searchCriteria);
		if(fileIdentifiableGlobalIdentifiers.isEmpty() && Boolean.TRUE.equals(createIfNull)){
			CreateReportFileArguments<IDENTIFIABLE> arguments = new CreateReportFileArguments.Builder<IDENTIFIABLE>(identifiable).setReportTemplate(reportTemplate).build();
			return createReportFile(arguments);
		}
		return fileIdentifiableGlobalIdentifiers.isEmpty() ? null : fileIdentifiableGlobalIdentifiers.iterator().next().getFile();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void prepare(IDENTIFIABLE identifiable,Crud crud,String[] childrenFieldNames){
		if(ArrayHelper.getInstance().isNotEmpty(childrenFieldNames))
			for(String childrenFieldName : childrenFieldNames){
				CollectionHelper.Instance<?> collection = (Instance<?>) FieldHelper.getInstance().read(identifiable, childrenFieldName);
				collection.setSynchonizationEnabled(Boolean.TRUE);
				if(Crud.CREATE.equals(crud)){
					
				}else{
					@SuppressWarnings("unchecked")
					Object business = inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)collection.getElementObjectClass());
					Collection<?> children = MethodHelper.getInstance().call(business, Collection.class, getFindByMasterMethodName(identifiable)
							,MethodHelper.Method.Parameter.buildArray(clazz,identifiable));//inject(ContactBusiness.class).findByCollection(identifiable)
					collection.setMany(children);	
				}
			}
	}
	
	protected String getFindByMasterMethodName(Object master){
		return "findBy"+clazz.getSimpleName();
	}
	
	@Override
	public File findReportFile(IDENTIFIABLE identifiable,String reportTemplateCode,Boolean createIfNull) {
		return findReportFile(identifiable, inject(ReportTemplateDao.class).read(reportTemplateCode), createIfNull);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findDuplicates(IDENTIFIABLE identifiable) {
		return dao.readDuplicates(identifiable);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findDuplicates() {
		return dao.readDuplicates();
	}

	@Override
	public IDENTIFIABLE instanciateOne(String[] values) {
		InstanciateOneListener<IDENTIFIABLE> listener = new InstanciateOneListener.Adapter.Default<IDENTIFIABLE>(instanciateOne(),values,0,null);
		IDENTIFIABLE identifiable = __instanciateOne__(values,listener);
		return identifiable;
	}

	@Deprecated
	protected IDENTIFIABLE __instanciateOne__(String[] values,InstanciateOneListener<IDENTIFIABLE> listener) {
		return null;
	}
	
	@Override
	public <T> T convert(Converter<IDENTIFIABLE, T> converter) {
		T result = converter.execute();
		return result;
	}
	
	@Override
	public <T> T convert(OneConverter<IDENTIFIABLE, T> converter) {
		T result = converter.execute();
		return result;
	}
	
	@Override
	public <T> T convert(ManyConverter<IDENTIFIABLE, T> converter) {
		T result = converter.execute();
		return result;
	}
	
	/**/
	
	public void throwIfBelongsTo(AbstractIdentifiable identifiable,Date fromDate,Date toDate){
		throw__(ConditionHelper.Condition.getBuildersDoesNotBelongsTo(identifiable, fromDate, toDate
				, Movement.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE));
	}
	
	/**/
	
	@Getter @Setter
	public static class Details<T extends AbstractIdentifiable> extends AbstractOutputDetails<T> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		
		public Details(T identifiable) {
			super(identifiable);
		}
		
	}
	
	
}
