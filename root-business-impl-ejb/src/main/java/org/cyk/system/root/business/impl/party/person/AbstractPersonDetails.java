package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.party.AbstractPartyDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public abstract class AbstractPersonDetails<PERSON extends AbstractIdentifiable> extends AbstractPartyDetails<PERSON> implements Serializable {

	private static final long serialVersionUID = 1165482775425753790L;

	@Input @InputText private String lastnames,surname,sex,birthDate,birthLocation,title,nationality/*,maritalStatus*/;
	
	public AbstractPersonDetails(PERSON person) {
		super(person);
		lastnames = getPerson().getLastnames();
		surname = getPerson().getSurname();
		if(getPerson().getSex()!=null)
			sex = getPerson().getSex().getName();
		
		if(getPerson().getNationality()!=null)
			nationality = RootBusinessLayer.getInstance().getFormatterBusiness().format(getPerson().getNationality());
		
		if(person.getBirthDate()!=null)
			birthDate = RootBusinessLayer.getInstance().getTimeBusiness().formatDate(getPerson().getBirthDate());
		
		if(getPerson().getExtendedInformations()!=null){
			if(getPerson().getExtendedInformations().getTitle()!=null)
				title = getPerson().getExtendedInformations().getTitle().getName();
			if(getPerson().getExtendedInformations().getBirthLocation()!=null)
				birthLocation = RootBusinessLayer.getInstance().getFormatterBusiness().format(getPerson().getExtendedInformations().getBirthLocation());
		}
	}
	
	@Override
	protected Party getParty() {
		return getPerson();
	}

	/**/
	
	protected abstract Person getPerson();
	
	/**/
	
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_LASTNAMES = "lastnames";
	public static final String FIELD_SURNAME = "surname";
	public static final String FIELD_BIRTHDATE = "birthDate";
	public static final String FIELD_BIRTHLOCATION = "birthLocation";
	public static final String FIELD_SEX = "sex";
}
