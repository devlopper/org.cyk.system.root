package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.MedicalInformationsBusiness;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.BloodGroupDao;
import org.cyk.system.root.persistence.api.party.person.MedicalInformationsDao;

public class MedicalInformationsBusinessImpl extends AbstractPersonExtendedInformationsBusinessImpl<MedicalInformations, MedicalInformationsDao> implements MedicalInformationsBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MedicalInformationsBusinessImpl(MedicalInformationsDao dao) {
		super(dao); 
	}

	@Override
	public MedicalInformations instanciateOneRandomly(Person person) {
		MedicalInformations medicalInformations = instanciateOne();
		medicalInformations.setBloodGroup(inject(BloodGroupDao.class).readOneRandomly());
		medicalInformations.setParty(person);
		return medicalInformations;
	}   
	
}
