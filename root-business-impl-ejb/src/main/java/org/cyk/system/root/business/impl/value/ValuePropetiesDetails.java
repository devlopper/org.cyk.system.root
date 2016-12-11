package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class ValuePropetiesDetails extends AbstractOutputDetails<ValueProperties> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String type,intervalCollection,set,nullable,nullString;
	
	public ValuePropetiesDetails(ValueProperties valueProperties) {
		super(valueProperties);
		type = formatUsingBusiness(valueProperties.getType());
		intervalCollection = formatUsingBusiness(valueProperties.getIntervalCollection());
		set = formatUsingBusiness(valueProperties.getSet());
		nullable = formatUsingBusiness(valueProperties.getNullable());
		nullString = formatUsingBusiness(valueProperties.getNullString());
	}
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_INTERVAL_COLLECTION = "intervalCollection";
	public static final String FIELD_SET = "set";
	public static final String FIELD_NULLABLE = "nullable";
	public static final String FIELD_NULLABLE_STRING = "nullString";

}