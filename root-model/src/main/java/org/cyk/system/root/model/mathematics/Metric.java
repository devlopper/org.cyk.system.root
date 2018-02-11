package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.value.Measure;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.Setter;

/**
 * Quantifiable measure that is used to track and assess the status of a specific business process.
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter /*@NoArgsConstructor @AllArgsConstructor*/ @Entity @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS)
@FieldOverride(name=AbstractCollectionItem.FIELD_COLLECTION,type=MetricCollection.class)
public class Metric extends AbstractCollectionItem<MetricCollection> implements Serializable {
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @JoinColumn(name=COLUMN_VALUE_PROPERTIES) private ValueProperties valueProperties;
	
	public ValueProperties getValueProperties(){
		return valueProperties == null ? collection.getValueProperties() : valueProperties;
	}
	
	public IntervalCollection getValueIntervalCollection(){
		ValueProperties valueProperties = getValueProperties();
		return valueProperties == null ? null : valueProperties.getIntervalCollection();
	}
	
	public Measure getMeasure(){
		ValueProperties valueProperties = getValueProperties();
		return valueProperties == null ? null : valueProperties.getMeasure();
	}
	
	public static final String FIELD_VALUE_PROPERTIES = "valueProperties";
	
	public static final String COLUMN_VALUE_PROPERTIES = "valueProperties";
}