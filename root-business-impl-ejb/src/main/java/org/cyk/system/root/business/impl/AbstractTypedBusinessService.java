package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.FileIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.business.api.file.report.ReportFileBusiness;
import org.cyk.system.root.business.api.file.report.RootReportProducer;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.file.report.AbstractRootReportProducer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.system.root.model.file.report.ReportFile;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValue;
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
import org.cyk.system.root.persistence.api.mathematics.MetricDao;
import org.cyk.system.root.persistence.api.value.ValueCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.converter.Converter;
import org.cyk.utility.common.converter.ManyConverter;
import org.cyk.utility.common.converter.OneConverter;

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
			else if(commonUtils.attributePath(GlobalIdentifier.FIELD_EXISTENCE_PERIOD, Period.FIELD_FROM_DATE).equals(name) && (identifiable.getExistencePeriod()!=null 
					|| identifiable.getGlobalIdentifierCreateIfNull().getExistencePeriod().getFromDate()==null))
				identifiable.getGlobalIdentifierCreateIfNull().getExistencePeriod().setFromDate(generateExistencePeriodBirthDate(tokens));
		}
	}
	
	protected void setAutoSettedProperties(IDENTIFIABLE identifiable){
		String property;
		if(isAutoSetPropertyValueClass(property = GlobalIdentifier.FIELD_CODE, identifiable.getClass()))
			setProperty(identifiable,property);
		if(isAutoSetPropertyValueClass(property = GlobalIdentifier.FIELD_NAME, identifiable.getClass()))
			setProperty(identifiable,property);
		if(isAutoSetPropertyValueClass(property = commonUtils.attributePath(GlobalIdentifier.FIELD_EXISTENCE_PERIOD, Period.FIELD_FROM_DATE), identifiable.getClass()))
			setProperty(identifiable,property);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<IDENTIFIABLE> instanciateMany(List<String[]> values) {
		Collection<IDENTIFIABLE> collection = new ArrayList<>();;
		for(String[] value : values){
			IDENTIFIABLE identifiable = instanciateOne(value);
			if(identifiable==null)
				System.out.println("Instanciation of "+clazz.getSimpleName()+" with values "+StringUtils.join(values,",")+" gives null");
			else
				collection.add(identifiable);
		}
		return collection;
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
	
	protected void beforeCreate(IDENTIFIABLE identifiable){
		setAutoSettedProperties(identifiable);
	    inject(ValidationPolicy.class).validateCreate(identifiable);
	    beforeCreate(getListeners(), identifiable);
	}
	
	@Override
	public IDENTIFIABLE create(IDENTIFIABLE identifiable) {
		beforeCreate(identifiable);
        identifiable = dao.create(identifiable);
        afterCreate(identifiable);
        return identifiable;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void afterCreate(IDENTIFIABLE identifiable){
		if(identifiable.getMetricCollectionIdentifiableGlobalIdentifiers().isSynchonizationEnabled())
			inject(MetricCollectionIdentifiableGlobalIdentifierBusiness.class).create(identifiable.getMetricCollectionIdentifiableGlobalIdentifiers().getCollection());
		
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
		afterCreate(getListeners(), identifiable);
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
		setAutoSettedProperties(identifiable);
		inject(ValidationPolicy.class).validateUpdate(identifiable);
		beforeUpdate(getListeners(), identifiable);
	}

	@Override
	public IDENTIFIABLE update(IDENTIFIABLE identifiable) {
		beforeUpdate(identifiable);
		IDENTIFIABLE newObject = dao.update(identifiable);
	    if(identifiable.getGlobalIdentifier()!=null)
	    	inject(GlobalIdentifierBusiness.class).update(identifiable.getGlobalIdentifier());
	    afterUpdate(identifiable);
		return newObject;
	}
	
	protected void afterUpdate(IDENTIFIABLE identifiable){
		afterUpdate(getListeners(), identifiable);
	}
	
	@Override
	public void update(Collection<IDENTIFIABLE> identifiables) {
	    for(IDENTIFIABLE identifiable : identifiables)
	    	update(identifiable);
	}
	
	protected void beforeDelete(IDENTIFIABLE identifiable){
		inject(ValidationPolicy.class).validateDelete(identifiable);
		beforeDelete(getListeners(), identifiable);
	}
	
	protected void afterDelete(IDENTIFIABLE identifiable){
		afterDelete(getListeners(), identifiable);
	}

	@Override
	public IDENTIFIABLE delete(IDENTIFIABLE identifiable) {
		beforeDelete(identifiable);
		if(identifiable.getGlobalIdentifier()!=null){
			inject(GlobalIdentifierBusiness.class).delete(identifiable.getGlobalIdentifier());
			identifiable.setGlobalIdentifier(null);
		}		
		/*identifiable = */dao.delete(identifiable);
		afterDelete(identifiable);
		return identifiable;
	}
	
	@Override
	public void delete(Collection<IDENTIFIABLE> identifiables) {
	    for(IDENTIFIABLE identifiable : identifiables)
	    	delete(identifiable);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IDENTIFIABLE load(Long identifier) {
		IDENTIFIABLE identifiable = find(identifier);
		load(identifiable);
		return identifiable;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void load(IDENTIFIABLE identifiable) {
		if(isIdentified(identifiable))
			__load__(identifiable);
	}
	
	protected void __load__(IDENTIFIABLE identifiable) {}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void load(Collection<IDENTIFIABLE> identifiables) {
		for(IDENTIFIABLE identifiable : identifiables)
			load(identifiable);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findAll() {
		return dao.readAll();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countAll() {
		return dao.countAll();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findAll(DataReadConfiguration dataReadConfig) {
		dao.getDataReadConfig().set(dataReadConfig);
		return dao.readAll();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countAll(DataReadConfiguration dataReadConfig) {
		return dao.countAll();
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

	protected void applyDataReadConfigToDao(DataReadConfiguration dataReadConfig){
		dao.getDataReadConfig().setFirstResultIndex(dataReadConfig.getFirstResultIndex());
		dao.getDataReadConfig().setMaximumResultCount(dataReadConfig.getMaximumResultCount());
	}
	
	/**
	 * Remove what is in database but not in user
	 * @param databaseIdentifiables
	 * @param userIdentifiables
	 * @return
	 */
	protected <T extends AbstractIdentifiable> Collection<T> delete(Class<T> aClass/*,TypedDao<T> dao*/,Collection<T> databaseIdentifiables,Collection<T> userIdentifiables) {
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
		
		inject(BusinessInterfaceLocator.class).injectTyped(aClass).delete(deleted);
		/*
		for(T identifiable : deleted)
			dao.delete(identifiable);
		*/
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
		
		ReportBasedOnTemplateFile<REPORT> reportBasedOnTemplateFile = inject(ReportBusiness.class).buildBinaryContent(producedReport, arguments.getReportTemplate().getTemplate()
				, arguments.getFile().getExtension());
		inject(FileBusiness.class).process(arguments.getFile(),reportBasedOnTemplateFile.getBytes(), ReportBusiness.DEFAULT_FILE_NAME_AND_EXTENSION);
		Boolean isNewFile = isNotIdentified(arguments.getFile());
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
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Instanciate one",getClass().getSimpleName());
		InstanciateOneListener<IDENTIFIABLE> listener = new InstanciateOneListener.Adapter.Default<IDENTIFIABLE>(instanciateOne(),values,0,logMessageBuilder);
		IDENTIFIABLE identifiable = __instanciateOne__(values,listener);
		logTrace(logMessageBuilder);
		return identifiable;
	}

	protected IDENTIFIABLE __instanciateOne__(String[] values,InstanciateOneListener<IDENTIFIABLE> listener) {
		return null;
	}
	
	@Override
	public <T> T convert(Converter<IDENTIFIABLE, T> converter) {
		T result = converter.execute();
		logTrace(converter.getLogMessageBuilder());
		return result;
	}
	
	@Override
	public <T> T convert(OneConverter<IDENTIFIABLE, T> converter) {
		T result = converter.execute();
		logTrace(converter.getLogMessageBuilder());
		return result;
	}
	
	@Override
	public <T> T convert(ManyConverter<IDENTIFIABLE, T> converter) {
		T result = converter.execute();
		logTrace(converter.getLogMessageBuilder());
		return result;
	}
	
	/**/
	
}
