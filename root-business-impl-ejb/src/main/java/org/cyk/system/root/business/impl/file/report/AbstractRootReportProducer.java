package org.cyk.system.root.business.impl.file.report;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.api.TypedBusiness.CreateReportFileArguments;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.report.RootReportProducer;
import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness.Derive;
import org.cyk.system.root.business.api.value.ValueCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractRootBusinessBean;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;
import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;
import org.cyk.system.root.model.file.report.LabelValueCollectionReport;
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
import org.cyk.system.root.persistence.api.party.person.PersonExtendedInformationsDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.FieldHelper;

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
		Collection<ValueCollectionItem> items = valueCollection.getItems().getElements();
		Collection<Value> values = new ArrayList<>();
		for(ValueCollectionItem item : valueCollection.getItems().getElements()){
			values.add(item.getValue());
		}
		/*
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
		*/
		String[][] array = ArrayHelper.getInstance().getTwoDimension(String.class,items
				, new String[]{ FieldHelper.getInstance().buildPath(Metric.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME),MetricValue.FIELD_VALUE });
		
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
		//LoggingHelper.Message.Builder logMessageBuilder = new LogMessage.Builder("Add","metric collection");
		final MetricCollection metricCollection = inject(MetricCollectionDao.class).read(code);
		//logMessageBuilder.addParameters("identifiable",identifiable,"class",identifiable.getClass().getSimpleName(),"code",code,"found",metricCollection!=null);
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
			//logMessageBuilder.addParameters("Identifiable metric collection found",metricCollectionIdentifiableGlobalIdentifier!=null);
			report.addLabelValueCollection((metricCollection.getName()
					+(metricCollectionIdentifiableGlobalIdentifier==null?Constant.EMPTY_STRING:" : "+inject(FormatterBusiness.class)
							.format(metricCollectionIdentifiableGlobalIdentifier.getValue()))));
			//report.addLabelValueCollection(metricCollection.getName());
		}
		Collection<Metric> metrics = inject(MetricDao.class).readByCollection(metricCollection);
		//logMessageBuilder.addParameters("metrics",metrics);
		Collection<MetricValue> metricValues = inject(MetricValueBusiness.class).findByMetricsByIdentifiables(metrics,Arrays.asList(identifiable)); 
		//logMessageBuilder.addParameters("values",metricValues);
		if(derive!=null)
			derive.addInputs(metricCollection);
		inject(MetricValueBusiness.class).derive(metricValues,derive);
		//logMessageBuilder.addParameters("derived",metricValues);
		/*
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
		*/
		String[][] values = ArrayHelper.getInstance().getTwoDimension(String.class,metricValues
				, new String[]{ FieldHelper.getInstance().buildPath(Metric.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME),MetricValue.FIELD_VALUE });
		//logMessageBuilder.addParameters("array",commonUtils.convertToString(values, Constant.CHARACTER_COMA, Constant.CHARACTER_VERTICAL_BAR));
		report.addLabelValues(values);
		//logTrace(logMessageBuilder);
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
	//TODO to be move in each report
	protected void setGlobalIdentifier(GlobalIdentifier globalIdentifier,GlobalIdentifierReport report){
		if(globalIdentifier==null)
			return;
		report.setCode(globalIdentifier.getCode());
		report.setName(globalIdentifier.getName());
		report.setBirthLocation(formatUsingBusiness(globalIdentifier.getBirthLocation()));
		report.setDeathLocation(formatUsingBusiness(globalIdentifier.getDeathLocation()));
		report.getExistencePeriod().setFromDate(format(globalIdentifier.getExistencePeriod().getFromDate()));
		report.getExistencePeriod().setToDate(format(globalIdentifier.getExistencePeriod().getToDate()));
	}
	
	protected void setGlobalIdentifier(AbstractIdentifiable identifiable,AbstractIdentifiableReport<?> report){
		if(identifiable==null || identifiable.getGlobalIdentifier()==null)
			return ;
		setGlobalIdentifier(identifiable.getGlobalIdentifier(), report.getGlobalIdentifier());
	}
	
	@Deprecated
	protected void set(Person person,PersonReport report){
		if(person==null){
			report.getGlobalIdentifier().setName(NOT_APPLICABLE);
			report.setNames(NOT_APPLICABLE);
			report.setLastnames(Constant.EMPTY_STRING);
		}else{
			report.setNames(inject(PersonBusiness.class).findNames(person));
			report.setSource(person);
			
			/*			
			if(person.getImage()!=null){
				report.getGlobalIdentifier().setImage(findInputStream(person.getImage()));
			}
			*/
			
			person.setExtendedInformations(inject(PersonExtendedInformationsDao.class).readByParty(person));
			if(person.getExtendedInformations()!=null){
				PersonExtendedInformations extendedInformations = person.getExtendedInformations();
				if(extendedInformations.getBirthLocation()!=null)
					report.getGlobalIdentifier().setBirthLocation(extendedInformations.getBirthLocation().getUiString());//TODO should be moved to global identifier
				if(extendedInformations.getSignatureSpecimen()!=null)
					report.setSignatureSpecimen(findInputStream(extendedInformations.getSignatureSpecimen()));
				
			}
		}
	}
	
	protected void set(AbstractActor actor,AbstractActorReport<?> report){
		//set(actor==null?null:actor.getPerson(), report.getPerson());
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
		
		void process(AbstractReportTemplateFile<?> report);
		void processLabelValueCollection(AbstractIdentifiableReport<?> identifiable,LabelValueCollectionReport labelValueCollection);
		
		public static class Adapter extends BeanAdapter implements Serializable,Listener {
			private static final long serialVersionUID = 1L;
			
			/**/
			
			@Override
			public void process(AbstractReportTemplateFile<?> report) {	}
			
			@Override
			public void processLabelValueCollection(AbstractIdentifiableReport<?> identifiable,LabelValueCollectionReport labelValueCollection) { }
			
			/**/
			
			public static <T extends Listener> void process(Collection<T> listeners,final AbstractReportTemplateFile<?> report){
				ListenerUtils.getInstance().execute(listeners, new ListenerUtils.VoidMethod<T>() {
					@Override
					public void execute(T listener) {
						listener.process(report);
					}
				});
			}
			
			public static <T extends Listener> void processLabelValueCollection(Collection<T> listeners,@SuppressWarnings("rawtypes") final AbstractIdentifiableReport identifiable,final LabelValueCollectionReport labelValueCollection){
				ListenerUtils.getInstance().execute(listeners, new ListenerUtils.VoidMethod<T>() {
					@Override
					public void execute(T listener) {
						listener.processLabelValueCollection(identifiable,labelValueCollection);
					}
				});
			}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}

			
		}
	}
	
}
