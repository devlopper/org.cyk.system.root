package org.cyk.system.root.business.impl.file.report;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessServiceImpl;
import org.cyk.system.root.business.impl.file.report.jasper.JasperReportBusinessImpl;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.utility.common.Constant;

@Singleton
public class ReportManager extends AbstractBusinessServiceImpl implements Serializable {

	private static final long serialVersionUID = -5836159182564631958L;

	private static ReportManager INSTANCE;
	
	private final String reportFileNameFormat = "%s - %s - %s - %s";
	private final String defaultFileExtension = "pdf";
	private final String defaultReportFieldName = "report";
	
	@Inject private FileBusiness fileBusiness;
	@Inject private TimeBusiness timeBusiness;
	@Inject private ApplicationBusiness applicationBusiness;
	@Inject private JasperReportBusinessImpl reportBusiness;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
    /**
     * build a valid system file name
     * @param report
     * @return
     */
    public String buildFileName(AbstractReport<?> report){
    	StringBuilder s = new StringBuilder(String.format(reportFileNameFormat,StringUtils.isNotBlank(report.getOwnerName())?report.getOwnerName():applicationBusiness.findCurrentInstance().getName(),
				report.getTitle(),StringUtils.replace(report.getCreationDate(),Constant.CHARACTER_COLON.toString(),Constant.CHARACTER_H.toString()),report.getCreatedBy()));
    	s = new StringBuilder(StringUtils.remove(s.toString(), Constant.CHARACTER_SLASH.charValue()));
    	s = new StringBuilder(StringUtils.remove(s.toString(), Constant.CHARACTER_BACK_SLASH.charValue()));
    	s = new StringBuilder(StringUtils.remove(s.toString(), Constant.CHARACTER_COLON.charValue()));
		return s.toString();
    }
    
    /**
     * Set if not not set informations like owner name , creation date , created by , file name
     * @param report
     */
    public void prepare(AbstractReport<?> report){
    	//logTrace("Prepare report {}", report);
    	if(StringUtils.isBlank(report.getOwnerName()))
    		report.setOwnerName(applicationBusiness.findCurrentInstance().getName());
    	
    	if(StringUtils.isBlank(report.getCreationDate()))
			report.setCreationDate(timeBusiness.formatDateTime(new Date()));
		
		if(StringUtils.isBlank(report.getCreatedBy()))
			report.setCreatedBy("ANONYMOUS");
		
		if(StringUtils.isBlank(report.getFileExtension()))
			report.setFileExtension(defaultFileExtension);
		
		if(StringUtils.isBlank(report.getTitle()))
			report.setTitle("UNKNOWN_TITLE");
		
		report.setFileName(buildFileName(report));
		logTrace("Report prepared : {}", report);
    }
    
    /**
     * Build the report binary content based on the provided informations
     */
    public <T> ReportBasedOnTemplateFile<T> buildBinaryContent(T reportModel,File template,String fileExtension){
		//logTrace("Create report binary content. existing file?{} , model?{} , template={} , content type={}",file!=null,reportModel!=null,template,fileExtension);
    	ReportBasedOnTemplateFile<T> reportBasedOnTemplateFile = new ReportBasedOnTemplateFile<T>();
    	//reportBasedOnTemplateFile.setTitle(title);
    	reportBasedOnTemplateFile.setFileExtension(StringUtils.isBlank(fileExtension)?defaultFileExtension:fileExtension);
		prepare(reportBasedOnTemplateFile);
		
		reportBasedOnTemplateFile.getDataSource().add(reportModel);
		reportBasedOnTemplateFile.setTemplateFile(template);
		reportBusiness.build(reportBasedOnTemplateFile, Boolean.FALSE);
		logTrace("Report binary content built using model {} and based on template file {}. size in bytes={}",reportModel.getClass().getSimpleName(),template,
				reportBasedOnTemplateFile.getBytes()==null?0l:reportBasedOnTemplateFile.getBytes().length);
		
		return reportBasedOnTemplateFile;
	}
    
    public <T> ReportBasedOnTemplateFile<T> buildBinaryContent(Object object,String reportFieldName,T reportModel,File template,Boolean persist,String fileExtension){
    	ReportBasedOnTemplateFile<T> reportBasedOnTemplateFile = buildBinaryContent(reportModel, template, fileExtension);
		if(Boolean.TRUE.equals(persist)){
			if(reportFieldName==null)
	    		reportFieldName = "report";
	    	Field reportField = commonUtils.getFieldFromClass(object.getClass(), reportFieldName); 
			File file = (File) commonUtils.readField(object, reportField, Boolean.TRUE,Boolean.TRUE);
			//logTrace("Report field {} has been read : {}", reportField,file);
			logTrace("Persist report binary content. field = {}.",reportField);
			persist(file, reportBasedOnTemplateFile);
		}
    	return reportBasedOnTemplateFile;
    }
    
    public <T> ReportBasedOnTemplateFile<T> buildBinaryContent(Object object,T reportModel,File template,Boolean persist,String fileExtension){
    	return buildBinaryContent(object, defaultReportFieldName, reportModel, template, persist, fileExtension);
    }
    
    public <T> ReportBasedOnTemplateFile<T> buildBinaryContent(Object object,T reportModel,File template,Boolean persist){
    	return buildBinaryContent(object, reportModel, template, persist, defaultFileExtension);
    }
    
    public <T> ReportBasedOnTemplateFile<T> buildBinaryContent(File file,String title,String extension){
    	ReportBasedOnTemplateFile<T> reportBasedOnTemplateFile = new ReportBasedOnTemplateFile<>();
    	reportBasedOnTemplateFile.setTitle(title);
    	reportBasedOnTemplateFile.setFileExtension(extension);
    	prepare(reportBasedOnTemplateFile);
    	reportBasedOnTemplateFile.setBytes(file.getBytes());
		logTrace("Report binary content taken from file {}.", file);
		return reportBasedOnTemplateFile;
    }
    
    public <T> ReportBasedOnTemplateFile<T> buildBinaryContent(File file,String title){
    	return buildBinaryContent(file, title, defaultFileExtension);
    }
    
    /**
     * Persist the report to the specific file
     * @param file
     * @param report
     */
    public void persist(File file,AbstractReport<?> report){
    	//logTrace("Persist report {}",report);
		file.setBytes(report.getBytes());
		file.setExtension(report.getFileExtension());
		if(file.getIdentifier()==null){
			fileBusiness.create(file);
			logTrace("Report file {} created",report);
		}else{
			fileBusiness.update(file);
			logTrace("Report file {} updated",report);
		}
	}
        
    /**/
    
    public static ReportManager getInstance() {
		return INSTANCE;
	}
	
}
