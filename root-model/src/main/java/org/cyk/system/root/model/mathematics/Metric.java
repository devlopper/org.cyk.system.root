package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollectionItem;

/**
 * Quantifiable measure that is used to track and assess the status of a specific business process.
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter /*@NoArgsConstructor @AllArgsConstructor*/ @Entity
public class Metric extends AbstractCollectionItem<MetricCollection> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@OneToOne private IntervalCollection valueIntervalCollection;
	
	public IntervalCollection getValueIntervalCollection(){
		return valueIntervalCollection==null?(collection==null?null:collection.getValueIntervalCollection()):valueIntervalCollection;
	}
	
}