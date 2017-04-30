package org.cyk.system.root.business.impl.file.report.jasper;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.file.report.AbstractReportBusinessImpl;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;

import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;

public class DefaultJasperReportBasedOnDynamicBuilder extends AbstractJasperReportBasedOnDynamicBuilder implements Serializable {

	private static final long serialVersionUID = -6397313866653430863L;

	@Override
	public void report(DynamicReportBuilder reportBuilder,ReportBasedOnDynamicBuilder<?> report) {
		reportBuilder.setReportLocale(report.getLocale());
		//Header
		if(StringUtils.isNotBlank(report.getOwnerLogoPath()) && Boolean.TRUE.equals(AbstractReportBusinessImpl.SHOW_OWNER_LOGO) )
			reportBuilder.addImageBanner(report.getOwnerLogoPath(), 40, 40, ImageBanner.ALIGN_LEFT);
		if(StringUtils.isNotBlank(report.getOwnerName()) && Boolean.TRUE.equals(AbstractReportBusinessImpl.SHOW_OWNER_NAME))
			reportBuilder.addAutoText(report.getOwnerName(), AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT);
		
        //Body
		StyleBuilder titleStyle=new StyleBuilder(true);
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(new Font(14, Font._FONT_GEORGIA, true));
        
        StyleBuilder subTitleStyle=new StyleBuilder(true);
        subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        subTitleStyle.setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true));
        
		reportBuilder.setTitle(report.getTitle());
        reportBuilder.setTitleStyle(titleStyle.build());
        reportBuilder.setSubtitle(report.getSubTitle());
        reportBuilder.setSubtitleStyle(subTitleStyle.build());
        reportBuilder.setUseFullPageWidth(true); 
        
        //Footer
        StyleBuilder footerStyleBuilder=new StyleBuilder(true);
        footerStyleBuilder.setFont(new Font(10, Font._FONT_GEORGIA, true));
        Style footerStyle = footerStyleBuilder.build();
        
        reportBuilder.addAutoText(AutoText.AUTOTEXT_PAGE_X_SLASH_Y,AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT,20,5,footerStyle);
        reportBuilder.addAutoText(report.getTitle(),AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_LEFT,800,footerStyle);
        reportBuilder.addAutoText(report.getCreationDate()+"/"+report.getCreatedBy(),AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_LEFT,800,footerStyle);
	}

	@Override
	public void column(DynamicReportBuilder dynamicReportBuilder,ReportBasedOnDynamicBuilder<?> report, ColumnBuilder columnBuilder,Column column) {
		columnBuilder.setTitle(column.getTitle());
		if(column.getWidth()==null)
			;
		else
			columnBuilder.setWidth(column.getWidth());
		
		columnBuilder
			.setHeaderStyle(buildStyle(column.getHeaderStyle()))
			.setStyle(buildStyle(column.getDetailStyle()))
			;
	}

	@Override
	public void report(ReportBasedOnDynamicBuilder<?> report,ReportBasedOnDynamicBuilderParameters<?> parameters) {
		
	}

	@Override
	public void column(ReportBasedOnDynamicBuilder<?> report, Column column) {
	
	}

	

}
