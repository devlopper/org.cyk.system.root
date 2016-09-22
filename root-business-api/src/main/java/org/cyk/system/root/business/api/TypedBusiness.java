package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.file.report.RootReportProducer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.computation.DataReadConfiguration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface TypedBusiness<IDENTIFIABLE extends AbstractIdentifiable> extends IdentifiableBusinessService<IDENTIFIABLE, Long> {

	/* predefined query  */
	
    IDENTIFIABLE load(Long identifier);
    
    void load(IDENTIFIABLE identifiable);
    void load(Collection<IDENTIFIABLE> identifiables);
    
    IDENTIFIABLE instanciateOneRandomly();
    Collection<IDENTIFIABLE> instanciateManyRandomly(Integer count);
    
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
    
	File createReportFile(IDENTIFIABLE identifiable,CreateReportFileArguments<IDENTIFIABLE> arguments);
	File findReportFile(IDENTIFIABLE identifiable,ReportTemplate reportTemplate,Boolean createIfNull);
	File findReportFile(IDENTIFIABLE identifiable,String reportTemplateCode,Boolean createIfNull);
	
	@Getter @Setter @AllArgsConstructor @NoArgsConstructor
	public static class CreateReportFileArguments<IDENTIFIABLE extends AbstractIdentifiable> implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private String reportTemplateCode;
		private ReportTemplate reportTemplate;
		private IDENTIFIABLE identifiable;
		private File file;
		private RootReportProducer reportProducer;
		private Boolean joinFileToIdentifiable = Boolean.TRUE;
		
		public CreateReportFileArguments(String reportTemplateCode, IDENTIFIABLE identifiable, File file) {
			super();
			this.reportTemplateCode = reportTemplateCode;
			this.identifiable = identifiable;
			this.file = file;
		}
		
		public CreateReportFileArguments(String reportTemplateCode, IDENTIFIABLE identifiable) {
			this(reportTemplateCode,identifiable,new File());
		}
		
		public CreateReportFileArguments(ReportTemplate reportTemplate, IDENTIFIABLE identifiable, File file) {
			super();
			this.reportTemplate = reportTemplate;
			this.identifiable = identifiable;
			this.file = file;
			if(this.reportTemplate != null)
				reportTemplateCode = reportTemplate.getCode();
		}
		
		public CreateReportFileArguments(ReportTemplate reportTemplate, IDENTIFIABLE identifiable) {
			this(reportTemplate,identifiable,new File());
		}
		
		
	}
	
    //TODO clone service must be implemented using reflection and listener
}
