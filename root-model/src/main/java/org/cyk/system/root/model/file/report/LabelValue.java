package org.cyk.system.root.model.file.report;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractModelElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LabelValue extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 9095448953171778448L;

	private String label; 
	
	private String value;
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	@Override
	public String toString() {
		return label+"="+value;
	}
	
	public static final String FIELD_LABEL = "label";
	public static final String FIELD_VALUE = "value";

}
