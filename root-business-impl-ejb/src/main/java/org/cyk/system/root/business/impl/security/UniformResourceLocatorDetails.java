package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.network.UniformResourceLocatorParameterBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class UniformResourceLocatorDetails extends AbstractEnumerationDetails<UniformResourceLocator> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String address,parameters;
	
	public UniformResourceLocatorDetails(UniformResourceLocator uniformResourceLocator) {
		super(uniformResourceLocator);
		address = uniformResourceLocator.getAddress();
		parameters = StringUtils.join(inject(UniformResourceLocatorParameterBusiness.class).findByUniformResourceLocator(uniformResourceLocator)
				,Constant.CHARACTER_COLON);
	}
}