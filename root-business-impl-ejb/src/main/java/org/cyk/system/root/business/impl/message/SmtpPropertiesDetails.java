package org.cyk.system.root.business.impl.message;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.message.SmtpProperties;

@Getter @Setter
public class SmtpPropertiesDetails extends AbstractOutputDetails<SmtpProperties> implements Serializable {

	private static final long serialVersionUID = -7568711914665423264L;

	
	public SmtpPropertiesDetails(SmtpProperties smtpProperties) {
		super(smtpProperties);
		
	}

}
