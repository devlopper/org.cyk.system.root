package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.Website;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class WebsiteDetails extends AbstractContactDetails<Website> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;

	@Input @InputText private FieldValue uniformResourceLocator;
	
	public WebsiteDetails(Website website) {
		super(website);
	}
	
	@Override
	public void setMaster(Website website) {
		super.setMaster(website);
		if(website!=null){
			uniformResourceLocator = new FieldValue(website.getUniformResourceLocator());
		}
	}

	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
}
