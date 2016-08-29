package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.geography.Country;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CountryDetails extends AbstractOutputDetails<Country> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;

	@Input @InputText private String phoneNumberCode;
	
	public CountryDetails(Country country) {
		super(country);
		phoneNumberCode = formatNumber(country.getPhoneNumberCode());
	}

	public static final String FIELD_PHONE_NUMBER_CODE = "phoneNumberCode";
}