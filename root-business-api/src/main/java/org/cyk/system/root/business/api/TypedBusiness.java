package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.report.RootReportProducer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.converter.Converter;
import org.cyk.utility.common.converter.ManyConverter;
import org.cyk.utility.common.converter.OneConverter;

public interface TypedBusiness<IDENTIFIABLE extends AbstractIdentifiable> extends IdentifiableBusinessService<IDENTIFIABLE, Long> {

	/* predefined query  */
	
	@Deprecated
    IDENTIFIABLE load(Long identifier);
	@Deprecated
    void load(IDENTIFIABLE identifiable);
	@Deprecated
	void load(Collection<IDENTIFIABLE> identifiables);
    
    IDENTIFIABLE delete(String code);
    Collection<IDENTIFIABLE> delete(Set<String> codes);
    
    Collection<IDENTIFIABLE> instanciateMany(List<String[]> list);
    Collection<IDENTIFIABLE> instanciateMany(String[][] strings);
    
    //IDENTIFIABLE instanciateOne(String[] values,InstanciateOneListener listener);
    IDENTIFIABLE instanciateOne(String[] values);
    
    IDENTIFIABLE instanciateOneRandomly();
    IDENTIFIABLE instanciateOneRandomly(String code);
    Collection<IDENTIFIABLE> instanciateManyRandomly(Integer count);
    Collection<IDENTIFIABLE> instanciateManyRandomly(Set<String> codes);
    
    IDENTIFIABLE findDefaulted(); 
    
    Collection<IDENTIFIABLE> findAllExclude(Collection<IDENTIFIABLE> identifiables); 
    
    Long countAllExclude(Collection<IDENTIFIABLE> identifiables); 
    
    Collection<IDENTIFIABLE> findByGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers);
	Long countByGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers);
    
	IDENTIFIABLE findByGlobalIdentifierValue(String globalIdentifier);
	
	Collection<IDENTIFIABLE> findByGlobalIdentifierOrderNumber(Long orderNumber);
	
    Collection<IDENTIFIABLE> findByClasses(Collection<Class<?>> classes);
    Long countByClasses(Collection<Class<?>> classes);
    
    Collection<IDENTIFIABLE> findByNotClasses(Collection<Class<?>> classes);
    Long countByNotClasses(Collection<Class<?>> classes);
    
    /**/
    
    <T extends IDENTIFIABLE> Collection<T> findByClass(Class<T> aClass);
    Long countByClass(Class<?> aClass);
    
    Collection<IDENTIFIABLE> findByNotClass(Class<?> aClass);
    Long countByNotClass(Class<?> aClass);
    
    /**
	 * {@link #findByGlobalIdentifierCode(String)}
	 * 
	 */
	IDENTIFIABLE find(String globalIdentifierCode);
	
	/**
	 * {@link #findByGlobalIdentifierCodes(Collection<String>)}
	 * 
	 */
	Collection<IDENTIFIABLE> find(Collection<String> globalIdentifierCodes);
    
	File createReportFile(CreateReportFileArguments<IDENTIFIABLE> arguments);
	File createReportFile(IDENTIFIABLE identifiable,String reportTemplateCode,Locale locale,Map<String, Boolean> fieldSortingMap);
	File createReportFile(IDENTIFIABLE identifiable,String reportTemplateCode,Locale locale);
	File createReportFile(IDENTIFIABLE identifiable,String reportTemplateCode,Map<String, Boolean> fieldSortingMap);
	File createReportFile(IDENTIFIABLE identifiable,String reportTemplateCode);
	Collection<File> createReportFiles(Collection<IDENTIFIABLE> identifiables,String reportTemplateCode,Map<String, Boolean> fieldSortingMap);
	Collection<File> createReportFiles(Collection<IDENTIFIABLE> identifiables,String reportTemplateCode);
	
	File findReportFile(IDENTIFIABLE identifiable,ReportTemplate reportTemplate,Boolean createIfNull);
	File findReportFile(IDENTIFIABLE identifiable,String reportTemplateCode,Boolean createIfNull);
	
	@Getter @Setter @AllArgsConstructor
	public static class CreateReportFileArguments<IDENTIFIABLE extends AbstractIdentifiable> implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private ReportTemplate reportTemplate;
		private Collection<ValueCollection> reportTemplateValueCollections = new ArrayList<>();
		private IDENTIFIABLE identifiable;
		private File file,backgroundImageFile;//TODO header image should exist too because can be runtime set
		private RootReportProducer reportProducer;
		private Boolean joinFileToIdentifiable = Boolean.TRUE,isDraft=Boolean.FALSE;
		private String identifiableName;
		private Person createdBy;
		private Date creationDate;
		private Locale locale;
		private Map<String, Boolean> fieldSortingMap = new HashMap<String, Boolean>();
		/**/
		
		public CreateReportFileArguments(IDENTIFIABLE identifiable){
			this.identifiable = identifiable;
		}
		
		/**/
		
		@SuppressWarnings("rawtypes") @Getter @Setter
		public static class Builder<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBuilder<CreateReportFileArguments> {
			private static final long serialVersionUID = 1L;

			private IDENTIFIABLE identifiable;
			
			private String reportTemplateCode;
			private ReportTemplate reportTemplate;
			private File file;
			private File backgroundImageFile;
			private RootReportProducer reportProducer;
			private Boolean joinFileToIdentifiable,isDraft = Boolean.FALSE,updateExisting=Boolean.TRUE;
			private Map<String, Boolean> fieldSortingMap = new HashMap<String, Boolean>();
			
			public Builder(IDENTIFIABLE identifiable,Builder<IDENTIFIABLE> builder) {
				super(CreateReportFileArguments.class);
				this.identifiable = identifiable;
				if(builder!=null){
					this.reportTemplateCode = builder.reportTemplateCode;
					this.reportTemplate = builder.reportTemplate;
					this.file = builder.file;
					this.backgroundImageFile = builder.backgroundImageFile;
					this.reportProducer = builder.reportProducer;
					this.joinFileToIdentifiable = builder.joinFileToIdentifiable;
					this.isDraft = builder.isDraft;
					this.updateExisting = builder.updateExisting;
				}
			}
			public Builder(IDENTIFIABLE identifiable) {
				this(identifiable,null);
			}

			@Override
			public CreateReportFileArguments<IDENTIFIABLE> build() {
				final CreateReportFileArguments<IDENTIFIABLE> arguments = new CreateReportFileArguments<>(identifiable);
				arguments.setReportTemplate(listenerUtils.getValue(ReportTemplate.class, Listener.COLLECTION, new ListenerUtils.ResultMethod<Listener, ReportTemplate>(){
					@Override
					public ReportTemplate execute(Listener listener) {
						return listener.getReportTemplate(reportTemplate,reportTemplateCode);
					}

					@Override
					public ReportTemplate getNullValue() {
						return null;
					}}));
				
				
				
				arguments.setBackgroundImageFile(listenerUtils.getValue(File.class, Listener.COLLECTION, new ListenerUtils.ResultMethod<Listener, File>(){
					@Override
					public File execute(Listener listener) {
						return listener.getBackgroundImageFile(identifiable, arguments.getReportTemplate(), backgroundImageFile, isDraft);
					}

					@Override
					public File getNullValue() {
						return null;
					}}));
				
				
				arguments.setJoinFileToIdentifiable(listenerUtils.getBoolean(Listener.COLLECTION, new ListenerUtils.ResultMethod<Listener, Boolean>(){
					@Override
					public Boolean execute(Listener listener) {
						return listener.getJoinFileToIdentifiable(identifiable, arguments.getReportTemplate());
					}

					@Override
					public Boolean getNullValue() {
						return joinFileToIdentifiable;
					}}));
				arguments.setFile(listenerUtils.getValue(File.class, Listener.COLLECTION, new ListenerUtils.ResultMethod<Listener, File>(){
					@Override
					public File execute(Listener listener) {
						return listener.getFile(identifiable, arguments.getReportTemplate(),updateExisting);
					}

					@Override
					public File getNullValue() {
						return null;
					}}));
				if(arguments.getFile()==null)
					arguments.setFile(new File());
				arguments.setReportProducer(reportProducer);
				arguments.setIsDraft(isDraft);
				arguments.getFieldSortingMap().putAll(getFieldSortingMap());
				return arguments;
			}
			
			public Builder<IDENTIFIABLE> setReportTemplate(ReportTemplate reportTemplate){
				this.reportTemplate = reportTemplate;
				return this;
			}
			
			public Builder<IDENTIFIABLE> setReportTemplate(String reportTemplateCode){
				this.reportTemplateCode = reportTemplateCode;
				return this;
			}
			
			public Builder<IDENTIFIABLE> setFile(File file){
				this.file = file;
				return this;
			}
			
			public Builder<IDENTIFIABLE> setReportProducer(RootReportProducer reportProducer){
				this.reportProducer = reportProducer;
				return this;
			}
			
			public Builder<IDENTIFIABLE> setJoinFileToIdentifiable(Boolean joinFileToIdentifiable){
				this.joinFileToIdentifiable = joinFileToIdentifiable;
				return this;
			}
			
			public Builder<IDENTIFIABLE> setIsDraft(Boolean isDraft){
				this.isDraft = isDraft;
				return this;
			}
			
			public Builder<IDENTIFIABLE> setUpdateExisting(Boolean updateExisting){
				this.updateExisting = updateExisting;
				return this;
			}
			
			/**/
			
			public static interface Listener {
				Collection<Listener> COLLECTION = new ArrayList<>();
				
				ReportTemplate getReportTemplate(String code);
				Boolean getJoinFileToIdentifiable(AbstractIdentifiable identifiable, ReportTemplate reportTemplate);
				File getFile(AbstractIdentifiable identifiable, ReportTemplate reportTemplate,Boolean updateExisting);
				ReportTemplate getReportTemplate(ReportTemplate reportTemplate,String code);
				File getBackgroundImageFile(AbstractIdentifiable identifiable,ReportTemplate reportTemplate,File backgroundImageFile,Boolean isDraft);
				/**/
				
				public static class Adapter extends BeanAdapter implements Listener,Serializable {
					private static final long serialVersionUID = 1L;
					@Override
					public ReportTemplate getReportTemplate(String code) {
						return null;
					}
					@Override
					public ReportTemplate getReportTemplate(ReportTemplate reportTemplate, String code) {
						return null;
					}
					@Override
					public File getBackgroundImageFile(AbstractIdentifiable identifiable, ReportTemplate reportTemplate,File backgroundImageFile,Boolean isDraft) {
						return null;
					}
					@Override
					public File getFile(AbstractIdentifiable identifiable, ReportTemplate reportTemplate,Boolean updateExisting) {
						return null;
					}
					@Override
					public Boolean getJoinFileToIdentifiable(AbstractIdentifiable identifiable,ReportTemplate reportTemplate) {
						return null;
					}
					/**/
					
					public static class Default extends Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						@Override
						public ReportTemplate getReportTemplate(ReportTemplate reportTemplate, String code) {
							return reportTemplate == null 
									? ( StringUtils.isNotBlank(code) ? getReportTemplate(code) : null ) 
									: reportTemplate;
						}
						
						@Override
						public File getBackgroundImageFile(AbstractIdentifiable identifiable,ReportTemplate reportTemplate, File backgroundImageFile,Boolean isDraft) {
							return backgroundImageFile == null 
									? ((isDraft==null || Boolean.FALSE.equals(isDraft)) ? reportTemplate.getBackgroundImage() : reportTemplate.getDraftBackgroundImage())
									: backgroundImageFile;
						}
						
					}
				}
			}
			
		}
		
		//TODO use builder
		
	}
	
    //TODO clone service must be implemented using reflection and listener
	
	/**/
	/**
	 * Find the duplicates of identifiable
	 * @param identifiable
	 * @return
	 */
	Collection<IDENTIFIABLE> findDuplicates(IDENTIFIABLE identifiable);
	
	Collection<IDENTIFIABLE> findDuplicates();
	
	<T> T convert(Converter<IDENTIFIABLE, T> converter);
	<T> T convert(OneConverter<IDENTIFIABLE, T> converter);
	<T> T convert(ManyConverter<IDENTIFIABLE, T> converter);

	/**/
	
	public static interface InstanciateOneListener<T> extends Serializable {
		
		SetListener getSetListener();
		InstanciateOneListener<T> setSetListener(SetListener setListener);
		T getInstance();
		
		@Getter @Setter
		public static class Adapter<T> extends BeanAdapter implements InstanciateOneListener<T>,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected T instance;
			protected SetListener setListener;
			
			public InstanciateOneListener<T> setSetListener(SetListener setListener){
				this.setListener = setListener;
				return this;
			}
			
			@Getter @Setter
			public static class Default<T> extends InstanciateOneListener.Adapter<T> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(T instance,String[] values,Integer index,LogMessage.Builder logMessageBuilder) {
					setListener = new SetListener.Adapter.Default(this.instance = instance,values,index,logMessageBuilder);
				}
			}
		}
	}
	
	public static interface SetListener extends Serializable {
		
		public Object getInstance();
		public SetListener setInstance(Object instance);
		public Integer getIndex();
		public SetListener setIndex(Integer index);
		public Integer getIndexIncrement();
		public SetListener setIndexIncrement(Integer indexIncrement);
		public String[] getValues();
		public Class<?> getFieldType();
		public SetListener setFieldType(Class<?> fieldType);
		public Class<?> getFieldType(Class<?> aClass,Field field);
		SetListener setNullValue(Object value);
		Object getNullValue();
		public LogMessage.Builder getLogMessageBuilder();
		
		@Getter @Setter
		public static class Adapter extends BeanAdapter implements SetListener,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected Object instance,nullValue;
			protected Integer index,indexIncrement=1;
			protected String[] values;
			protected LogMessage.Builder logMessageBuilder;
			protected Class<?> fieldType;
			
			public SetListener setInstance(Object instance){
				this.instance = instance;
				return this;
			}
			
			public SetListener setIndex(Integer index){
				this.index = index;
				return this;
			}
			
			public SetListener setIndexIncrement(Integer indexIncrement){
				this.indexIncrement = indexIncrement;
				return this;
			}
			
			public SetListener setFieldType(Class<?> fieldType){
				this.fieldType = fieldType;
				return this;
			}
			
			public SetListener setNullValue(Object nullValue){
				this.nullValue = nullValue;
				return this;
			}
			
			@Override
			public Class<?> getFieldType(Class<?> aClass,Field field) {
				return null;
			}
			
			public static class Default extends SetListener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Object instance,String[] values,Integer index,LogMessage.Builder logMessageBuilder) {
					this.instance = instance;
					this.values = values;
					this.index = index;
					this.logMessageBuilder = logMessageBuilder;
				}
				
				@Override
				public Class<?> getFieldType(Class<?> aClass,Field field) {
					return commonUtils.getFieldType(aClass, field);
				}
			}
			
		}
		
	}
}
