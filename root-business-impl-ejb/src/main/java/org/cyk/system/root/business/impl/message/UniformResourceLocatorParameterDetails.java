package org.cyk.system.root.business.impl.message;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UniformResourceLocatorParameterDetails extends AbstractOutputDetails<UniformResourceLocatorParameter> implements Serializable {

	private static final long serialVersionUID = -7568711914665423264L;

	@Input @InputText private String value;
	
	public UniformResourceLocatorParameterDetails(UniformResourceLocatorParameter uniformResourceLocatorParameter) {
		super(uniformResourceLocatorParameter);
	}
	
	@Override
	public void setMaster(UniformResourceLocatorParameter uniformResourceLocatorParameter) {
		super.setMaster(uniformResourceLocatorParameter);
		if(uniformResourceLocatorParameter!=null){
			value = uniformResourceLocatorParameter.getValue();
			
		}
	}
	
	public static final String FIELD_VALUE = "value";

}
