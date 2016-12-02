package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import org.cyk.system.root.model.AbstractCollectionItem;
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
public class Metric extends AbstractCollectionItem<MetricCollection> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@OneToOne private IntervalCollection valueIntervalCollection;
	
	/**
	 * If not null then use it else use MetricCollection.valueType
	 */
	@Enumerated(EnumType.ORDINAL) private MetricValueType valueType = MetricValueType.NUMBER;
	
	public IntervalCollection getValueIntervalCollection(){
		return valueIntervalCollection==null?(collection==null?null:collection.getValueIntervalCollection()):valueIntervalCollection;
	}
	
	public static final String FIELD_VALUE_INTERVAL_COLLECTION = "valueIntervalCollection";
}