package org.cyk.system.root.business.impl.globalidentification;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;

@Stateless
public class GlobalIdentifierBusinessImpl implements GlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = 7024534251413461778L;

	@Inject private GlobalIdentifierDao globalIdentifierDao;
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isCreatable(Class<? extends AbstractIdentifiable> aClass) {
		return Boolean.TRUE;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isReadable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || identifiable.getGlobalIdentifier().getReadable()==null || identifiable.getGlobalIdentifier().getReadable();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isUpdatable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || identifiable.getGlobalIdentifier().getUpdatable()==null || identifiable.getGlobalIdentifier().getUpdatable();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isDeletable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || identifiable.getGlobalIdentifier().getDeletable()==null || identifiable.getGlobalIdentifier().getDeletable();
	}

	@Override
	public GlobalIdentifier update(GlobalIdentifier globalIdentifier) {
		return globalIdentifierDao.update(globalIdentifier);
	}

}
