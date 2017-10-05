package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.geography.Contact;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractContactDetails<IDENTIFIABLE extends Contact> extends AbstractOutputDetails<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;
	
	public AbstractContactDetails(IDENTIFIABLE contact) {
		super(contact);
	}

}
