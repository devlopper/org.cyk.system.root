package org.cyk.system.root.business.impl.__data__;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.business.impl.globalidentification.GlobalIdentifierBusinessImpl;
import org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.information.Tag;
import org.cyk.system.root.model.information.Tangibility;
import org.cyk.system.root.model.language.programming.Script;
import org.cyk.system.root.model.language.programming.ScriptEvaluationEngine;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementAction;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionTypeMode;
import org.cyk.system.root.model.mathematics.movement.MovementGroupType;
import org.cyk.system.root.model.mathematics.movement.MovementMode;
import org.cyk.system.root.model.mathematics.movement.MovementReason;
import org.cyk.system.root.model.metadata.Entity;
import org.cyk.system.root.model.metadata.Property;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.Store;
import org.cyk.system.root.model.party.StoreType;
import org.cyk.system.root.model.party.person.Allergy;
import org.cyk.system.root.model.party.person.BloodGroup;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Medication;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRoleName;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.time.DurationType;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionType;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.model.value.Measure;
import org.cyk.system.root.model.value.MeasureType;
import org.cyk.system.root.model.value.Value;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.InstanceHelper.Builder.OneDimensionArray;
import org.cyk.utility.common.helper.InstanceHelper.Pool;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.MicrosoftExcelHelper;
import org.cyk.utility.common.helper.StackTraceHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.TimeHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class DataSet extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 2282674526022995453L;
	
	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class, Boolean.FALSE);
	}
	
	private static final String INSTANCE_BUILDER_FROM_ARRAY_CLASS_NAME = "BuilderOneDimensionArray";
	
	protected String systemIdentifier;
	protected String excelWorkbookFileName;
	protected final Collection<Class<?>> excelSheetClasses = new LinkedHashSet<>();
	protected final Collection<Class<?>> excelSheetRequiredClasses = new LinkedHashSet<>();
	
	protected Package basePackage;
	protected Class<?> baseClass;
	protected Deque<Package> basePackageQueue = new ArrayDeque<>();
	protected Boolean basePackageQueueingEnabled = Boolean.FALSE;
	
	protected Map<Class<?>,org.cyk.utility.common.helper.ArrayHelper.Dimension.Key.Builder> instanceKeyBuilderMap = new LinkedHashMap<>();
	protected Map<Class<?>,org.cyk.utility.common.helper.InstanceHelper.Builder.OneDimensionArray<?>> instanceBuilderMap = new LinkedHashMap<>();
	protected Map<Class<?>,Collection<?>> instanceMap = new LinkedHashMap<>();
	protected Map<Class<?>,Integer> identifiableCountByTransactionMap = new LinkedHashMap<>();
	protected Map<Class<?>,String> sheetNameMap = new HashMap<>();
	
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
		Collection<Class<?>> classes = new ArrayList<>();
		for(Class<?> aClass : excelSheetClasses)
			classes.add(aClass);
		
		InputStream  workbookFileInputStream;
		//Integer count = 0;
		for(Class<?> aClass : classes){
			LoggingHelper.Logger<?, ?, ?> logger = LoggingHelper.getInstance().getLogger();
			logger.getMessageBuilder(Boolean.TRUE).addManyParameters("instanciate",new Object[]{"entity",aClass.getSimpleName()});
			
			workbookFileInputStream = baseClass.getResourceAsStream(fileName);
			if(workbookFileInputStream==null){
				logError("file {} cannot be loaded", fileName);
			}
			TimeHelper.Collection timeCollection = new TimeHelper.Collection().addCurrent();
			org.cyk.utility.common.helper.ArrayHelper.Dimension.Key.Builder keyBuilder = instanceKeyBuilderMap.get(aClass);
			if(keyBuilder==null)
				if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass))
					keyBuilder = new org.cyk.system.root.business.impl.helper.ArrayHelper.KeyBuilder();
				else
					keyBuilder = new org.cyk.system.root.business.impl.helper.ArrayHelper.KeyBuilder();
			
			org.cyk.utility.common.helper.InstanceHelper.Builder.OneDimensionArray<?> instanceBuilder = (OneDimensionArray<Object>) instanceBuilderMap.get(aClass);
			if(instanceBuilder==null)
				instanceBuilder = new org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray(aClass).addFieldCodeName();
	    	
	    	InstanceHelper.Pool.getInstance().load(aClass);
	    	MicrosoftExcelHelper.Workbook.Sheet.Builder builder = new MicrosoftExcelHelper.Workbook.Sheet.Builder.Adapter.Default(workbookFileInputStream,sheetNameMap.get(aClass),aClass);
	    	//builder.setCloseWorkbook(count++ == classes.size());
	    	builder.createMatrix().getMatrix().getRow().setFromIndex(1);
	    	builder.getMatrix().getRow().setKeyBuilder(keyBuilder);
	    	builder.getMatrix().getRow().getKeyBuilder().addParameters(new Object[]{0});
	    	builder.getMatrix().getRow().addIgnoredKeyValues(MethodHelper.getInstance().callGet(InstanceHelper.Pool.getInstance().get(aClass), String.class
	    			, GlobalIdentifier.FIELD_CODE));	
	    	
	    	MicrosoftExcelHelper.Workbook.Sheet sheet = builder.execute();
	    	logger.getMessageBuilder(Boolean.TRUE).addManyParameters(new Object[]{"#values",ArrayHelper.getInstance().size(sheet.getValues())}
	    		,new Object[]{"#ignored",ArrayHelper.getInstance().size(sheet.getIgnoreds())});
	    
			instanceBuilder.setKeyBuilder(keyBuilder);
			instanceBuilder.setIsAddInstanceToPool(ClassHelper.getInstance().isInstanceOf(AbstractDataTreeNode.class, aClass));
			Collection<?> instances = null;
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass)){
				instances = inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass).instanciateMany(sheet,(InstanceHelper.Builder.OneDimensionArray<AbstractIdentifiable>) instanceBuilder);
			}else{
				instances = inject(GlobalIdentifierBusiness.class).instanciateMany(sheet, (InstanceHelper.Builder.OneDimensionArray<GlobalIdentifier>)instanceBuilder);
			}
			logger.getMessageBuilder().addNamedParameters("#instances",CollectionHelper.getInstance().getSize(instances));
			//if(!ClassHelper.getInstance().isInstanceOf(AbstractDataTreeNode.class, fClass))
			addInstances(aClass, commonUtils.castCollection(instances, aClass),Boolean.TRUE/*!ClassHelper.getInstance().isInstanceOf(AbstractDataTreeNode.class, aClass)*/);
			
			timeCollection.addCurrent();
			logger.getMessageBuilder().addManyParameters(new Object[]{"duration",new TimeHelper.Stringifier.Duration.Adapter.Default(timeCollection.getDuration()).execute()});
			logger.execute(getClass(),LoggingHelper.Logger.Level.TRACE,null);
		}
		Listener listener = ClassHelper.getInstance().instanciateOne(Listener.class);
		listener.instanciate(this);
	}
	
	public DataSet instanciate(){
		new LoggingHelper.Run.Adapter.Default(StackTraceHelper.getInstance().getAt(2),getClass()){
			private static final long serialVersionUID = 1L;
			
			public Object __execute__() {
				__instanciate__();
				return null;
			}
			
			public String getName() {
				return super.getName()+Constant.CHARACTER_SPACE+systemIdentifier;
			}
			
		}.execute();
		/*
		Long millisecond = System.currentTimeMillis();
		__instanciate__();
		logTrace("instanciate system data {} done. duration is {}", systemIdentifier,new TimeHelper.Stringifier.Duration.Adapter
				.Default(System.currentTimeMillis()-millisecond).execute());
		*/
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public void __save__(){
		for(Entry<Class<?>,Collection<?>> entry : instanceMap.entrySet()){
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, entry.getKey()) && !CollectionHelper.getInstance().isEmpty(entry.getValue())){
				Integer saveCount = CollectionHelper.getInstance().getSize(entry.getValue());
				Integer createCount = CollectionHelper.getInstance().getSize(new CollectionHelper.Filter.Adapter.Default<Object>((Collection<Object>) entry.getValue())
						.setProperty(CollectionHelper.Filter.PROPERTY_NAME_FIELD_NAME, AbstractIdentifiable.FIELD_IDENTIFIER)
						.setProperty(CollectionHelper.Filter.PROPERTY_NAME_FIELD_VALUE, null)
						.execute());
				Integer updateCount = saveCount - createCount;
				
				Long millisecond1 = System.currentTimeMillis();
				Integer count = identifiableCountByTransactionMap.get(entry.getKey());
				if(count==null)
					inject(GenericBusiness.class).save((Collection<AbstractIdentifiable>)entry.getValue());
				else for(Object identifiable : entry.getValue())
					inject(GenericBusiness.class).save((AbstractIdentifiable)identifiable);
				
				logTrace("save {}. count {} #create {} #update {} , duration is {}", entry.getKey().getSimpleName(),saveCount,createCount,updateCount
						,new TimeHelper.Stringifier.Duration.Adapter.Default(System.currentTimeMillis()-millisecond1).execute());
			}
		}
	}
	
	public void save(){
		new LoggingHelper.Run.Adapter.Default(StackTraceHelper.getInstance().getAt(2),getClass()){
			private static final long serialVersionUID = 1L;
			
			public Object __execute__() {
				__save__();
				return null;
			}
			
			public String getName() {
				return super.getName()+Constant.CHARACTER_SPACE+systemIdentifier;
			}
			
		}.execute();
		/*
		Long millisecond = System.currentTimeMillis();
		logTrace("save {} system data running", systemIdentifier);
		__save__();
		logTrace("save {} system data done. duration is {}", systemIdentifier,new TimeHelper.Stringifier.Duration.Adapter
				.Default(System.currentTimeMillis()-millisecond).execute());
				*/
	}
	
	@SuppressWarnings("unchecked")
	public <T> DataSet addClass(Class<T> aClass,org.cyk.utility.common.helper.InstanceHelper.Builder.OneDimensionArray<?> instanceBuilder){
		Listener listener = ClassHelper.getInstance().instanciateOne(Listener.class);
		if(Boolean.TRUE.equals(InstanceHelper.getInstance().getIfNotNullElseDefault(listener.isAddable(aClass),Boolean.TRUE))){
			excelSheetClasses.add(aClass);
			if(instanceBuilder==null){
				if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass)){
					String instanceBuilderClassName = inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass).getClass().getName()+Constant.CHARACTER_DOLLAR+INSTANCE_BUILDER_FROM_ARRAY_CLASS_NAME;
					Class<?> instanceBuilderClass = ClassHelper.getInstance().getByName(instanceBuilderClassName,Boolean.TRUE);	
					if(instanceBuilderClass==null)
						System.out.println("No instance builder set for "+aClass+" : "+instanceBuilderClassName);
					else
						instanceBuilder = (BuilderOneDimensionArray<?>) ClassHelper.getInstance().instanciateOne(instanceBuilderClass);	
				}else{
					instanceBuilder = new GlobalIdentifierBusinessImpl.BuilderOneDimensionArray();
				}
					
			}
			
			if(instanceBuilder!=null)
				instanceBuilderMap.put(aClass, instanceBuilder);	
		}
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
	public <T> DataSet addInstances(Class<T> aClass,Collection<?> instances,Boolean addToPool){
		if(!CollectionHelper.getInstance().isEmpty(instances)){
			Collection<T> collection = (Collection<T>) instanceMap.get(aClass);
			if(collection==null){
				collection = new ArrayList<>();
				instanceMap.put(aClass, collection);
			}
			Collection<T> classInstances = CollectionHelper.getInstance().cast(aClass, instances);
			collection.addAll(classInstances);
			if(Boolean.TRUE.equals(addToPool))
				Pool.getInstance().add(aClass, classInstances);
		}
		return this;
	}
	
	public <T> DataSet addInstances(Class<T> aClass,Collection<?> instances){
		return addInstances(aClass, instances, Boolean.TRUE);
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
	
	/**/
	
	public <T extends AbstractIdentifiable> T getInstance(Class<T> aClass,String code){
		return InstanceHelper.Pool.getInstance().get(aClass, code);
	}
	
	/**/
	
	public interface Listener {
		
		Collection<Package> getPackages();
		Collection<Package> getExcludedPackages();
		Collection<Class<?>> getClasses();
		Collection<Class<?>> getExcludedClasses();
		Boolean isAddable(Class<?> aClass);
		
		Collection<Class<?>> getRelatedClasses(Class<?> aClass);
		void processRelatedClasses(Class<?> aClass,Collection<Class<?>> classes);
		
		void instanciate(DataSet dataSet);
		
		@Getter
		public static class Adapter extends AbstractBean implements DataSet.Listener {
			private static final long serialVersionUID = 1L;
			
			protected Collection<Package> packages,excludedPackages;
			protected Collection<Class<?>> classes,excludedClasses;
			
			@Override
			public Boolean isAddable(Class<?> aClass) {
				return null;
			}
			
			@Override
			public Collection<Class<?>> getRelatedClasses(Class<?> aClass) {
				return null;
			}
			
			@Override
			public void processRelatedClasses(Class<?> aClass,Collection<Class<?>> classes) {}
			
			@Override
			public void instanciate(DataSet dataSet) {}
			
			/**/
			
			public static class Default extends DataSet.Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default() {
					classes = getClasses();
					if(classes!=null){
						classes = new ArrayList<>(classes);
						Collection<Class<?>> related = new ArrayList<>();
						for(Class<?> index : classes){
							Collection<Class<?>> r = getRelatedClasses(index);
							if(r == null)
								r = new ArrayList<>();
							if(r!=null){
								processRelatedClasses(index,r);
								related.addAll(r);
							}
						}
						classes.addAll(related);
					}
					packages = getPackages();
					excludedPackages = getExcludedPackages();
					excludedClasses = getExcludedClasses();
				}
				
				@Override
				public void processRelatedClasses(Class<?> aClass,Collection<Class<?>> classes) {
					super.processRelatedClasses(aClass,classes);
					if(Script.class.equals(aClass)){
						classes.addAll(Arrays.asList(ScriptEvaluationEngine.class,Script.class));
					}else if(File.class.equals(aClass)){
						classes.addAll(Arrays.asList(FileRepresentationType.class));
					}else if(Interval.class.equals(aClass)){
						classes.addAll(Arrays.asList(IntervalCollection.class,Interval.class,MetricCollectionType.class));
					}else if(Movement.class.equals(aClass)){
						classes.addAll(Arrays.asList(MetricCollectionType.class,MovementAction.class,MovementReason.class,MovementMode.class,MovementGroupType.class
								,MovementCollectionType.class,MovementCollectionTypeMode.class,MovementCollection.class));
					}else if(IdentifiablePeriod.class.equals(aClass)){
						classes.addAll(Arrays.asList(TimeDivisionType.class,DurationType.class,IdentifiablePeriodCollection.class,IdentifiablePeriodCollectionType.class));
					}else if(Value.class.equals(aClass)){
						classes.addAll(Arrays.asList(Measure.class,MeasureType.class));
					}else if(Party.class.equals(aClass)){
						classes.addAll(Arrays.asList(BusinessRole.class,Sex.class,MaritalStatus.class,JobFunction.class,JobTitle.class,PersonTitle.class,BloodGroup.class
								,Allergy.class,Medication.class,PersonRelationshipTypeGroup.class,PersonRelationshipType.class,PersonRelationshipTypeRoleName.class
								,PersonRelationshipTypeRole.class,Store.class,StoreType.class));
					}else if(Entity.class.equals(aClass)){
						classes.addAll(Arrays.asList(Entity.class,Property.class));
					}else if(Tangibility.class.equals(aClass)){
						classes.addAll(Arrays.asList(Tangibility.class,Comment.class,Tag.class));
					}
				}
				
				@Override
				public Boolean isAddable(Class<?> aClass) {	
					Boolean excluded = excludedClasses == null ? null : CollectionHelper.getInstance().contains(excludedClasses, aClass);
					Boolean included = classes == null ? null : CollectionHelper.getInstance().contains(classes, aClass);
					if(excluded == null)
						return included==null || included;
					return excluded ? Boolean.TRUE : (included==null || included);
				}			
			}
		}
		
	}
}
