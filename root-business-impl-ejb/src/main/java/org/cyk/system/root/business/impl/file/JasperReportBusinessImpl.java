package org.cyk.system.root.business.impl.file;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import org.cyk.system.root.business.api.file.ReportBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.Report;
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

	@Inject private LanguageBusiness languageBusiness;
	
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
	public void build(Report<?> aReport,Boolean print) {
		
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
				JRXmlExporter xmlExporter = new JRXmlExporter();
				
				xmlExporter.exportReport();
			}
			
			aReport.setBytes(baos.toByteArray());
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
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
		
		//styleBuilder.setBorderBottom(Border.PEN_2_POINT());
		/*
		styleBuilder.setBorderColor(Color.BLACK);
		styleBuilder.setBackgroundColor(Color.ORANGE);
		styleBuilder.setTextColor(Color.BLACK);
		styleBuilder.setHorizontalAlign(HorizontalAlign.CENTER);
		styleBuilder.setVerticalAlign(VerticalAlign.MIDDLE);
		styleBuilder.setTransparency(Transparency.OPAQUE);       
		*/ 
        return styleBuilder.build();
	}
/*
	public JasperPrint build1(Report<?, ?> aReport) throws ColumnBuilderException, JRException, ClassNotFoundException {
        Style headerStyle = createHeaderStyle();
        Style detailTextStyle = createDetailTextStyle();        
        Style detailNumberStyle = createDetailNumberStyle();        
        DynamicReport dynaReport = build(aReport,headerStyle, detailTextStyle,detailNumberStyle);
        JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(), new JRBeanCollectionDataSource(aReport.getDataSource()));
        return jasperPrint;
    }
  */
	/*
    private Style createHeaderStyle() {        
        StyleBuilder sb=new StyleBuilder(true);
        sb.setFont(Font.VERDANA_MEDIUM_BOLD);
        sb.setBorder(Border.THIN());
        sb.setBorderBottom(Border.PEN_2_POINT());
        sb.setBorderColor(Color.BLACK);
        sb.setBackgroundColor(Color.ORANGE);
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.CENTER);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setTransparency(Transparency.OPAQUE);        
        return sb.build();
    }
    
    private Style createDetailTextStyle(){
        StyleBuilder sb=new StyleBuilder(true);
        sb.setFont(Font.VERDANA_MEDIUM);
        sb.setBorder(Border.DOTTED());        
        sb.setBorderColor(Color.BLACK);        
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.LEFT);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setPaddingLeft(5);        
        return sb.build();
    }
    
    private Style createDetailNumberStyle(){
        StyleBuilder sb=new StyleBuilder(true);
        sb.setFont(Font.VERDANA_MEDIUM);
        sb.setBorder(Border.DOTTED());        
        sb.setBorderColor(Color.BLACK);        
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.RIGHT);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setPaddingRight(5);        
        return sb.build();
    }
    */
    
}
