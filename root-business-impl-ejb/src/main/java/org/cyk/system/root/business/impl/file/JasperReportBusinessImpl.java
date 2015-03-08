package org.cyk.system.root.business.impl.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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

import org.apache.commons.io.FileUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.ReportBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.Report;
import org.cyk.system.root.model.file.report.ReportTable;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.ReportColumn;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

public class JasperReportBusinessImpl implements ReportBusiness<Style> , Serializable {

	private static final long serialVersionUID = -3596293198479369047L;

	public static final Set<AbstractReportConfiguration<Object, AbstractReport<?>>> REPORTS = new HashSet<>();
	
	@Inject protected FileBusiness fileBusiness;
	@Inject protected LanguageBusiness languageBusiness;
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected GenericBusiness genericBusiness;
	
	@SuppressWarnings("unchecked")
	@Override
	public <MODEL, REPORT extends AbstractReport<?>> void registerConfiguration(AbstractReportConfiguration<MODEL, REPORT> configuration) {
		REPORTS.add((AbstractReportConfiguration<Object, AbstractReport<?>>) configuration);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <MODEL, REPORT extends AbstractReport<?>> AbstractReportConfiguration<MODEL, REPORT> findConfiguration(String identifier) {
		for(AbstractReportConfiguration<?,?> r : REPORTS)
			if(r.getIdentifier().equals(identifier))
				return (AbstractReportConfiguration<MODEL, REPORT>) r;
		return null;
	}
	
	@Override
	public void build(Report<?> aReport, Boolean print) {
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(aReport.getDataSource());
		InputStream inputStream = fileBusiness.findInputStream(aReport.getTemplateFile());
		try {
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
			__build__(aReport, jasperPrint, print);
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void buildTable(ReportTable<?> aReport,Boolean print) {
		
		DynamicReportBuilder report=new DynamicReportBuilder();
        for(Column column : aReport.getColumns()){
        	AbstractColumn c = ColumnBuilder.getNew()
                    .setColumnProperty(column.getFieldName(), column.getType().getName()).setTitle(column.getTitle()).setWidth(column.getWidth())
                    .setStyle(buildStyle(column.getDetailStyle())).setHeaderStyle(buildStyle(column.getHeaderStyle())).build();
        	report.addColumn(c);
        }
        StyleBuilder titleStyle=new StyleBuilder(true);
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(new Font(20, Font._FONT_GEORGIA, true));
        
        StyleBuilder subTitleStyle=new StyleBuilder(true);
        subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        subTitleStyle.setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true));
        
        report.setTitle(aReport.getTitle());
        report.setTitleStyle(titleStyle.build());
        report.setSubtitle(aReport.getSubTitle());
        report.setSubtitleStyle(subTitleStyle.build());
        report.setUseFullPageWidth(true); 
		
		try {
			JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(report.build(), new ClassicLayoutManager(),new JRBeanCollectionDataSource(aReport.getDataSource()));
			__build__(aReport, jasperPrint, print);
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <MODEL> ReportTable<MODEL> buildTable(Class<MODEL> aClass,String fileExtension, Boolean print) {
		Collection<MODEL> datas = new ArrayList<>();
		if(AbstractEnumeration.class.isAssignableFrom(aClass))
			for(AbstractIdentifiable identifiable : genericBusiness.use((Class<? extends AbstractIdentifiable>) aClass).find().all())
				datas.add((MODEL) identifiable);
		else
			ExceptionUtils.getInstance().exception("data.collection");
		return buildTable(aClass,datas,fileExtension, print);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <MODEL> ReportTable<MODEL> buildTable(Class<MODEL> aClass,Collection<MODEL> datas,String fileExtension,Boolean print) {
		BusinessEntityInfos businessEntityInfos = applicationBusiness.findBusinessEntityInfos((Class<AbstractIdentifiable>) aClass);
		ReportTable<MODEL> report = new ReportTable<MODEL>();
		report.setTitle(languageBusiness.findText("report.datatable.title", new Object[]{businessEntityInfos.getUiLabel()}));
		report.setFileName(report.getTitle());
		report.setFileExtension(fileExtension);
		report.setColumns(findColumns(aClass));
		//Collection<IDENTIFIABLE> collection = (Collection<IDENTIFIABLE>) genericBusiness.use(aClass).find().all();
		if(datas==null)
			;
		else
			report.getDataSource().addAll(datas); 
		buildTable(report,print);
		return report;
	}
	
	@Override
	public void write(java.io.File directory,AbstractReport<?> aReport) {
		try {
			FileUtils.writeByteArrayToFile(new java.io.File(directory,aReport.getFileName()+"."+aReport.getFileExtension()), aReport.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**/
	
	@Override
	public Collection<Column> findColumns(Class<?> aClass) {
		Collection<Column> columns = new ArrayList<>();
		Collection<Class<? extends Annotation>> filters = new ArrayList<>();
    	filters.add(Input.class);
    	filters.add(ReportColumn.class);
    	for(Field field : CommonUtils.getInstance().getAllFields(aClass, filters)){
    		columns.add(new Column(field.getName(),field.getType(),languageBusiness.findFieldLabelText(field)) );
    	}
		return columns;
	}
	
	@Override
	public Style buildStyle(org.cyk.system.root.model.userinterface.style.Style style) {
		StyleBuilder styleBuilder=new StyleBuilder(true);
		Font font;
		switch (style.getFont().getName()) {
		case VERDANA:
			switch(style.getFont().getStyle()){
			case BOLD:font = Font.VERDANA_MEDIUM_BOLD;break;
			default:font = Font.VERDANA_MEDIUM_BOLD;
			}
			break;
		default:font = Font.VERDANA_MEDIUM_BOLD;break;
		}
		styleBuilder.setFont(font);
		
		Border border;
		switch (style.getBorder().getStyle()) {
		case DOTTED:border = Border.DOTTED();break;
		case SOLID:border = Border.PEN_1_POINT();break;
		default:border = Border.PEN_1_POINT();break;
		}
		styleBuilder.setBorder(border);
		
        return styleBuilder.build();
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
	
}
