package org.cyk.system.root.business.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.InstanceHelper.Pool;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.MicrosoftExcelHelper;
import org.cyk.utility.common.helper.TimeHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class DataSet extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 2282674526022995453L;
	
	private String systemIdentifier;
	private String excelWorkbookFileName;
	private final Collection<Class<?>> excelSheetClasses = new LinkedHashSet<>();
	private final Collection<Class<?>> excelSheetRequiredClasses = new LinkedHashSet<>();
	
	private Package basePackage;
	private Deque<Package> basePackageQueue = new ArrayDeque<>();
	private Boolean basePackageQueueingEnabled = Boolean.FALSE;
	
	private Map<Class<AbstractIdentifiable>,org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<?>> instanceKeyBuilderMap = new HashMap<>();
	private Map<Class<?>,org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<?>> instanceBuilderMap = new HashMap<>();
	private Map<Class<AbstractIdentifiable>,Collection<AbstractIdentifiable>> instanceMap = new HashMap<>();
	
	public DataSet(Package basePackage) {
		this.basePackage = basePackage;
		this.systemIdentifier = StringUtils.substringBetween(basePackage.getName(), "system.", ".business");
	}
	
	public DataSet(Class<?> basePackageClass) {
		this(basePackageClass.getPackage());
	}
	
	public String getExcelWorkbookFileName(){
		if(excelWorkbookFileName==null)
			excelWorkbookFileName = systemIdentifier+"data.xls";
		return excelWorkbookFileName;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void instanciate(){
		System.out.println("Instanciate from "+systemIdentifier);
		/*
		 * Fetch data from excel sheets
		 */
		Collection<Class<AbstractIdentifiable>> classes = new ArrayList<>();
		for(Class<?> aClass : excelSheetClasses)
			classes.add((Class<AbstractIdentifiable>) aClass);
		InputStream  workbookFileInputStream;
		//Integer count = 0;
		for(Class<AbstractIdentifiable> aClass : classes){
			workbookFileInputStream = getClass().getResourceAsStream(getExcelWorkbookFileName());
			TimeHelper.Collection timeCollection = new TimeHelper.Collection().addCurrent();
			org.cyk.system.root.business.impl.helper.ArrayHelper.KeyBuilder keyBuilder = new org.cyk.system.root.business.impl.helper.ArrayHelper.KeyBuilder();
			org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<AbstractIdentifiable> instanceBuilder = (BuilderOneDimensionArray<AbstractIdentifiable>) instanceBuilderMap.get(aClass);
			if(instanceBuilder==null)
				instanceBuilder = new org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray(aClass).addFieldCodeName();
	    	System.out.print(aClass.getSimpleName()+" ");
	    	InstanceHelper.Pool.getInstance().load(aClass);
	    	MicrosoftExcelHelper.Workbook.Sheet.Builder builder = new MicrosoftExcelHelper.Workbook.Sheet.Builder.Adapter.Default(workbookFileInputStream,aClass);
	    	//builder.setCloseWorkbook(count++ == classes.size());
	    	builder.createMatrix().getMatrix().getRow().setFromIndex(1);
	    	builder.getMatrix().getRow().setKeyBuilder(keyBuilder);
	    	builder.getMatrix().getRow().getKeyBuilder().addParameters(new Object[]{0});
	    	builder.getMatrix().getRow().addIgnoredKeyValues(MethodHelper.getInstance().callGet(InstanceHelper.Pool.getInstance().get(aClass), String.class
	    			, GlobalIdentifier.FIELD_CODE));	
	    	
	    	System.out.print("sheet ");
	    	MicrosoftExcelHelper.Workbook.Sheet sheet = builder.execute();
	    	System.out.print("(#values="+ArrayHelper.getInstance().size(sheet.getValues())+",#ignored="+ArrayHelper.getInstance().size(sheet.getIgnoreds())+") ");
			InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<AbstractIdentifiable> instancesBuilder = new InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<AbstractIdentifiable>();
			instanceBuilder.setKeyBuilder(keyBuilder);
			instancesBuilder.setOneDimensionArray(instanceBuilder);
			System.out.print("instanciate ");
			Collection<AbstractIdentifiable> identifiables = inject(BusinessInterfaceLocator.class).injectTyped(aClass).instanciateMany(sheet,instanceBuilder);
			instanceMap.put(aClass, identifiables);
			Pool.getInstance().add(aClass, identifiables);
			timeCollection.addCurrent();
			System.out.println("SUCCESS. "+new TimeHelper.Stringifier.Duration.Adapter.Default(timeCollection.getDuration()).execute());
		}
	}
	
	public void create(){
		System.out.println("Creating from "+systemIdentifier);
		for(Entry<Class<AbstractIdentifiable>,Collection<AbstractIdentifiable>> entry : instanceMap.entrySet()){
			if(!CollectionHelper.getInstance().isEmpty(entry.getValue())){
				inject(GenericBusiness.class).create(entry.getValue());
			}
		}
		System.out.println("Create from "+systemIdentifier+" DONE");
	}
	
	public <T extends AbstractIdentifiable> DataSet addClass(Class<T> aClass,org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<T> instanceBuilder){
		excelSheetClasses.add(aClass);
		if(instanceBuilder!=null)
			instanceBuilderMap.put(aClass, instanceBuilder);
		return this;
	}
	
	public <T extends AbstractIdentifiable> DataSet addClass(Class<T> aClass){
		return addClass(aClass, null);
	}
}
