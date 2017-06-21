package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A join between a metric value and an identifiable
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity  @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {MetricValueIdentifiableGlobalIdentifier.FIELD_METRIC_VALUE
		,MetricValueIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER})})
public class MetricValueIdentifiableGlobalIdentifier extends AbstractJoinGlobalIdentifier implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	@ManyToOne @NotNull private MetricValue metricValue;
	
	/**/
	
	public MetricValueIdentifiableGlobalIdentifier(MetricValue metricValue,AbstractIdentifiable identifiable){
		super(identifiable);
		this.metricValue = metricValue;
	}
	
	@Override
	public String toString() {
		return globalIdentifier+","+metricValue;
	}
	
	public static final String FIELD_METRIC_VALUE = "metricValue";
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;
		
		private Collection<Metric> metrics = new ArrayList<>();
		
		@Override
		public SearchCriteria addGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers){
			return (SearchCriteria) super.addGlobalIdentifiers(globalIdentifiers);
		}
		
		@Override
		public SearchCriteria addGlobalIdentifier(GlobalIdentifier globalIdentifier){
			return (SearchCriteria) super.addGlobalIdentifier(globalIdentifier);
		}
		
		@Override
		public SearchCriteria addIdentifiablesGlobalIdentifiers(Collection<? extends AbstractIdentifiable> identifiables) {
			return (SearchCriteria) super.addIdentifiablesGlobalIdentifiers(identifiables);
		}
		
		@Override
		public SearchCriteria addIdentifiableGlobalIdentifier(AbstractIdentifiable identifiable) {
			return (SearchCriteria) super.addIdentifiableGlobalIdentifier(identifiable);
		}

		public SearchCriteria addMetrics(Collection<Metric> metrics){
			this.metrics.addAll(metrics);
			return this;
		}
		public SearchCriteria addMetric(Metric metric){
			return addMetrics(Arrays.asList(metric));
		}
		
		@Override
		public String toString() {
			return super.toString()+" , Metrics : "+metrics.toString();
		}
	}
	
	public static void define(Class<? extends AbstractIdentifiable> aClass){
		define(MetricValueIdentifiableGlobalIdentifier.class, aClass);
	}
	
	public static Boolean isUserDefinedClass(Class<?> aClass){
		return isUserDefinedClass(MetricValueIdentifiableGlobalIdentifier.class,aClass);
	}
	
	public static Boolean isUserDefinedObject(Object object){
		return isUserDefinedObject(MetricValueIdentifiableGlobalIdentifier.class,object);
	}
	
}