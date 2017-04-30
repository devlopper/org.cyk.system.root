package org.cyk.system.root.business.impl.file.report.jasper;


import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

@Getter @Setter
public class DynamicReportJasper<ENTITY> implements Serializable {
 
	private static final long serialVersionUID = -3762610670439880435L;

	private String title,subTitle;
	private Collection<ReportColumnJasper> columns = new ArrayList<>();
	private Collection<ENTITY> collection;
	
	public DynamicReportJasper(String title, String subTitle,Collection<ReportColumnJasper> columns,Collection<ENTITY> collection) {
		super();
		this.title = title;
		this.subTitle = subTitle;
		this.columns = columns;
		this.collection = collection;
	}    
	
	public JasperPrint build() throws ColumnBuilderException, JRException, ClassNotFoundException {
        Style headerStyle = createHeaderStyle();
        Style detailTextStyle = createDetailTextStyle();        
        Style detailNumberStyle = createDetailNumberStyle();        
        DynamicReport dynaReport = build(headerStyle, detailTextStyle,detailNumberStyle);
        JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(), new JRBeanCollectionDataSource(collection));
        return jasperPrint;
    }
    
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
    
    private DynamicReport build(Style headerStyle, Style detailTextStyle, Style detailNumStyle) throws ColumnBuilderException, ClassNotFoundException {
        DynamicReportBuilder report=new DynamicReportBuilder();
        for(ReportColumnJasper column : columns){
        	AbstractColumn c = ColumnBuilder.getNew()
                    .setColumnProperty(column.getFieldName(), column.getType().getName()).setTitle(column.getTitle()).setWidth(column.getWidth())
                    .setStyle(column.getDetailStyle()).setHeaderStyle(column.getHeaderStyle()).build();
        	report.addColumn(c);
        }
        	         
        StyleBuilder titleStyle=new StyleBuilder(true);
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(new Font(20, Font._FONT_GEORGIA, true));
        
        StyleBuilder subTitleStyle=new StyleBuilder(true);
        subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        subTitleStyle.setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true));
        
        report.setTitle(title);
        report.setTitleStyle(titleStyle.build());
        report.setSubtitle(subTitle);
        report.setSubtitleStyle(subTitleStyle.build());
        report.setUseFullPageWidth(true); 
        return report.build();
    }

	
}