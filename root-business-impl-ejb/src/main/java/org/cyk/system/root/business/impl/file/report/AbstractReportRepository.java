package org.cyk.system.root.business.impl.file.report;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderIdentifiableConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.search.DateSearchCriteria;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractReportRepository extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6917567891985885124L;
	
	public static final Collection<ReportRepositoryListener> LISTENERS = new ArrayList<>();
	
	@Inject protected ReportBusiness reportBusiness;
	
	public abstract void build();
	
	protected void addListener(ReportBasedOnDynamicBuilderListener listener){
		ReportBasedOnDynamicBuilderListener.GLOBALS.add(listener);
		logInfo("Global dynamic report builder listener added : {}",listener);
	}
	
	protected void addConfiguration(ReportBasedOnDynamicBuilderIdentifiableConfiguration<AbstractIdentifiable, Object> configuration){
		ReportBasedOnDynamicBuilderListener.IDENTIFIABLE_CONFIGURATIONS.add(configuration);
		logInfo("Dynamic report configuration added. Identifier is {} , Entity is {} , Model is {}"
				,configuration.getReportBasedOnDynamicBuilderIdentifier(),configuration.getIdentifiableClass(),configuration.getModelClass());
	}
	
	protected <MODEL, REPORT extends AbstractReport<?>> void registerConfiguration(AbstractReportConfiguration<MODEL, REPORT> configuration) {
		reportBusiness.registerConfiguration(configuration);
		logInfo("Report configuration added. Identifier is {}",configuration.getIdentifier());
	}
	
	protected String getParameter(String name,ReportBasedOnDynamicBuilderParameters<Object> parameters){
		return parameters.getExtendedParameterMap().get(name)[0];
	}
	protected Date getParameterFromDate(ReportBasedOnDynamicBuilderParameters<Object> parameters){
		String value = getParameter(RootBusinessLayer.getInstance().getParameterFromDate(), parameters);
		if(StringUtils.isBlank(value)){
			Date date = null;
			for(ReportRepositoryListener listener : LISTENERS){
				Date d = listener.getLowestFromDate(parameters);
				if(d!=null)
					date = d;
			}
			if(date==null)
				date = DateSearchCriteria.DATE_MOST_PAST;
			return date;
		}else
			return new Date(Long.parseLong(value));
	}
	protected Date getParameterToDate(ReportBasedOnDynamicBuilderParameters<Object> parameters){
		String value = getParameter(RootBusinessLayer.getInstance().getParameterToDate(), parameters);
		if(StringUtils.isBlank(value)){
			Date date = null;
			for(ReportRepositoryListener listener : LISTENERS){
				Date d = listener.getHighestToDate(parameters);
				if(d!=null)
					date = d;
			}
			if(date==null)
				date = DateSearchCriteria.DATE_MOST_FUTURE;
			return date;
		}else
			return new Date(Long.parseLong(value));
	}
	
	protected String format(BigDecimal value){
		return RootBusinessLayer.getInstance().getNumberBusiness().format(value);
	}
	
	/**/
	
	protected void set(BigDecimal[][] totals,Integer i,Integer j,BigDecimal value){
		totals[i][j] = value;
	}
	protected void setTotal(BigDecimal[][] totals,Integer i,BigDecimal value){
		set(totals, i, 0, value);
		set(totals, i, 1, value);
	}
	protected void increment(BigDecimal[][] totals,Integer i,Integer j,BigDecimal value){
		totals[i][j] = totals[i][j].add(value);
	}
	protected void incrementTotal(BigDecimal[][] totals,Integer i,BigDecimal value){
		increment(totals, i, 0, value);
		increment(totals, i, 1, value);
	}
	
	protected void set(BigDecimal[][] totals,BigDecimal value){
		for(BigDecimal[] total : totals)
			for(int i=0;i<total.length;i++)
				total[i] = value;
	}
	
	protected BigDecimal get(BigDecimal[][] totals,Integer i,Integer j){
		return totals[i][j];
	}
	protected String getTotal(BigDecimal[][] totals,Integer i){
		return RootBusinessLayer.getInstance().getNumberBusiness().format(get(totals, i,0));
	}
	protected String getTotalOfTotals(BigDecimal[][] totals,Integer i){
		return RootBusinessLayer.getInstance().getNumberBusiness().format(get(totals, i,1));
	}
}