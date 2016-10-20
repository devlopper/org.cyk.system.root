package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.language.LanguageCollectionDetails;
import org.cyk.system.root.business.impl.party.AbstractPartyDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public abstract class AbstractPersonDetails<PERSON extends AbstractIdentifiable> extends AbstractPartyDetails<PERSON> implements Serializable {

	private static final long serialVersionUID = 1165482775425753790L;

	@Input @InputText private String lastnames,surname,sex,birthDate,birthLocation,title,nationality,bloodGroup,jobFunction/*,maritalStatus*/;
	
	@IncludeInputs(layout=Layout.VERTICAL) 
	protected LanguageCollectionDetails languageCollection;
	
	public AbstractPersonDetails(PERSON person) {
		super(person);	
	}
	
	@Override
	public void setMaster(PERSON person) {
		super.setMaster(person);
		name = getPerson().getName();
		lastnames = getPerson().getLastnames();
		surname = getPerson().getSurname();
		if(getPerson().getSex()!=null)
			sex = getPerson().getSex().getName();
		
		if(getPerson().getNationality()!=null)
			nationality = RootBusinessLayer.getInstance().getFormatterBusiness().format(getPerson().getNationality());
		
		if(getPerson().getBirthDate()!=null)
			birthDate = inject(TimeBusiness.class).formatDate(getPerson().getBirthDate());
		
		if(getPerson().getExtendedInformations()!=null){
			if(getPerson().getExtendedInformations().getTitle()!=null)
				title = getPerson().getExtendedInformations().getTitle().getName();
			if(getPerson().getExtendedInformations().getBirthLocation()!=null)
				birthLocation = RootBusinessLayer.getInstance().getFormatterBusiness().format(getPerson().getExtendedInformations().getBirthLocation());
			getLanguageCollection().setMaster(getPerson().getExtendedInformations().getLanguageCollection());
		}
		
		if(getPerson().getJobInformations()!=null){
			if(getPerson().getJobInformations().getFunction()!=null)
				jobFunction = getPerson().getJobInformations().getFunction().getName();
		}
		
		if(getPerson().getMedicalInformations()!=null){
			if(getPerson().getMedicalInformations().getBloodGroup()!=null)
				bloodGroup = getPerson().getMedicalInformations().getBloodGroup().getName();
		}
	}
	
	public LanguageCollectionDetails getLanguageCollection(){
		if(languageCollection == null)
			languageCollection = new LanguageCollectionDetails();
		return languageCollection;
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
	public static final String FIELD_BIRTH_DATE = "birthDate";
	public static final String FIELD_BIRTH_LOCATION = "birthLocation";
	public static final String FIELD_SEX = "sex";
	public static final String FIELD_NATIONALITY = "nationality";
	public static final String FIELD_BLOOD_GROUP = "bloodGroup";
	public static final String FIELD_JOB_FUNCTION = "jobFunction";
	public static final String FIELD_LANGUAGE_COLLECTION = "languageCollection";
}
