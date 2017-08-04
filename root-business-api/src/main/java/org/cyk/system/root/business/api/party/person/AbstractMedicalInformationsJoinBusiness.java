package org.cyk.system.root.business.api.party.person;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.party.person.AbstractMedicalInformationsJoin;
import org.cyk.system.root.model.party.person.MedicalInformations;

public interface AbstractMedicalInformationsJoinBusiness<INFORMATIONS extends AbstractMedicalInformationsJoin> extends TypedBusiness<INFORMATIONS> {

	Collection<INFORMATIONS> findByMedicalInformations(MedicalInformations medicalInformations);
	
}
