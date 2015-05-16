package org.cyk.system.root.business.api.file.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.system.root.model.userinterface.style.Style;

public interface ReportBusiness<STYLE> {
    
	<MODEL,REPORT extends AbstractReport<?>> void registerConfiguration(AbstractReportConfiguration<MODEL,REPORT> builder);
	<MODEL,REPORT extends AbstractReport<?>> AbstractReportConfiguration<MODEL,REPORT> findConfiguration(String identifier);
	
	<MODEL> ReportBasedOnDynamicBuilder<MODEL> build(ReportBasedOnDynamicBuilderParameters<MODEL> parameters);
	
	//<MODEL> ReportBasedOnDynamicBuilder<MODEL> build(Class<MODEL> aClass,Collection<MODEL> datas,String fileExtension,Boolean print,ReportBasedOnDynamicBuilderListener listener);
	/*
	<MODEL> ReportBasedOnDynamicBuilder<MODEL> build(Class<MODEL> aClass,Collection<MODEL> datas,String fileExtension,Boolean print);
	
	<MODEL> ReportBasedOnDynamicBuilder<MODEL> build(Class<MODEL> aClass,String fileExtension,Boolean print,ReportBasedOnDynamicBuilderListener listener);
	<MODEL> ReportBasedOnDynamicBuilder<MODEL> build(Class<MODEL> aClass,String fileExtension,Boolean print);
	
	void build(ReportBasedOnDynamicBuilder<?> aReport,Boolean print,ReportBasedOnDynamicBuilderListener listener);
	*/
	void build(ReportBasedOnTemplateFile<?> aReport,Boolean print);
	
	void write(java.io.File directory,AbstractReport<?> aReport);
	
	/**/
	
	Collection<Column> findColumns(Class<?> aClass);
	
	STYLE buildStyle(Style style);
	
	/**/
	
	@Getter @Setter @AllArgsConstructor @NoArgsConstructor
	public static class ReportBasedOnDynamicBuilderParameters<MODEL> implements Serializable{

		private static final long serialVersionUID = -8188598097968268568L;
		
		private ReportBasedOnDynamicBuilder<?> report;
		private Class<MODEL> clazz;
		private Collection<MODEL> datas;
		private String fileExtension;
		private Boolean print;
		private Collection<ReportBasedOnDynamicBuilderListener> reportBasedOnDynamicBuilderListeners = new ArrayList<>();
		
	}
	
}
