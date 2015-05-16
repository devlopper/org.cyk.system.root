package org.cyk.system.root.business.impl.file.report.jasper;

import java.awt.Color;
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
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.report.JasperReportBusiness;
import org.cyk.system.root.business.api.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.file.report.AbstractReportBusinessImpl;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.system.root.model.userinterface.style.Border.Side;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.ReportColumn;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

public class JasperReportBusinessImpl extends AbstractReportBusinessImpl<Style> implements JasperReportBusiness , Serializable {

	private static final long serialVersionUID = -3596293198479369047L;

	public static final Set<AbstractReportConfiguration<Object, AbstractReport<?>>> REPORTS = new HashSet<>();
	
	@Inject protected FileBusiness fileBusiness;
	@Inject protected LanguageBusiness languageBusiness;
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected GenericBusiness genericBusiness;
	
	@Override
	public <MODEL> ReportBasedOnDynamicBuilder<MODEL> build(JasperReportBasedOnDynamicBuilderParameters<MODEL> parameters) {
		return null;
	}
	
	@Override
	protected <MODEL> void __build__(ReportBasedOnDynamicBuilderParameters<MODEL> parameters) {
		DynamicReportBuilder reportBuilder = new DynamicReportBuilder();
		//reportBuilder.addImageBanner("h:/ss.png", 100, 100, ImageBanner.ALIGN_LEFT);
		
		for(ReportBasedOnDynamicBuilderListener listener : parameters.getReportBasedOnDynamicBuilderListeners())
			listener.report(parameters.getReport());
		for(Column column : parameters.getReport().getColumns()){
			for(ReportBasedOnDynamicBuilderListener listener : parameters.getReportBasedOnDynamicBuilderListeners())
				listener.column(parameters.getReport(), column);
        	AbstractColumn c = ColumnBuilder.getNew()
                    .setColumnProperty(column.getFieldName(), column.getType().getName()).setTitle(column.getTitle())//.setWidth(column.getWidth())
                    .setHeaderStyle(buildStyle(column.getHeaderStyle()))
                    .setStyle(buildStyle(column.getDetailStyle())).setHeaderStyle(buildStyle(column.getHeaderStyle()))
                    .build();
        	
        	reportBuilder.addColumn(c);
        }
        
        StyleBuilder titleStyle=new StyleBuilder(true);
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(new Font(20, Font._FONT_GEORGIA, true));
        
        StyleBuilder subTitleStyle=new StyleBuilder(true);
        subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        subTitleStyle.setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true));
        
        reportBuilder.setTitle(parameters.getReport().getTitle());
        reportBuilder.setTitleStyle(titleStyle.build());
        reportBuilder.setSubtitle(parameters.getReport().getSubTitle());
        reportBuilder.setSubtitleStyle(subTitleStyle.build());
        reportBuilder.setUseFullPageWidth(true); 
		
		try {
			DynamicReport dynamicReport = reportBuilder.build();
			JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager()
			,new JRBeanCollectionDataSource(parameters.getReport().getDataSource()));
			__build__(parameters.getReport(), jasperPrint, parameters.getPrint());
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
	
	/* Builders */
	

	
	@Override
	public void build(ReportBasedOnTemplateFile<?> aReport, Boolean print) {
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
	/*
	@Override
	public void build(ReportBasedOnDynamicBuilder<?> aReport,Boolean print,ReportBasedOnDynamicBuilderListener listener) {
		
		DynamicReportBuilder reportBuilder = new DynamicReportBuilder();
		
		reportBuilder.addImageBanner("h:/ss.png", 100, 100, ImageBanner.ALIGN_LEFT);
		
		listener.report(aReport);
		for(Column column : aReport.getColumns()){
        	listener.column(aReport, column);
        	AbstractColumn c = ColumnBuilder.getNew()
                    .setColumnProperty(column.getFieldName(), column.getType().getName()).setTitle(column.getTitle()).setWidth(column.getWidth())
                    .setHeaderStyle(buildStyle(column.getHeaderStyle()))
                    .setStyle(buildStyle(column.getDetailStyle())).setHeaderStyle(buildStyle(column.getHeaderStyle()))
                    .build();
        	
        	reportBuilder.addColumn(c);
        }
        StyleBuilder titleStyle=new StyleBuilder(true);
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(new Font(20, Font._FONT_GEORGIA, true));
        
        StyleBuilder subTitleStyle=new StyleBuilder(true);
        subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        subTitleStyle.setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true));
        
        reportBuilder.setTitle(aReport.getTitle());
        reportBuilder.setTitleStyle(titleStyle.build());
        reportBuilder.setSubtitle(aReport.getSubTitle());
        reportBuilder.setSubtitleStyle(subTitleStyle.build());
        reportBuilder.setUseFullPageWidth(true); 
		
		try {
			JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(reportBuilder.build(), new ClassicLayoutManager(),new JRBeanCollectionDataSource(aReport.getDataSource()));
			__build__(aReport, jasperPrint, print);
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
	/*
	@Override
	public void buildTable(ReportBasedOnDynamicBuilder<?> aReport,Boolean print) {
		build(aReport, print, new DefaultReportBasedOnDynamicBuilderAdapter());
	}
	*/
	 /*
	@SuppressWarnings("unchecked")
	@Override
	public <MODEL> ReportBasedOnDynamicBuilder<MODEL> build(Class<MODEL> aClass,String fileExtension, Boolean print,ReportBasedOnDynamicBuilderListener listener) {
		Collection<MODEL> datas = new ArrayList<>();
		if(AbstractEnumeration.class.isAssignableFrom(aClass))
			for(AbstractIdentifiable identifiable : genericBusiness.use((Class<? extends AbstractIdentifiable>) aClass).find().all())
				datas.add((MODEL) identifiable);
		else
			ExceptionUtils.getInstance().exception("data.collection");
		return build(aClass,datas,fileExtension, print,listener);
		
	}
	@Override
	public <MODEL> ReportBasedOnDynamicBuilder<MODEL> build(Class<MODEL> aClass,String fileExtension, Boolean print) {
		return build(aClass, fileExtension, print, new DefaultReportBasedOnDynamicBuilderAdapter());
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <MODEL> ReportBasedOnDynamicBuilder<MODEL> build(Class<MODEL> aClass,Collection<MODEL> datas,String fileExtension,Boolean print,ReportBasedOnDynamicBuilderListener listener) {
		BusinessEntityInfos businessEntityInfos = applicationBusiness.findBusinessEntityInfos((Class<AbstractIdentifiable>) aClass);
		ReportBasedOnDynamicBuilder<MODEL> report = new ReportBasedOnDynamicBuilder<MODEL>();
		report.setTitle(languageBusiness.findText("report.datatable.title", new Object[]{businessEntityInfos==null?"TITLE":languageBusiness.findText(businessEntityInfos.getUiLabelId())}));
		report.setFileName(report.getTitle());
		report.setFileExtension(fileExtension);
		report.setColumns(findColumns(aClass));
		//Collection<IDENTIFIABLE> collection = (Collection<IDENTIFIABLE>) genericBusiness.use(aClass).find().all();
		if(datas==null)
			;
		else
			report.getDataSource().addAll(datas); 
		build(report,print,listener);
		return report;
	}
	
	@Override
	public <MODEL> ReportBasedOnDynamicBuilder<MODEL> build(Class<MODEL> aClass,Collection<MODEL> datas,String fileExtension,Boolean print) {
		return build(aClass,datas,fileExtension,print,new DefaultReportBasedOnDynamicBuilderAdapter());
	}
	*/
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
		
		if(style.getBorder().getAll()!=null && !style.getBorder().getAll().getStyle().equals(org.cyk.system.root.model.userinterface.style.Border.Side.Style.NONE)){
			Border border;
			switch (style.getBorder().getAll().getStyle()) {
			case DOTTED:border = Border.DOTTED();break;
			case SOLID:border = Border.PEN_1_POINT();break;
			default:border = Border.PEN_1_POINT();break;
			}
			styleBuilder.setBorder(border);
			styleBuilder.setBorderColor(style.getBorder().getAll().getColor().systemColor());
		}
		borderSide(styleBuilder,style.getBorder().getLeft(),0);
		borderSide(styleBuilder,style.getBorder().getTop(),1);
		borderSide(styleBuilder,style.getBorder().getRight(),2);
		borderSide(styleBuilder,style.getBorder().getBottom(),3);
		
		Color color = style.getBackground().getColor().systemColor();
		if(color==null){
			styleBuilder.setTransparency(Transparency.TRANSPARENT);
		}else{
			styleBuilder.setTransparency(Transparency.OPAQUE);
			styleBuilder.setBackgroundColor(color);
		}
		
		styleBuilder.setTextColor(style.getText().getColor().systemColor());
		switch(style.getText().getAlignment().getHorizontal()){
		case LEFT:styleBuilder.setHorizontalAlign(HorizontalAlign.LEFT);break;
		case MIDDLE:styleBuilder.setHorizontalAlign(HorizontalAlign.CENTER);break;
		case RIGHT:styleBuilder.setHorizontalAlign(HorizontalAlign.RIGHT);break;
		case JUSTIFY:styleBuilder.setHorizontalAlign(HorizontalAlign.JUSTIFY);break;
		}
		switch(style.getText().getAlignment().getVertical()){
		case TOP:styleBuilder.setVerticalAlign(VerticalAlign.TOP);break;
		case MIDDLE:styleBuilder.setVerticalAlign(VerticalAlign.MIDDLE);break;
		case BOTTOM:styleBuilder.setVerticalAlign(VerticalAlign.BOTTOM);break;
		case JUSTIFY:styleBuilder.setVerticalAlign(VerticalAlign.JUSTIFIED);break;
		}
		
        return styleBuilder.build();
	}
	
	private void borderSide(StyleBuilder styleBuilder,Side side,Integer index){
		if(side==null){
			
		}else{
			Border border;
			if(side.getSize()==1)
				border = Border.PEN_1_POINT();
			else if(side.getSize()==2)
				border = Border.PEN_2_POINT();
			else if(side.getSize()==4)
				border = Border.PEN_4_POINT();
			else
				border = Border.PEN_1_POINT();
			if(index==0)
				styleBuilder.setBorderLeft(border);
			else if(index==1)
				styleBuilder.setBorderTop(border);
			else if(index==2)
				styleBuilder.setBorderRight(border);
			else if(index==3)
				styleBuilder.setBorderBottom(border);
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
	
}
