package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS)
public class MetricCollectionType extends AbstractEnumeration implements Serializable{
	
	private static final long serialVersionUID = 374208919427476791L;
	
	@ManyToOne private ValueProperties collectionValueProperties;
	
	@ManyToOne private ValueProperties metricValueProperties;
	
	public MetricCollectionType() {}

	public MetricCollectionType(String code,String name, String abbreviation) {
		super(code,name, abbreviation,null);
	}
	
	public MetricCollectionType setCollectionValueProperties(ValueProperties collectionValueProperties){
		this.collectionValueProperties = collectionValueProperties;
		return this;
	}
	
	public MetricCollectionType setMetricValueProperties(ValueProperties metricValueProperties){
		this.metricValueProperties = metricValueProperties;
		return this;
	}

}

