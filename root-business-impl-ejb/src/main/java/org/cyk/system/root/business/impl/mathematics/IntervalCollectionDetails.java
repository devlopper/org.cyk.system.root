package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractCollectionDetails;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class IntervalCollectionDetails extends AbstractCollectionDetails<IntervalCollection> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String lowestValue,highestValue,numberOfDecimalAfterDot;
	
	public IntervalCollectionDetails(IntervalCollection intervalCollection) {
		super(intervalCollection);
		lowestValue = formatNumber(intervalCollection.getLowestValue());
		highestValue = formatNumber(intervalCollection.getHighestValue());
		numberOfDecimalAfterDot = formatNumber(intervalCollection.getNumberOfDecimalAfterDot());
	}
	
	public static final String FIELD_LOWEST_VALUE = "lowestValue";
	public static final String FIELD_HIGHEST_VALUE = "highestValue";
	public static final String FIELD_NUMBER_OF_DECIMAL_AFTER_DOT = "numberOfDecimalAfterDot";
}