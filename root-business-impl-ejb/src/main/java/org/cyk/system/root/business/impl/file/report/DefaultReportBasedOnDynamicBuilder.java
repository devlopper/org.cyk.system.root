package org.cyk.system.root.business.impl.file.report;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.userinterface.style.Border.Side;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.ReportColumn;
import org.cyk.utility.common.annotation.user.interfaces.style.Alignment.Horizontal;
import org.cyk.utility.common.annotation.user.interfaces.style.Alignment.Vertical;

public class DefaultReportBasedOnDynamicBuilder extends AbstractReportBasedOnDynamicBuilder implements Serializable {

	private static final long serialVersionUID = -6397313866653430863L;

	@SuppressWarnings("unchecked")
	@Override
	public void report(ReportBasedOnDynamicBuilder<?> report,ReportBasedOnDynamicBuilderParameters<?> parameters) {
		if(StringUtils.isBlank(report.getTitle()))
			if(StringUtils.isBlank(parameters.getTitle())){
				//LanguageBusiness languageBusiness = RootBusinessLayer.getInstance().getLanguageBusiness();
				//ApplicationBusiness applicationBusiness = RootBusinessLayer.getInstance().getApplicationBusiness();
				BusinessEntityInfos businessEntityInfos = null;
				if(parameters.getIdentifiableClass()!=null)
					businessEntityInfos = applicationBusiness.findBusinessEntityInfos((Class<AbstractIdentifiable>) parameters.getIdentifiableClass());
				
				StringBuilder titleBuilder = new StringBuilder(businessEntityInfos==null?"TITLE":languageBusiness.findText(businessEntityInfos.getUserInterface().getLabelId()));
				if(parameters.getExtendedParameterMap()!=null){
					String[] paramValues = null;
					Date fromDate = null;
					paramValues = parameters.getExtendedParameterMap().get(rootBusinessLayer.getParameterFromDate());
					if(paramValues!= null && StringUtils.isNotBlank(paramValues[0]))
						fromDate = new Date(Long.parseLong(paramValues[0]));
					
					Date toDate = null;
					paramValues = parameters.getExtendedParameterMap().get(rootBusinessLayer.getParameterToDate());
					if(paramValues!= null && StringUtils.isNotBlank(paramValues[0]))
						toDate = new Date(Long.parseLong(paramValues[0]));
					
					if(fromDate!=null){
						String pattern = TimeBusiness.DATE_SHORT_PATTERN;
						titleBuilder.append(Constant.CHARACTER_SPACE+inject(TimeBusiness.class).formatPeriodFromTo(new Period(fromDate, toDate), pattern));
					}
				}
				
				report.setTitle(titleBuilder.toString());
				
				
			}else
				report.setTitle(parameters.getTitle());
		
		if(StringUtils.isBlank(report.getSubTitle()))
			if(StringUtils.isBlank(parameters.getSubTitle())){
				
			}else
				report.setSubTitle(parameters.getTitle());
		
		if(StringUtils.isBlank(report.getCreationDate()))
			if(StringUtils.isBlank(parameters.getCreationDate())){
				;
			}else
				report.setCreationDate(parameters.getCreationDate());
		
		if(StringUtils.isBlank(report.getCreatedBy()))
			report.setCreatedBy(parameters.getCreatedBy());
		
		if(parameters.getOwner()!=null){
			report.setOwnerName(parameters.getOwner().getGlobalIdentifier().getName());
			if(parameters.getOwner().getImage()!=null)
				report.setOwnerLogoPath(inject(FileBusiness.class).findSystemPath(parameters.getOwner().getImage()).toString());
		}
		
		//File ownerNameImageFile = rootBusinessLayer.getFileBusiness()
		//		.process(rootBusinessLayer.getGraphicBusiness().createFromText(report.getOwnerName()), "name.png");
		//report.setOwnerNameImagePath(rootBusinessLayer.getFileBusiness().findSystemPath(ownerNameImageFile).toString());
		//report.setOwnerContacts(parameters.getOwner().getContactCollection().getUiString());
		
		//report.setFileName(rootBusinessLayer.buildReportFileName(report));
		//report.setFileName((StringUtils.isNotBlank(report.getOwnerName())?report.getOwnerName()+" - ":"")+report.getTitle()+" - "+StringUtils.replace(report.getCreationDate(),":","H")+" - "+report.getCreatedBy());
		
		report.setFileExtension(parameters.getFileExtension());
		
		reportBusiness.prepare(report);
	}

	@Override
	public void column(ReportBasedOnDynamicBuilder<?> report, Column column) {
		column.getHeaderStyle().getBackground().getColor().setHexademicalCode(COLUMN_HEADER_BACKGROUND_COLOR);
		column.getHeaderStyle().getBorder().setBottom(new Side());
		column.getHeaderStyle().getBorder().getBottom().setSize(2);
		
		column.getDetailStyle().getBackground().getColor().setHexademicalCode(COLUMN_DETAIL_BACKGROUND_COLOR);
		column.getDetailStyle().getBorder().setAll(null);
		
		column.getFooterStyle().getBackground().getColor().setHexademicalCode(COLUMN_FOOTER_BACKGROUND_COLOR);
		
		ReportColumn reportColumn = column.getField().getAnnotation(ReportColumn.class);
		
		Horizontal horizontalAlignment = reportColumn == null? Horizontal.AUTO:reportColumn.style().alignment().horizontal();
		Vertical verticalAlignment = reportColumn == null? Vertical.AUTO:reportColumn.style().alignment().vertical();
		if(Horizontal.AUTO.equals(horizontalAlignment)){
			if(CommonUtils.getInstance().isNumberClass(column.getType()))
				horizontalAlignment = Horizontal.RIGHT;
			else
				horizontalAlignment = Horizontal.LEFT;
		}
		if(Vertical.AUTO.equals(verticalAlignment))
			verticalAlignment = Vertical.MIDDLE;
		
		column.getHeaderStyle().getText().getAlignment().setHorizontal(horizontalAlignment);
		column.getDetailStyle().getText().getAlignment().setHorizontal(horizontalAlignment);
		column.getFooterStyle().getText().getAlignment().setHorizontal(horizontalAlignment);
	}

	
	

}
