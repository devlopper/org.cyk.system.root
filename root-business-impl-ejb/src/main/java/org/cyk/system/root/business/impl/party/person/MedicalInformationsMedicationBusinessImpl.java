package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.MedicalInformationsMedicationBusiness;
import org.cyk.system.root.model.party.person.MedicalInformationsMedication;
import org.cyk.system.root.persistence.api.party.person.MedicalInformationsMedicationDao;

public class MedicalInformationsMedicationBusinessImpl extends AbstractMedicalInformationsJoinBusinessImpl<MedicalInformationsMedication, MedicalInformationsMedicationDao> implements MedicalInformationsMedicationBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MedicalInformationsMedicationBusinessImpl(MedicalInformationsMedicationDao dao) {
		super(dao); 
	}   
	
}
