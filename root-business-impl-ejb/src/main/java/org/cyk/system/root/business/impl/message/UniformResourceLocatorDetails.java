package org.cyk.system.root.business.impl.message;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UniformResourceLocatorDetails extends AbstractOutputDetails<UniformResourceLocator> implements Serializable {

	private static final long serialVersionUID = -7568711914665423264L;

	@Input @InputText private String address;
	
	public UniformResourceLocatorDetails(UniformResourceLocator uniformResourceLocator) {
		super(uniformResourceLocator);
	}
	
	@Override
	public void setMaster(UniformResourceLocator uniformResourceLocator) {
		super.setMaster(uniformResourceLocator);
		if(uniformResourceLocator!=null){
			address = uniformResourceLocator.getName();
			
		}
	}
	
	public static final String FIELD_ADDRESS = "address";

}
