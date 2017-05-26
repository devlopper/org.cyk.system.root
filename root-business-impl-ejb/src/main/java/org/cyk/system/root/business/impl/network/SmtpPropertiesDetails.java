package org.cyk.system.root.business.impl.network;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SmtpPropertiesDetails extends AbstractOutputDetails<SmtpProperties> implements Serializable {

	private static final long serialVersionUID = -7568711914665423264L;

	@Input @InputText private FieldValue from;
	@Input @InputText private FieldValue service;
	@Input @InputText private FieldValue credentials;
	
	public SmtpPropertiesDetails(SmtpProperties smtpProperties) {
		super(smtpProperties);
	}
	
	@Override
	public void setMaster(SmtpProperties smtpProperties) {
		super.setMaster(smtpProperties);
		if(master!=null){
			service = new FieldValue(smtpProperties.getService());
			from = new FieldValue(smtpProperties.getFrom());
			credentials = new FieldValue(smtpProperties.getCredentials());
		}
	}
	
	public static final String FIELD_FROM = "from";
	public static final String FIELD_SERVICE = "service";
	public static final String FIELD_CREDENTIALS = "credentials";


}
