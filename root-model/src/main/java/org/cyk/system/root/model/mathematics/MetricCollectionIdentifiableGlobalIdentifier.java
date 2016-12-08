package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.value.Value;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A join between a metric collection and an identifiable
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity  @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class MetricCollectionIdentifiableGlobalIdentifier extends AbstractJoinGlobalIdentifier implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	@ManyToOne @NotNull private MetricCollection metricCollection;
	
	@ManyToOne @JoinColumn(name="thevalue") private Value value;
	
	/**/
	
	public MetricCollectionIdentifiableGlobalIdentifier(MetricCollection metricCollection,AbstractIdentifiable identifiable,Value value){
		super(identifiable);
		this.metricCollection = metricCollection;
		this.value = value;
	}
	
	/**/
	
	@Override
	public String toString() {
		return identifiableGlobalIdentifier.getCode()+" "+metricCollection+" "+value;
	}
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;
		
		private Collection<MetricCollectionType> metricCollectionTypes = new ArrayList<>();
		
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

		public SearchCriteria addMetricCollectionTypes(Collection<MetricCollectionType> metricCollectionTypes){
			this.metricCollectionTypes.addAll(metricCollectionTypes);
			return this;
		}
		
		public SearchCriteria addMetricCollectionType(MetricCollectionType metricCollectionType){
			return addMetricCollectionTypes(Arrays.asList(metricCollectionType));
		}
		
		
	}
	
	public static void define(Class<? extends AbstractIdentifiable> aClass){
		define(MetricCollectionIdentifiableGlobalIdentifier.class, aClass);
	}
	
	public static Boolean isUserDefinedClass(Class<?> aClass){
		return isUserDefinedClass(MetricCollectionIdentifiableGlobalIdentifier.class,aClass);
	}
	
	public static Boolean isUserDefinedObject(Object object){
		return isUserDefinedObject(MetricCollectionIdentifiableGlobalIdentifier.class,object);
	}
	
}