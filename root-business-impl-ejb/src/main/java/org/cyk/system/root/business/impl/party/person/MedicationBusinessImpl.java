package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.MedicationBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.party.person.Medication;
import org.cyk.system.root.persistence.api.party.person.MedicationDao;

public class MedicationBusinessImpl extends AbstractEnumerationBusinessImpl<Medication, MedicationDao> implements MedicationBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MedicationBusinessImpl(MedicationDao dao) {
		super(dao); 
	}   
	
}
