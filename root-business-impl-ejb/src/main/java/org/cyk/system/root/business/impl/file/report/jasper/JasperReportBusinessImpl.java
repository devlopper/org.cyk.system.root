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
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.report.JasperReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.business.api.file.report.JasperReportBusiness;
import org.cyk.system.root.business.impl.file.report.AbstractReportBusinessImpl;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.utility.common.Constant;
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
	public void build(ReportBasedOnTemplateFile<?> aReport, Boolean print) {
		JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(aReport.getDataSource());
		InputStream inputStream = fileBusiness.findInputStream(aReport.getTemplateFile());
		try {
			String jrxml = IOUtils.toString(inputStream);
			for(Listener listener : Listener.COLLECTION)
				if(Boolean.TRUE.equals(listener.isJrxmlProcessable()))
					jrxml = listener.processJrxml(jrxml);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(jrxml.getBytes(Constant.ENCODING_UTF8));
			for(Listener listener : Listener.COLLECTION)
				listener.processInputStream(bais);
			
			JasperDesign jasperDesign = JRXmlLoader.load(bais);
			for(Listener listener : Listener.COLLECTION)
				listener.processDesign(jasperDesign);
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			for(Listener listener : Listener.COLLECTION)
				listener.processReport(jasperReport);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, datasource);
			for(Listener listener : Listener.COLLECTION)
				listener.processPrint(jasperPrint);
		
			if(aReport.getWidth()!=null)
				jasperPrint.setPageWidth(aReport.getWidth());
			if(aReport.getHeight()!=null)
				jasperPrint.setPageHeight(aReport.getHeight());
			__build__(aReport, jasperPrint, print);
		} catch (Exception e) {
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
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		Boolean isJrxmlProcessable();
		void processPrint(JasperPrint jasperPrint);
		void processReport(JasperReport jasperReport);
		void processDesign(JasperDesign jasperDesign);
		void processInputStream(ByteArrayInputStream bais);
		String processJrxml(String jrxml);
		
		/**/
		
		public static class Adapter extends BeanAdapter implements Listener,Serializable{
			private static final long serialVersionUID = -9048282379616583423L;
			
			@Override
			public Boolean isJrxmlProcessable() {
				return null;
			}
			
			@Override
			public String processJrxml(String jrxml) {
				return null;
			}
			
			@Override public void processDesign(JasperDesign jasperDesign) {}
			@Override public void processInputStream(ByteArrayInputStream bais) {}
			@Override public void processPrint(JasperPrint jasperPrint) {}
			@Override public void processReport(JasperReport jasperReport) {}
			
			/**/
			
			public static class Default extends Adapter implements Serializable{
				private static final long serialVersionUID = 2884910167320359611L;
				
			}
		}
	}
	
}
