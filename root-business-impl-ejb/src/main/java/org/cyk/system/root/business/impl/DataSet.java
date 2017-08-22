package org.cyk.system.root.business.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
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
import org.cyk.utility.common.helper.TimeHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class DataSet extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 2282674526022995453L;
	
	private static final String INSTANCE_BUILDER_FROM_ARRAY_CLASS_NAME = "BuilderOneDimensionArray";
	
	private String systemIdentifier;
	private String excelWorkbookFileName;
	private final Collection<Class<?>> excelSheetClasses = new LinkedHashSet<>();
	private final Collection<Class<?>> excelSheetRequiredClasses = new LinkedHashSet<>();
	
	private Package basePackage;
	private Class<?> baseClass;
	private Deque<Package> basePackageQueue = new ArrayDeque<>();
	private Boolean basePackageQueueingEnabled = Boolean.FALSE;
	
	private Map<Class<?>,org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<?>> instanceKeyBuilderMap = new LinkedHashMap<>();
	private Map<Class<?>,org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<?>> instanceBuilderMap = new LinkedHashMap<>();
	private Map<Class<AbstractIdentifiable>,Collection<AbstractIdentifiable>> instanceMap = new LinkedHashMap<>();
	
	public DataSet(Class<?> baseClass) {
		this.baseClass = baseClass;
		this.basePackage = this.baseClass.getPackage();
		this.systemIdentifier = StringUtils.substringBetween(basePackage.getName(), "system.", ".business");
	}
	
	public String getExcelWorkbookFileName(){
		if(excelWorkbookFileName==null)
			excelWorkbookFileName = systemIdentifier+"data.xls";
		return excelWorkbookFileName;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void instanciate(){
		Long millisecond = System.currentTimeMillis();
		String fileName = getExcelWorkbookFileName();
		logTrace("instanciate {} system data using file {} running...", systemIdentifier,fileName);
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
			instanceMap.put(aClass, identifiables);
			Pool.getInstance().add(aClass, identifiables);
			timeCollection.addCurrent();
			logger.getMessageBuilder().addManyParameters(new Object[]{"duration",new TimeHelper.Stringifier.Duration.Adapter.Default(timeCollection.getDuration()).execute()});
			logger.execute(getClass(),LoggingHelper.Logger.Level.TRACE,null);
		}
		logTrace("instanciate system data {} done. duration is {}", systemIdentifier,new TimeHelper.Stringifier.Duration.Adapter
				.Default(System.currentTimeMillis()-millisecond).execute());
	}
	
	public void create(){
		Long millisecond = System.currentTimeMillis();
		logTrace("create system data {} running", systemIdentifier);
		for(Entry<Class<AbstractIdentifiable>,Collection<AbstractIdentifiable>> entry : instanceMap.entrySet()){
			if(!CollectionHelper.getInstance().isEmpty(entry.getValue())){
				Long millisecond1 = System.currentTimeMillis();
				inject(GenericBusiness.class).create(entry.getValue());
				logTrace("create {}. count {} , duration is {}", entry.getKey().getSimpleName(),CollectionHelper.getInstance().getSize(entry.getValue())
						,new TimeHelper.Stringifier.Duration.Adapter.Default(System.currentTimeMillis()-millisecond1).execute());
			}
		}
		logTrace("create system data {} done. duration is {}", systemIdentifier,new TimeHelper.Stringifier.Duration.Adapter
				.Default(System.currentTimeMillis()-millisecond).execute());
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> DataSet addClass(Class<T> aClass,org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<T> instanceBuilder){
		excelSheetClasses.add(aClass);
		if(instanceBuilder==null){
			String instanceBuilderClassName = inject(BusinessInterfaceLocator.class).injectTyped(aClass).getClass().getName()+Constant.CHARACTER_DOLLAR+INSTANCE_BUILDER_FROM_ARRAY_CLASS_NAME;
			Class<?> instanceBuilderClass = ClassHelper.getInstance().getByName(instanceBuilderClassName,Boolean.TRUE);	
			if(instanceBuilderClass==null)
				System.out.println("No instance builder set for "+aClass+" : "+instanceBuilderClassName);
			else
				instanceBuilder = (BuilderOneDimensionArray<T>) ClassHelper.getInstance().instanciateOne(instanceBuilderClass);
		}
		
		if(instanceBuilder!=null)
			instanceBuilderMap.put(aClass, instanceBuilder);
		return this;
	}
	
	public <T extends AbstractIdentifiable> DataSet addClass(Class<T> aClass){
		return addClass(aClass, null);
	}
}
