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

	@Input @InputText private String uniformResourceLocatorFilteringEnabled,webContext;

	public ApplicationDetails(Application application) {
		super(application);
		uniformResourceLocatorFilteringEnabled = formatResponse(application.getUniformResourceLocatorFilteringEnabled());
		webContext = application.getWebContext();
	}

}
