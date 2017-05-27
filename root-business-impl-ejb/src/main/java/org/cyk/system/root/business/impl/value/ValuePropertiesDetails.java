package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ValuePropertiesDetails extends AbstractOutputDetails<ValueProperties> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue measure,intervalCollection;
	@Input @InputText private String type,set;
	
	@Input @InputText private String derived;
	@Input @InputText private FieldValue derivationScript;
	
	@Input @InputText private String nullable;
	@Input @InputText private FieldValue nullString;
	
	public ValuePropertiesDetails(ValueProperties valueProperties) {
		super(valueProperties);
	}
	
	@Override
	public void setMaster(ValueProperties valueProperties) {
		super.setMaster(valueProperties);
		if(valueProperties!=null){
			if(valueProperties.getMeasure()!=null)
				measure = new FieldValue(valueProperties.getMeasure());
			if(valueProperties.getIntervalCollection()!=null)
				intervalCollection = new FieldValue(valueProperties.getIntervalCollection());
			if(valueProperties.getDerivationScript()!=null)
				derivationScript = new FieldValue(valueProperties.getDerivationScript());
			if(valueProperties.getNullString()!=null)
				nullString = new FieldValue(valueProperties.getNullString());
			
			type = formatUsingBusiness(valueProperties.getType());
			set = formatUsingBusiness(valueProperties.getSet());
			
			derived = formatResponse(valueProperties.getDerived());
			nullable = formatResponse(valueProperties.getNullable());
		}
	}
	
	public static final String FIELD_MEASURE = "measure";
	public static final String FIELD_INTERVAL_COLLECTION = "intervalCollection";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_SET = "set";
	public static final String FIELD_DERIVED = "derived";
	public static final String FIELD_DERIVATION_SCRIPT = "derivationScript";
	public static final String FIELD_NULLABLE = "nullable";
	public static final String FIELD_NULL_STRING = "nullString";
	
}