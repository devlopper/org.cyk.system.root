package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.party.person.AbstractMedicalInformationsJoinBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.party.person.AbstractMedicalInformationsJoin;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.persistence.api.party.person.AbstractMedicalInformationsJoinDao;

public abstract class AbstractMedicalInformationsJoinBusinessImpl<INFORMATIONS extends AbstractMedicalInformationsJoin,DAO extends AbstractMedicalInformationsJoinDao<INFORMATIONS>> extends AbstractTypedBusinessService<INFORMATIONS, DAO> implements AbstractMedicalInformationsJoinBusiness<INFORMATIONS>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractMedicalInformationsJoinBusinessImpl(DAO dao) {
		super(dao); 
	}
	
	@Override
	public Collection<INFORMATIONS> findByMedicalInformations(MedicalInformations medicalInformations) {
		return dao.readByMedicalInformations(medicalInformations);
	}
}
