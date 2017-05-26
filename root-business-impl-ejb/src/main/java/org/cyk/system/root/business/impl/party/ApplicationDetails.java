package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.party.Application;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class ApplicationDetails extends AbstractOutputDetails<Application> implements Serializable {

	private static final long serialVersionUID = -7568711914665423264L;

	@Input @InputText private FieldValue smtpProperties;
	@Input @InputText private String uniformResourceLocatorFiltered,webContext;

	public ApplicationDetails(Application application) {
		super(application);
		
	}

	@Override
	public void setMaster(Application application) {
		super.setMaster(application);
		if(application!=null){
			uniformResourceLocatorFiltered = formatResponse(application.getUniformResourceLocatorFiltered());
			webContext = application.getWebContext();
			if(application.getSmtpProperties()!=null)
				smtpProperties = new FieldValue(application.getSmtpProperties());
		}
	}
	
	/**/
	
	public static final String FIELD_SMTP_PROPERTIES = "smtpProperties";
	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR_FILTERED = "uniformResourceLocatorFiltered";
	public static final String FIELD_WEB_CONTEXT = "webContext";
}
