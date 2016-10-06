package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.MedicalInformationsAllergyBusiness;
import org.cyk.system.root.model.party.person.MedicalInformationsAllergy;
import org.cyk.system.root.persistence.api.party.person.MedicalInformationsAllergyDao;

public class MedicalInformationsAllergyBusinessImpl extends AbstractMedicalInformationsJoinBusinessImpl<MedicalInformationsAllergy, MedicalInformationsAllergyDao> implements MedicalInformationsAllergyBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MedicalInformationsAllergyBusinessImpl(MedicalInformationsAllergyDao dao) {
		super(dao); 
	}   
	
}
