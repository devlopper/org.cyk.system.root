package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.value.Measure;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MeasureDetails extends AbstractOutputDetails<Measure> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue type;
	@Input @InputText private String value;
	
	public MeasureDetails(Measure measure) {
		super(measure);
	}
	
	@Override
	public void setMaster(Measure measure) {
		super.setMaster(measure);
		if(measure!=null){
			type = new FieldValue(measure.getType());
			value = formatNumber(measure.getValue());
		}
	}
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_VALUE = "value";
	
}