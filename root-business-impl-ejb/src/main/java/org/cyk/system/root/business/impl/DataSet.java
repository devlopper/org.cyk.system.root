package org.cyk.system.root.business.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.InstanceHelper.Pool;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.MicrosoftExcelHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.TimeHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class DataSet extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 2282674526022995453L;
	
	private static final String INSTANCE_BUILDER_FROM_ARRAY_CLASS_NAME = "BuilderOneDimensionArray";
	
	protected String systemIdentifier;
	protected String excelWorkbookFileName;
	protected final Collection<Class<?>> excelSheetClasses = new LinkedHashSet<>();
	protected final Collection<Class<?>> excelSheetRequiredClasses = new LinkedHashSet<>();
	
	protected Package basePackage;
	protected Class<?> baseClass;
	protected Deque<Package> basePackageQueue = new ArrayDeque<>();
	protected Boolean basePackageQueueingEnabled = Boolean.FALSE;
	
	protected Map<Class<?>,org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<?>> instanceKeyBuilderMap = new LinkedHashMap<>();
	protected Map<Class<?>,org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<?>> instanceBuilderMap = new LinkedHashMap<>();
	protected Map<Class<?>,Collection<?>> instanceMap = new LinkedHashMap<>();
	protected Map<Class<?>,Integer> identifiableCountByTransactionMap = new LinkedHashMap<>();
	
	public DataSet(Class<?> baseClass) {
		this.baseClass = baseClass;
		this.basePackage = this.baseClass.getPackage();
		this.systemIdentifier = StringUtils.substringBetween(basePackage.getName(), "system.", ".business");
		this.excelWorkbookFileName = systemIdentifier+"data.xls";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void __instanciate__(){
		String fileName = getExcelWorkbookFileName();
		if(StringHelper.getInstance().isBlank(fileName))
			return;
		logTrace("instanciation from excel file {}",fileName);
		/*
		 * Fetch data from excel sheets
		 */
		Collection<Class<AbstractIdentifiable>> classes = new ArrayList<>();
		for(Class<?> aClass : excelSheetClasses)
			classes.add((Class<AbstractIdentifiable>) aClass);
		InputStream  workbookFileInputStream;
		//Integer count = 0;
		for(Class<AbstractIdentifiable> aClass : classes){
			LoggingHelper.Logger<?, ?, ?> logger = LoggingHelper.getInstance().getLogger();
			logger.getMessageBuilder(Boolean.TRUE).addManyParameters("instanciate",new Object[]{"entity",aClass.getSimpleName()});
			
			workbookFileInputStream = baseClass.getResourceAsStream(fileName);
			TimeHelper.Collection timeCollection = new TimeHelper.Collection().addCurrent();
			org.cyk.system.root.business.impl.helper.ArrayHelper.KeyBuilder keyBuilder = new org.cyk.system.root.business.impl.helper.ArrayHelper.KeyBuilder();
			org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<AbstractIdentifiable> instanceBuilder = (BuilderOneDimensionArray<AbstractIdentifiable>) instanceBuilderMap.get(aClass);
			if(instanceBuilder==null)
				instanceBuilder = new org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray(aClass).addFieldCodeName();
	    	
	    	InstanceHelper.Pool.getInstance().load(aClass);
	    	MicrosoftExcelHelper.Workbook.Sheet.Builder builder = new MicrosoftExcelHelper.Workbook.Sheet.Builder.Adapter.Default(workbookFileInputStream,aClass);
	    	//builder.setCloseWorkbook(count++ == classes.size());
	    	builder.createMatrix().getMatrix().getRow().setFromIndex(1);
	    	builder.getMatrix().getRow().setKeyBuilder(keyBuilder);
	    	builder.getMatrix().getRow().getKeyBuilder().addParameters(new Object[]{0});
	    	builder.getMatrix().getRow().addIgnoredKeyValues(MethodHelper.getInstance().callGet(InstanceHelper.Pool.getInstance().get(aClass), String.class
	    			, GlobalIdentifier.FIELD_CODE));	
	    	
	    	MicrosoftExcelHelper.Workbook.Sheet sheet = builder.execute();
	    	logger.getMessageBuilder(Boolean.TRUE).addManyParameters(new Object[]{"#values",ArrayHelper.getInstance().size(sheet.getValues())}
	    		,new Object[]{"#ignored",ArrayHelper.getInstance().size(sheet.getIgnoreds())});
	    	InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<AbstractIdentifiable> instancesBuilder = new InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<AbstractIdentifiable>();
			instanceBuilder.setKeyBuilder(keyBuilder);
			instancesBuilder.setOneDimensionArray(instanceBuilder);
			Collection<AbstractIdentifiable> identifiables = inject(BusinessInterfaceLocator.class).injectTyped(aClass).instanciateMany(sheet,instanceBuilder);
			addInstances(aClass, identifiables);
			Pool.getInstance().add(aClass, identifiables);
			timeCollection.addCurrent();
			logger.getMessageBuilder().addManyParameters(new Object[]{"duration",new TimeHelper.Stringifier.Duration.Adapter.Default(timeCollection.getDuration()).execute()});
			logger.execute(getClass(),LoggingHelper.Logger.Level.TRACE,null);
		}
	}
	
	public void instanciate(){
		Long millisecond = System.currentTimeMillis();
		__instanciate__();
		logTrace("instanciate system data {} done. duration is {}", systemIdentifier,new TimeHelper.Stringifier.Duration.Adapter
				.Default(System.currentTimeMillis()-millisecond).execute());
	}
	
	@SuppressWarnings("unchecked")
	public void __create__(){
		for(Entry<Class<?>,Collection<?>> entry : instanceMap.entrySet()){
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, entry.getKey()) && !CollectionHelper.getInstance().isEmpty(entry.getValue())){
				Long millisecond1 = System.currentTimeMillis();
				Integer count = identifiableCountByTransactionMap.get(entry.getKey());
				if(count==null)
					inject(GenericBusiness.class).create((Collection<AbstractIdentifiable>)entry.getValue());
				else for(Object identifiable : entry.getValue())
					inject(GenericBusiness.class).create((AbstractIdentifiable)identifiable);
				
				logTrace("create {}. count {} , duration is {}", entry.getKey().getSimpleName(),CollectionHelper.getInstance().getSize(entry.getValue())
						,new TimeHelper.Stringifier.Duration.Adapter.Default(System.currentTimeMillis()-millisecond1).execute());
			}
		}
	}
	
	public void create(){
		Long millisecond = System.currentTimeMillis();
		logTrace("create system data {} running", systemIdentifier);
		__create__();
		logTrace("create system data {} done. duration is {}", systemIdentifier,new TimeHelper.Stringifier.Duration.Adapter
				.Default(System.currentTimeMillis()-millisecond).execute());
	}
	
	@SuppressWarnings("unchecked")
	public <T> DataSet addClass(Class<T> aClass,org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<?> instanceBuilder){
		excelSheetClasses.add(aClass);
		if(instanceBuilder==null){
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass)){
				String instanceBuilderClassName = inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass).getClass().getName()+Constant.CHARACTER_DOLLAR+INSTANCE_BUILDER_FROM_ARRAY_CLASS_NAME;
				Class<?> instanceBuilderClass = ClassHelper.getInstance().getByName(instanceBuilderClassName,Boolean.TRUE);	
				if(instanceBuilderClass==null)
					System.out.println("No instance builder set for "+aClass+" : "+instanceBuilderClassName);
				else
					instanceBuilder = (BuilderOneDimensionArray<?>) ClassHelper.getInstance().instanciateOne(instanceBuilderClass);	
			}
		}
		
		if(instanceBuilder!=null)
			instanceBuilderMap.put(aClass, instanceBuilder);
		return this;
	}
	
	public <T> DataSet addClass(Class<T> aClass){
		return addClass(aClass, null);
	}

	public <T> DataSet addInstances(Class<T> aClass,@SuppressWarnings("unchecked") T...instances){
		if(!ArrayHelper.getInstance().isEmpty(instances))
			addInstances(aClass, Arrays.asList(instances));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Collection<T> getInstances(Class<T> aClass){
		return (Collection<T>) instanceMap.get(aClass);
	}
	
	@SuppressWarnings("unchecked")
	public <T> DataSet addInstances(Class<T> aClass,Collection<T> instances){
		if(!CollectionHelper.getInstance().isEmpty(instances)){
			Collection<T> collection = (Collection<T>) instanceMap.get(aClass);
			if(collection==null){
				collection = new ArrayList<>();
				instanceMap.put((Class<AbstractIdentifiable>)aClass, (Collection<AbstractIdentifiable>)collection);
			}
			collection.addAll(instances);
		}
		return this;
	}

	public <T extends AbstractIdentifiable> DataSet create(Class<T> aClass,Integer countByTransaction){
		for(T instance : getInstances(aClass)){
    		inject(GenericBusiness.class).create(instance);
    	}
		return this;
	}
	
	public <T extends AbstractIdentifiable> DataSet create(Class<T> aClass){
		return create(aClass, null);
	}
}
