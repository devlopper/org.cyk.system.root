package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.report.RootReportProducer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.computation.DataReadConfiguration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public interface TypedBusiness<IDENTIFIABLE extends AbstractIdentifiable> extends IdentifiableBusinessService<IDENTIFIABLE, Long> {

	/* predefined query  */
	
    IDENTIFIABLE load(Long identifier);
    
    void load(IDENTIFIABLE identifiable);
    void load(Collection<IDENTIFIABLE> identifiables);
    
    IDENTIFIABLE delete(String code);
    Collection<IDENTIFIABLE> delete(Set<String> codes);
    
    Collection<IDENTIFIABLE> instanciateMany(List<String[]> list);
    
    IDENTIFIABLE instanciateOneRandomly();
    IDENTIFIABLE instanciateOneRandomly(String code);
    Collection<IDENTIFIABLE> instanciateManyRandomly(Integer count);
    Collection<IDENTIFIABLE> instanciateManyRandomly(Set<String> codes);
    
    Collection<IDENTIFIABLE> findAll(); 
    Long countAll();
    
    Collection<IDENTIFIABLE> findAll(DataReadConfiguration configuration); 
    Long countAll(DataReadConfiguration configuration);
    
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
	File findReportFile(IDENTIFIABLE identifiable,ReportTemplate reportTemplate,Boolean createIfNull);
	File findReportFile(IDENTIFIABLE identifiable,String reportTemplateCode,Boolean createIfNull);
	
	@Getter @Setter @AllArgsConstructor
	public static class CreateReportFileArguments<IDENTIFIABLE extends AbstractIdentifiable> implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private ReportTemplate reportTemplate;
		private IDENTIFIABLE identifiable;
		private File file,backgroundImageFile;
		private RootReportProducer reportProducer;
		private Boolean joinFileToIdentifiable = Boolean.TRUE,isDraft=Boolean.FALSE;
		private String identifiableName;
		/**/
		
		public CreateReportFileArguments(IDENTIFIABLE identifiable){
			this.identifiable = identifiable;
		}
		
		/**/
		
		@SuppressWarnings("rawtypes")
		public static class Builder<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBuilder<CreateReportFileArguments> {
			private static final long serialVersionUID = 1L;

			private IDENTIFIABLE identifiable;
			
			private String reportTemplateCode;
			private ReportTemplate reportTemplate;
			private File file,backgroundImageFile;
			private RootReportProducer reportProducer;
			private Boolean joinFileToIdentifiable,isDraft = Boolean.FALSE,updateExisting=Boolean.TRUE;
			
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
}
