package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Text;

public abstract class AbstractPartyDetails<PARTY extends AbstractIdentifiable> extends AbstractOutputDetails<PARTY> implements Serializable {

	private static final long serialVersionUID = 1165482775425753790L;

	@OutputSeperator(label=@Text(value="model.entity.contactCollection")) 
	@Input @InputText protected FieldValue contactCollection;
	
	public AbstractPartyDetails(PARTY party) {
		super(party);
		if(getParty().getContactCollection()!=null)
			contactCollection = new FieldValue(getParty().getContactCollection());
	}

	/**/
	
	protected abstract Party getParty();
	
	public static final String FIELD_CONTACT_COLLECTION = "contactCollection";
}
