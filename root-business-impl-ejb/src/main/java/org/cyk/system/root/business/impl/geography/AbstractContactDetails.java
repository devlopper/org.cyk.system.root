package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class AbstractContactDetails<IDENTIFIABLE extends Contact> extends AbstractOutputDetails<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;

	@Input @InputText protected String value,orderNumber;
	
	public AbstractContactDetails(IDENTIFIABLE contact) {
		super(contact);
		value = formatUsingBusiness(contact);
		orderNumber = formatNumber(contact.getOrderNumber());
	}

	public static final String FIELD_VALUE = "value";
	public static final String FIELD_ORDER_NUMBER = "orderNumber";
}
