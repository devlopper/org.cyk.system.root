package org.cyk.system.root.business.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.RootReportProducer;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;
import org.cyk.system.root.model.file.report.LabelValueCollectionReport;
import org.cyk.system.root.model.file.report.LabelValueReport;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ContactReport;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.IntervalReport;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.ActorReport;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.party.person.PersonReport;
import org.cyk.system.root.model.userinterface.style.Style;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRootReportProducer extends AbstractRootBusinessBean implements RootReportProducer,Serializable {

	private static final long serialVersionUID = 7126711234011563710L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRootReportProducer.class);
	private static final String JASPER_STYLE = "<style size=\"%s\" forecolor=\"%s\">%s</style>";
	public static String NULL_VALUE = "NA";
	public static String NOT_APPLICABLE = "NA";
	
	protected LabelValueCollectionReport currentLabelValueCollection;
	
	protected String getJasperStyle(String text,Style style){
		return String.format(JASPER_STYLE, style.getFont().getSize(),"#"+style.getText().getColor().getHexademicalCode(),text);
		//return String.format(JASPER_STYLE, style.getFont().getSize(),"#0000ff",text);
	}
	
	protected LabelValueCollectionReport labelValueCollection(String labelId){
		currentLabelValueCollection = new LabelValueCollectionReport();
		currentLabelValueCollection.setName(rootBusinessLayer.getLanguageBusiness().findText(labelId));
		return currentLabelValueCollection;
	}
	
	protected LabelValueReport labelValue(LabelValueCollectionReport collection,String id,String value,Boolean condition){
		if(!Boolean.TRUE.equals(condition))
			return null;
		currentLabelValueCollection = collection;
		return currentLabelValueCollection.add(id,languageBusiness.findText(id), value);
	}
	protected LabelValueReport labelValue(String labelId,String value,Boolean condition){
		return labelValue(currentLabelValueCollection, labelId, value,condition);
	}
	
	protected LabelValueReport labelValue(LabelValueCollectionReport collection,String labelId,String value){
		return labelValue(collection, labelId, value,Boolean.TRUE);
	}
	protected LabelValueReport labelValue(String labelId,String value){
		return labelValue(currentLabelValueCollection,labelId, value);
	}
	
	protected LabelValueReport getLabelValue(String id){
		return currentLabelValueCollection.getById(id);
	}
	
	protected void set(ContactCollection contactCollection,ContactReport report){
		report.setPhoneNumbers(StringUtils.join(contactCollection.getPhoneNumbers(),Constant.CHARACTER_COLON));
		report.setEmails(StringUtils.join(contactCollection.getElectronicMails(),Constant.CHARACTER_COLON));
		report.setLocations(StringUtils.join(contactCollection.getLocations(),Constant.CHARACTER_COLON));
		report.setPostalBoxs(StringUtils.join(contactCollection.getPostalBoxs(),Constant.CHARACTER_COLON));
		report.setWebsites(StringUtils.join(contactCollection.getWebsites(),Constant.CHARACTER_COLON));
	}
	
	protected void set(Person person,PersonReport report){
		if(person==null){
			report.setName(NOT_APPLICABLE);
			report.setNames(NOT_APPLICABLE);
			report.setLastName(NOT_APPLICABLE);
			report.setLastName(Constant.EMPTY_STRING);
		}else{
			rootBusinessLayer.getPersonBusiness().load(person);
			if(person.getContactCollection()!=null)
				set(person.getContactCollection(), report.getContact());
			
			report.setName(person.getName());
			report.setLastName(person.getLastName());
			report.setNames(person.getNames());
			report.setSurname(person.getSurname());
			report.setBirthDate(format(person.getBirthDate()));
			report.setCode(person.getCode());
			
			if(person.getImage()!=null){
				report.setImage(findInputStream(person.getImage()));
			}
			if(person.getNationality()!=null)
				report.setNationality(person.getNationality().getUiString());
			if(person.getSex()!=null)
				report.setSex(person.getSex().getName());
			
			if(person.getExtendedInformations()!=null){
				PersonExtendedInformations extendedInformations = person.getExtendedInformations();
				if(extendedInformations.getBirthLocation()!=null)
					report.setBirthLocation(extendedInformations.getBirthLocation().getUiString());
				if(extendedInformations.getTitle()!=null)
					report.setTitle(extendedInformations.getTitle().getUiString());
				if(extendedInformations.getSignatureSpecimen()!=null)
					report.setSignatureSpecimen(findInputStream(extendedInformations.getSignatureSpecimen()));
				if(extendedInformations.getMaritalStatus()!=null)
					report.setMaritalStatus(extendedInformations.getMaritalStatus().getName());
			}
			
			if(person.getJobInformations()!=null){
				JobInformations jobInformations = person.getJobInformations();
				if(jobInformations.getFunction()!=null)
					report.setJobFonction(jobInformations.getFunction().getName());
				if(jobInformations.getTitle()!=null)
					report.setJobTitle(jobInformations.getTitle().getName());
			}
			
			//System.out.println("AbstractRootReportProducer.set() : "+report.getImage());	
		}
	}
	
	protected void set(AbstractActor actor,ActorReport report){
		set(actor==null?null:actor.getPerson(), report.getPerson());
		if(actor==null){
			
		}else{
			report.setRegistrationCode(actor.getRegistration().getCode());
			report.setRegistrationDate(format(actor.getRegistration().getDate()));
		}
	}
	
	protected void set(Person person,ActorReport report){
		set(person, report.getPerson());
	}
	
	protected void set(Interval interval,IntervalReport report){
		report.setCode(interval==null?NOT_APPLICABLE:interval.getCode());
		report.setName(interval==null?NOT_APPLICABLE:/*format(interval.getLow())+" - "+format(interval.getHigh())+" "+*/interval.getName());
	}
	
	protected InputStream findInputStream(File file){
		return RootBusinessLayer.getInstance().getFileBusiness().findInputStream(file);
	}
	
	protected String[][] convertToArray(Collection<Metric> metrics,Collection<MetricValue> metricValues){
		String[][] values = new String[metrics.size()][2];
		Integer i = 0;
		for(Metric metric : metrics){
			for(MetricValue metricValue : metricValues)
				if(metricValue.getMetric().equals(metric)){
					values[i][0] = metric.getName();
					values[i][1] = rootBusinessLayer.getMetricValueBusiness().format(metricValue);
				}
			i++;
		}
		return values;
	}
	
	protected String[][] convertToArray(Collection<Interval> intervals,Boolean includeExtremities){
		String[][] values = new String[intervals.size()][2+(Boolean.TRUE.equals(includeExtremities)?1:0)];
		Integer i = 0;
		for(Interval interval : intervals){
			/*for(Listener listener : Listener.COLLECTION){
				String v = listener.getCollectionItemCode(interval);
				if(v!=null)
					values[i][0] = v;
			}*/
			values[i][0] = rootBusinessLayer.getIntervalBusiness().findRelativeCode(interval);
			values[i][1] = interval.getName();
			if(Boolean.TRUE.equals(includeExtremities))
				values[i][2] = interval.getLow()+" - "+interval.getHigh();
			i++;
		}
		return values;
	}
	
	protected LabelValueCollectionReport addIntervalCollectionLabelValueCollection(AbstractReportTemplateFile<?> report,IntervalCollection intervalCollection,Boolean ascending,Boolean includeExtremities,
			Integer[][] columnsToSwap){
		String[][] values =  convertToArray(rootBusinessLayer.getIntervalDao().readByCollection(intervalCollection,ascending),includeExtremities);
		if(columnsToSwap!=null)
			for(Integer[] index : columnsToSwap){
				commonUtils.swapColumns(values, index[0], index[1]);
			}
		return report.addLabelValueCollection(intervalCollection.getName(),values);
	}
	
	/*protected LabelValueCollectionReport addMetricCollectionLabelValueCollection(AbstractReportTemplateFile<?> report,MetricCollection metricCollection){
		LabelValueCollectionReport labelValueCollectionReport = new LabelValueCollectionReport();
		labelValueCollectionReport.setName(metricCollection.getName());
		for(MetricValue metricValue : rootBusinessLayer.getMetricDao().fin)
	}*/
			
	
	@Override
	protected Logger __logger__() {
		return LOGGER;
	}
	
	/**/
	
	public static interface Listener {
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		/**/
		public static class Adapter extends BeanAdapter implements Serializable,Listener {
			private static final long serialVersionUID = 1L;
			
			/**/
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}
		}
	}
	
}
