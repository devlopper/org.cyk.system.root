package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.value.Value;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ValueDetails extends AbstractOutputDetails<Value> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue properties;
	@Input @InputText private String value;
	
	public ValueDetails(Value value) {
		super(value);
	}
	
	@Override
	public void setMaster(Value value) {
		super.setMaster(value);
		if(value!=null){
			this.value = formatUsingBusiness(value);
			if(value.getProperties()!=null)
				properties = new FieldValue(value.getProperties());
		}
	}
	
	public static final String FIELD_PROPERTIES = "properties";
	public static final String FIELD_VALUE = "value";
	
}