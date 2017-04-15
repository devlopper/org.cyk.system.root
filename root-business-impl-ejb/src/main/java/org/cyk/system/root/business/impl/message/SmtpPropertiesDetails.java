package org.cyk.system.root.business.impl.message;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.network.ServiceDetails;
import org.cyk.system.root.business.impl.security.CredentialsDetails;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SmtpPropertiesDetails extends AbstractOutputDetails<SmtpProperties> implements Serializable {

	private static final long serialVersionUID = -7568711914665423264L;

	@Input @InputText private String from;
	@IncludeInputs private ServiceDetails service = new ServiceDetails();
	@IncludeInputs private CredentialsDetails credentials = new CredentialsDetails();
	
	public SmtpPropertiesDetails(SmtpProperties smtpProperties) {
		super(smtpProperties);
	}
	
	@Override
	public void setMaster(SmtpProperties smtpProperties) {
		super.setMaster(smtpProperties);
		if(master!=null){
			service.setMaster(smtpProperties.getService());
			from = smtpProperties.getFrom();
			credentials.setMaster(smtpProperties.getCredentials());
		}
	}
	
	public static final String FIELD_FROM = "from";
	public static final String FIELD_SERVICE = "service";
	public static final String FIELD_CREDENTIALS = "credentials";


}
