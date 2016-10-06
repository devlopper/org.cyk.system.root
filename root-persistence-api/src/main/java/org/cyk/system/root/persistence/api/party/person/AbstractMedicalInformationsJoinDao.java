package org.cyk.system.root.persistence.api.party.person;

import java.util.Collection;

import org.cyk.system.root.model.party.person.AbstractMedicalInformationsJoin;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractMedicalInformationsJoinDao<INFORMATIONS extends AbstractMedicalInformationsJoin> extends TypedDao<INFORMATIONS> {

	Collection<INFORMATIONS> readByMedicalInformations(MedicalInformations medicalInformations);
	
}
