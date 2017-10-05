package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.Website;

@Getter @Setter
public class WebsiteDetails extends AbstractContactDetails<Website> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;

	private FieldValue country,type,locationType;
	
	public WebsiteDetails(Website website) {
		super(website);
	}

	
}
