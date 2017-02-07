package org.cyk.system.root.business.impl.file.report;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.api.TypedBusiness.CreateReportFileArguments;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.report.RootReportProducer;
import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness.Derive;
import org.cyk.system.root.business.api.value.ValueCollectionBusiness;
import org.cyk.system.root.business.api.value.ValueCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractRootBusinessBean;
import org.cyk.system.root.business.impl.ManyConverter;
import org.cyk.system.root.business.impl.OneConverter;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;
import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ContactCollectionReport;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifierReport;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.IntervalReport;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.AbstractActorReport;
import org.cyk.system.root.model.party.person.ActorReport;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.party.person.PersonReport;
import org.cyk.system.root.model.userinterface.style.Style;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueCollectionItem;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.system.root.persistence.api.mathematics.IntervalDao;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionTypeDao;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.BeanAdapter;

public abstract class AbstractRootReportProducer extends AbstractRootBusinessBean implements RootReportProducer,Serializable {

	private static final long serialVersionUID = 7126711234011563710L;

	private static final String JASPER_STYLE = "<style size=\"%s\" forecolor=\"%s\">%s</style>";
	public static String NULL_VALUE = "NA";
	public static String NOT_APPLICABLE = "NA";
	public static RootReportProducer DEFAULT;
	
	@Override
	public <REPORT extends AbstractReportTemplateFile<REPORT>> REPORT produce(Class<REPORT> reportClass,CreateReportFileArguments<?> arguments) {
		return null;
	}
	
	protected String getJasperStyle(String text,Style style){
		return String.format(JASPER_STYLE, style.getFont().getSize(),Constant.CHARACTER_SHARP+style.getText().getColor().getHexademicalCode(),text);
	}
	
	@Override
	public Class<?> getReportTemplateFileClass(AbstractIdentifiable identifiable,String reportTemplateCode) {
		return null;
	}
	
	protected <T extends AbstractReportTemplateFile<?>> T createReportTemplateFile(Class<T> aClass,CreateReportFileArguments<?> arguments){
		T report = newInstance(aClass);
		report.setIsDraft(arguments.getIsDraft());
		report.setSource(arguments.getIdentifiable());
		return report;
	}
	
	public void addValueCollection(AbstractReportTemplateFile<?> report,String code,Derive listener,Boolean create){
		ValueCollection valueCollection = inject(ValueCollectionBusiness.class).deriveByCode(code,listener);
		if(Boolean.TRUE.equals(create))
			report.addLabelValueCollection(valueCollection.getName());
		Collection<ValueCollectionItem> items = valueCollection.getCollection();
		Collection<Value> values = new ArrayList<>();
		for(ValueCollectionItem item : valueCollection.getCollection()){
			values.add(item.getValue());
		}
		String[][] array = inject(ValueCollectionItemBusiness.class).convert(new ManyConverter.ConverterToArray<ValueCollectionItem,String[][]>(items, String[][].class
			,new OneConverter.ConverterToArray<ValueCollectionItem,String[]>(ValueCollectionItem.class, null, String[].class, null){
				private static final long serialVersionUID = 1L;
				
				@Override
				public Object getValueAt(Integer index) {
					if(index==1)
						return inject(FormatterBusiness.class).format(instance.getValue().get());
					return super.getValueAt(index);
				}
				
			}, null));
		report.addLabelValues(array);
	}
	
	public void addValueCollection(AbstractReportTemplateFile<?> report,String code,Derive listener){
		addValueCollection(report, code, listener,Boolean.TRUE);
	}
	/*
	public void addMetricCollection(AbstractReportTemplateFile<?> report,AbstractIdentifiable identifiable,MetricCollection metricCollection){
		MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria();
		searchCriteria.addIdentifiableGlobalIdentifier(identifiable).addMetricCollectionType(metricCollection.getType());
		Collection<MetricCollectionIdentifiableGlobalIdentifier> metricCollectionIdentifiableGlobalIdentifiers = inject(MetricCollectionIdentifiableGlobalIdentifierDao.class)
				.readByCriteria(searchCriteria);
		
		MetricCollectionIdentifiableGlobalIdentifier metricCollectionIdentifiableGlobalIdentifier = null;
		for(MetricCollectionIdentifiableGlobalIdentifier m : metricCollectionIdentifiableGlobalIdentifiers)
			if(m.getMetricCollection().equals(metricCollection)){
				metricCollectionIdentifiableGlobalIdentifier = m;
				break;
			}
		
		String n = metricCollection.getName()+(metricCollectionIdentifiableGlobalIdentifier==null?Constant.EMPTY_STRING:" : "+inject(FormatterBusiness.class)
				.format(metricCollectionIdentifiableGlobalIdentifier.getValue()));
		
		report.addLabelValueCollection(n);
	}
	*/
	
	/**/
	
	protected void addMetricCollection(AbstractReportTemplateFile<?> report,AbstractIdentifiable identifiable,String code,Derive derive,Boolean create){
		final MetricCollection metricCollection = inject(MetricCollectionDao.class).read(code);
		if(Boolean.TRUE.equals(create)){
			MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria();
			searchCriteria.addIdentifiableGlobalIdentifier(identifiable).addMetricCollectionType(metricCollection.getType());
			Collection<MetricCollectionIdentifiableGlobalIdentifier> metricCollectionIdentifiableGlobalIdentifiers = inject(MetricCollectionIdentifiableGlobalIdentifierDao.class)
					.readByCriteria(searchCriteria);
			
			MetricCollectionIdentifiableGlobalIdentifier metricCollectionIdentifiableGlobalIdentifier = null;
			for(MetricCollectionIdentifiableGlobalIdentifier m : metricCollectionIdentifiableGlobalIdentifiers)
				if(m.getMetricCollection().equals(metricCollection)){
					metricCollectionIdentifiableGlobalIdentifier = m;
					break;
				}
			
			report.addLabelValueCollection((metricCollection.getName()
					+(metricCollectionIdentifiableGlobalIdentifier==null?Constant.EMPTY_STRING:" : "+inject(FormatterBusiness.class)
							.format(metricCollectionIdentifiableGlobalIdentifier.getValue()))));
			//report.addLabelValueCollection(metricCollection.getName());
		}
		Collection<Metric> metrics = inject(MetricDao.class).readByCollection(metricCollection);
		Collection<MetricValue> metricValues = inject(MetricValueBusiness.class).findByMetricsByIdentifiables(metrics,Arrays.asList(identifiable)); 
		if(derive!=null)
			derive.addInputs(metricCollection);
		inject(MetricValueBusiness.class).derive(metricValues,derive);
		String[][] values = inject(MetricValueBusiness.class).convert(new ManyConverter.ConverterToArray<MetricValue,String[][]>(metricValues, String[][].class
			,new OneConverter.ConverterToArray<MetricValue,String[]>(MetricValue.class, null, String[].class, null){
				private static final long serialVersionUID = 1L;
				
				@Override
				public Object getValueAt(Integer index) {
					if(index==0)
						return instance.getMetric().getName();
					if(index==1)
						return inject(FormatterBusiness.class).format(instance.getValue());
					return super.getValueAt(index);
				}
				
				@Override
				public Object getBlankValueOf(Integer index) {
					if(index==1 && metricCollection.getValueProperties()!=null && Boolean.TRUE.equals(metricCollection.getValueProperties().getNullable()))
						return metricCollection.getValueProperties().getNullString().getAbbreviation();
					return super.getBlankValueOf(index);
				}
				
			}, null));
		report.addLabelValues(values);
	}
	
	protected void addMetricCollection(AbstractReportTemplateFile<?> report,AbstractIdentifiable identifiable,String code,Derive derive){
		addMetricCollection(report, identifiable, code,derive,Boolean.TRUE);  
	}
	
	protected void addMetricCollection(AbstractReportTemplateFile<?> report,AbstractIdentifiable identifiable,String code){
		addMetricCollection(report, identifiable, code,null);  
	}
	
	protected void addMetricCollectionsByType(AbstractReportTemplateFile<?> report,AbstractIdentifiable identifiable,Collection<MetricCollectionIdentifiableGlobalIdentifier> metricCollectionIdentifiableGlobalIdentifiers){
		for(MetricCollectionIdentifiableGlobalIdentifier index : metricCollectionIdentifiableGlobalIdentifiers){
			addMetricCollection(report, identifiable, index.getMetricCollection().getCode());
		}
	}
	
	protected void addMetricCollectionsByType(AbstractReportTemplateFile<?> report,AbstractIdentifiable identifiable,AbstractIdentifiable metricCollectionJoin,String typeCode){
		addMetricCollectionsByType(report, identifiable, inject(MetricCollectionIdentifiableGlobalIdentifierDao.class)
				.readByCriteria(new MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria().addIdentifiableGlobalIdentifier(metricCollectionJoin)
						.addMetricCollectionType(inject(MetricCollectionTypeDao.class).read(typeCode))));
	}
	
	protected AbstractRootReportProducer addIntervalCollection(AbstractReportTemplateFile<?> report,IntervalCollection intervalCollection,ValueProperties valueProperties,Boolean ascending,Boolean includeExtremities,
			Integer[][] columnsToSwap,Boolean create){
		String[][] values =  convertToArray(inject(IntervalDao.class).readByCollection(intervalCollection,ascending),includeExtremities);
		if(columnsToSwap!=null)
			for(Integer[] index : columnsToSwap){
				commonUtils.swapColumns(values, index[0], index[1]);
			}
		if(Boolean.TRUE.equals(create))
			report.addLabelValueCollection(intervalCollection.getName());
		
		report.addLabelValues(values);
		if(valueProperties!=null && Boolean.TRUE.equals(valueProperties.getNullable()) && valueProperties.getNullString()!=null)
			report.addLabelValue(valueProperties.getNullString().getAbbreviation(), valueProperties.getNullString().getName());
		return this;
	}
	
	protected AbstractRootReportProducer addIntervalCollection(AbstractReportTemplateFile<?> report,IntervalCollection intervalCollection,ValueProperties valueProperties,Boolean ascending,Boolean includeExtremities,
			Integer[][] columnsToSwap){
		return addIntervalCollection(report, intervalCollection, valueProperties, ascending, includeExtremities, columnsToSwap,Boolean.TRUE);
	}
	
	/**/
	
	protected void setGlobalIdentifier(GlobalIdentifier globalIdentifier,GlobalIdentifierReport report){
		if(globalIdentifier==null)
			return;
		report.setCode(globalIdentifier.getCode());
		report.setName(globalIdentifier.getName());
		report.setBirthLocation(formatUsingBusiness(globalIdentifier.getBirthLocation()));
		report.setDeathLocation(formatUsingBusiness(globalIdentifier.getDeathLocation()));
		report.getExistencePeriod().setFrom(format(globalIdentifier.getExistencePeriod().getFromDate()));
		report.getExistencePeriod().setTo(format(globalIdentifier.getExistencePeriod().getToDate()));
	}
	
	protected void setGlobalIdentifier(AbstractIdentifiable identifiable,AbstractIdentifiableReport<?> report){
		if(identifiable==null || identifiable.getGlobalIdentifier()==null)
			return ;
		setGlobalIdentifier(identifiable.getGlobalIdentifier(), report.getGlobalIdentifier());
	}
	
	protected void set(ContactCollection contactCollection,ContactCollectionReport report){
		report.setPhoneNumbers(StringUtils.join(contactCollection.getPhoneNumbers(),Constant.CHARACTER_COLON));
		report.setEmails(StringUtils.join(contactCollection.getElectronicMails(),Constant.CHARACTER_COLON));
		report.setLocations(StringUtils.join(contactCollection.getLocations(),Constant.CHARACTER_COLON));
		report.setPostalBoxs(StringUtils.join(contactCollection.getPostalBoxs(),Constant.CHARACTER_COLON));
		report.setWebsites(StringUtils.join(contactCollection.getWebsites(),Constant.CHARACTER_COLON));
	}
	
	protected void set(Person person,PersonReport report){
		if(person==null){
			report.getGlobalIdentifier().setName(NOT_APPLICABLE);
			report.setNames(NOT_APPLICABLE);
			report.setLastnames(NOT_APPLICABLE);
			report.setLastnames(Constant.EMPTY_STRING);
		}else{
			inject(PersonBusiness.class).load(person);
			if(person.getContactCollection()!=null)
				set(person.getContactCollection(), report.getContactCollection());
			
			report.getGlobalIdentifier().setName(person.getGlobalIdentifier().getName());
			report.setLastnames(person.getLastnames());
			report.setNames(inject(PersonBusiness.class).findNames(person));
			report.setSurname(person.getSurname());
			report.getGlobalIdentifier().getExistencePeriod().setFrom(format(person.getBirthDate()));
			report.getGlobalIdentifier().setCode(person.getCode());
			
			if(person.getImage()!=null){
				report.getGlobalIdentifier().setImage(findInputStream(person.getImage()));
			}
			if(person.getNationality()!=null)
				report.setNationality(person.getNationality().getUiString());
			if(person.getSex()!=null)
				report.setSex(person.getSex().getName());
			
			if(person.getExtendedInformations()!=null){
				PersonExtendedInformations extendedInformations = person.getExtendedInformations();
				if(extendedInformations.getBirthLocation()!=null)
					report.getGlobalIdentifier().setBirthLocation(extendedInformations.getBirthLocation().getUiString());
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
	
	protected void set(AbstractActor actor,AbstractActorReport<?> report){
		set(actor==null?null:actor.getPerson(), report.getPerson());
		setGlobalIdentifier(actor, report);
	}
	
	protected void set(Person person,ActorReport report){
		set(person, report.getPerson());
	}
	
	protected void set(Interval interval,IntervalReport report){
		report.getGlobalIdentifier().setCode(interval==null?NOT_APPLICABLE:interval.getCode());
		report.getGlobalIdentifier().setName(interval==null?NOT_APPLICABLE:/*format(interval.getLow())+" - "+format(interval.getHigh())+" "+*/interval.getName());
	}
	
	protected InputStream findInputStream(File file){
		return inject(FileBusiness.class).findInputStream(file);
	}
	
	//TODO use convertToArray(Collection<Value> values)
	
	@Deprecated
	protected String[][] convertToArray(Collection<Metric> metrics,Collection<MetricValue> metricValues,String nullValueString){
		String[][] values = new String[metrics.size()][2];
		Integer i = 0;
		for(Metric metric : metrics){//TODO is it really necessary to do double loop
			for(MetricValue metricValue : metricValues){
				if(metricValue.getMetric().equals(metric)){
					values[i][0] = metricValue.getMetric().getName();
					values[i][1] = formatMetricValue(metricValue);
					if(values[i][1] == null)
						values[i][1] = nullValueString;
				}
			}
			i++;
		}
		return values;
	}
	
	
	protected String format(Object object){
		return inject(FormatterBusiness.class).format(object);
	}
	
	protected String formatMetricValue(MetricValue metricValue){
		return format(metricValue.getValue());
	}
	
	@Deprecated
	protected String[][] convertToArray(Collection<Interval> intervals,Boolean includeExtremities){
		String[][] values = new String[intervals.size()][2+(Boolean.TRUE.equals(includeExtremities)?1:0)];
		Integer i = 0;
		for(Interval interval : intervals){
			values[i][0] = RootConstant.Code.getRelativeCode(interval);
			values[i][1] = interval.getName();
			if(Boolean.TRUE.equals(includeExtremities))
				values[i][2] = interval.getLow()+" - "+interval.getHigh();
			i++;
		}
		return values;
	}
		
	/**/
	
	public static interface Listener {
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		/**/
		public static class Adapter extends BeanAdapter implements Serializable,Listener {
			private static final long serialVersionUID = 1L;
			
			/**/
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}
		}
	}
	
}
