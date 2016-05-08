package org.cyk.system.root.business.impl.file.report.jasper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXmlExporterOutput;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.report.JasperReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.business.api.file.report.JasperReportBusiness;
import org.cyk.system.root.business.api.markuplanguage.MarkupLanguageBusiness.UpdateTagArguments;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.file.report.AbstractReportBusinessImpl;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.BeanAdapter;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;

public class JasperReportBusinessImpl extends AbstractReportBusinessImpl implements JasperReportBusiness , Serializable {

	private static final long serialVersionUID = -3596293198479369047L;

	public static final Set<AbstractReportConfiguration<Object, AbstractReport<?>>> REPORTS = new HashSet<>();
	 
	@Inject private FileBusiness fileBusiness;
	
	@Override
	public void build(final ReportBasedOnTemplateFile<?> aReport, Boolean print) {
		JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(aReport.getDataSource());
		InputStream inputStream = fileBusiness.findInputStream(aReport.getTemplateFile());
		try {
			final StringBuilder jrxmlBuilder = new StringBuilder(IOUtils.toString(inputStream));
			
			listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.ResultMethod<Listener, String>() {
				@Override
				public String execute(Listener listener) {
					jrxmlBuilder.delete(0, jrxmlBuilder.length());
					jrxmlBuilder.append(listener.processJrxml(aReport, jrxmlBuilder.toString()));
					return jrxmlBuilder.toString();
				}
			});
			/*
			for(Listener listener : Listener.COLLECTION)
				if(Boolean.TRUE.equals(listener.isJrxmlProcessable(aReport)))
					jrxml = listener.processJrxml(aReport,jrxml);
			*/
			
			final ByteArrayInputStream bais = new ByteArrayInputStream(jrxmlBuilder.toString().getBytes(Constant.ENCODING_UTF8));
			listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
				@Override
				public void execute(Listener listener) {
					listener.processInputStream(aReport,bais);
				}
			});
			
			
			JasperDesign jasperDesign = JRXmlLoader.load(bais);
			for(Listener listener : Listener.COLLECTION)
				listener.processDesign(aReport,jasperDesign);
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			for(Listener listener : Listener.COLLECTION)
				listener.processReport(aReport,jasperReport);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, datasource);
			for(Listener listener : Listener.COLLECTION)
				listener.processPrint(aReport,jasperPrint);
		
			if(aReport.getWidth()!=null)
				jasperPrint.setPageWidth(aReport.getWidth());
			if(aReport.getHeight()!=null)
				jasperPrint.setPageHeight(aReport.getHeight());
			__build__(aReport, jasperPrint, print);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
		
	@Override
	protected <MODEL> void __build__(ReportBasedOnDynamicBuilderParameters<MODEL> parameters) {	
		DynamicReportBuilder reportBuilder = new DynamicReportBuilder();
		notifyReportStartBuilding(parameters);
		for(ReportBasedOnDynamicBuilderListener listener : parameters.getReportBasedOnDynamicBuilderListeners())
			if(listener instanceof JasperReportBasedOnDynamicBuilderListener)
				((JasperReportBasedOnDynamicBuilderListener)listener).report(reportBuilder, parameters.getReport());
		
		for(Column column : parameters.getReport().getColumns()){
			ColumnBuilder columnBuilder = ColumnBuilder.getNew();
			columnBuilder.setColumnProperty(column.getFieldName(), column.getType().getName());
			
			notifyColumnStartBuilding(parameters, column);
			for(ReportBasedOnDynamicBuilderListener listener : parameters.getReportBasedOnDynamicBuilderListeners())
				if(listener instanceof JasperReportBasedOnDynamicBuilderListener)
					((JasperReportBasedOnDynamicBuilderListener)listener).column(reportBuilder, parameters.getReport(), columnBuilder, column);
			
        	reportBuilder.addColumn(columnBuilder.build());
        }
      
		try {	
			JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(reportBuilder.build(), new ClassicLayoutManager()
			,new JRBeanCollectionDataSource(parameters.getReport().getDataSource()));
			__build__(parameters.getReport(), jasperPrint, parameters.getPrint());
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
		
	/**/
	
	private void __build__(AbstractReport<?> aReport, JasperPrint jasperPrint,Boolean print) {
		try {
			if(aReport.getFileExtension()==null)
				aReport.setFileExtension("xml");
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			if("pdf".equalsIgnoreCase(aReport.getFileExtension())){
				JRPdfExporter exporter = new JRPdfExporter();
				
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
				
				SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
				exporter.setConfiguration(configuration);
				if(Boolean.TRUE.equals(print))
					configuration.setPdfJavaScript("this.print();");
				
				exporter.exportReport();
				
			}else if("xls".equalsIgnoreCase(aReport.getFileExtension())){
				JRXlsExporter exporter = new JRXlsExporter();
				
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
				
				SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
				exporter.setConfiguration(configuration);
				
				configuration.setOnePagePerSheet(true);
				configuration.setDetectCellType(true);
				configuration.setCollapseRowSpan(false);
								
				exporter.exportReport();
			}else if("xml".equalsIgnoreCase(aReport.getFileExtension())){
				JRXmlExporter exporter = new JRXmlExporter();
				
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleXmlExporterOutput(baos));
				
				exporter.exportReport();
			}else
				ExceptionUtils.getInstance().exception("report.extension.missing");
			
			aReport.setBytes(baos.toByteArray());
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**/
	
	public static interface Listener{
		String NAMESPACE_COMPONENT = "http://jasperreports.sourceforge.net/jasperreports/components";
		
		String WIDTH = "width";
		String DETAIL = "detail";
		String BAND = "band";
		String FRAME = "frame";
		String COMPONENT_ELEMENT = "componentElement";
		String TABLE = "table";
		String COLUMN = "column";
		String COLUMN_HEADER = "columnHeader";
		String TEXT_FIELD = "textField";
		String REPORT_ELEMENT = "reportElement";
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		Boolean isJrxmlProcessable(ReportBasedOnTemplateFile<?> aReport);
		void processPrint(ReportBasedOnTemplateFile<?> aReport,JasperPrint jasperPrint);
		void processReport(ReportBasedOnTemplateFile<?> aReport,JasperReport jasperReport);
		void processDesign(ReportBasedOnTemplateFile<?> aReport,JasperDesign jasperDesign);
		void processInputStream(ReportBasedOnTemplateFile<?> aReport,ByteArrayInputStream bais);
		String processJrxml(ReportBasedOnTemplateFile<?> aReport,String jrxml);
		
		/**/
		
		public static class Adapter extends BeanAdapter implements Listener,Serializable{
			private static final long serialVersionUID = -9048282379616583423L;
			
			@Override
			public Boolean isJrxmlProcessable(ReportBasedOnTemplateFile<?> aReport) {
				return null;
			}
			
			@Override
			public String processJrxml(ReportBasedOnTemplateFile<?> aReport,String jrxml) {
				return null;
			}
			
			@Override public void processDesign(ReportBasedOnTemplateFile<?> aReport,JasperDesign jasperDesign) {}
			@Override public void processInputStream(ReportBasedOnTemplateFile<?> aReport,ByteArrayInputStream bais) {}
			@Override public void processPrint(ReportBasedOnTemplateFile<?> aReport,JasperPrint jasperPrint) {}
			@Override public void processReport(ReportBasedOnTemplateFile<?> aReport,JasperReport jasperReport) {}
			
			/**/
			
			protected String updateTableColumn(String jrxml,Object[] parentTags,Integer tableIndex,Integer columnIndex,String[] attributes){
				UpdateTagArguments updateTagArguments = new UpdateTagArguments();
				for(int i=0;i<parentTags.length;i=i+2)
					updateTagArguments.getFindTagArguments().addTag((String)parentTags[i],(Integer)parentTags[i+1]);
				
				updateTagArguments.getFindTagArguments().addTag(TABLE,NAMESPACE_COMPONENT,tableIndex).addTag(COLUMN,NAMESPACE_COMPONENT,columnIndex);
    			updateTagArguments.setAttributes(attributes); 
    			jrxml = RootBusinessLayer.getInstance().getMarkupLanguageBusiness().updateTag(jrxml, updateTagArguments);
    			if(ArrayUtils.contains(attributes, WIDTH)){
    				UpdateTagArguments textFieldUpdateTagArguments = new UpdateTagArguments();
    				textFieldUpdateTagArguments.setFindTagArguments(updateTagArguments.getFindTagArguments());
    				textFieldUpdateTagArguments.getFindTagArguments().addTag(COLUMN_HEADER,NAMESPACE_COMPONENT,0).addTag(TEXT_FIELD, 0).addTag(REPORT_ELEMENT, 0);
    				textFieldUpdateTagArguments.setAttributes(WIDTH,updateTagArguments.getAttributes().get(WIDTH));
    				jrxml = RootBusinessLayer.getInstance().getMarkupLanguageBusiness().updateTag(jrxml, textFieldUpdateTagArguments);
    			}
    			return jrxml;
			}
			
			/**/
			
			public static class Default extends Adapter implements Serializable{
				private static final long serialVersionUID = 2884910167320359611L;
				
			}
		}
	}
	
}
