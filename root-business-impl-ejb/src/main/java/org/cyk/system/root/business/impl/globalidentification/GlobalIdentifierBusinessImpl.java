package org.cyk.system.root.business.impl.globalidentification;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;
import org.cyk.utility.common.cdi.AbstractBean;

@Stateless
public class GlobalIdentifierBusinessImpl extends AbstractBean implements GlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = 7024534251413461778L;

	@Inject private GlobalIdentifierDao globalIdentifierDao;
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isCreatable(Class<? extends AbstractIdentifiable> aClass) {
		return Boolean.TRUE;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isReadable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || identifiable.getGlobalIdentifier().getRud().getReadable()==null || identifiable.getGlobalIdentifier().getRud().getReadable();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isUpdatable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || identifiable.getGlobalIdentifier().getRud().getUpdatable()==null || identifiable.getGlobalIdentifier().getRud().getUpdatable();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isDeletable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || identifiable.getGlobalIdentifier().getRud().getDeletable()==null || identifiable.getGlobalIdentifier().getRud().getDeletable();
	}

	@Override
	public GlobalIdentifier create(GlobalIdentifier globalIdentifier) {
		logTrace("Creating global identifier {}", globalIdentifier);
		System.out.println("GlobalIdentifierBusinessImpl.create() : "+ToStringBuilder.reflectionToString(globalIdentifier, ToStringStyle.DEFAULT_STYLE));
		return globalIdentifierDao.create(globalIdentifier);
	}
	
	@Override
	public GlobalIdentifier update(GlobalIdentifier globalIdentifier) {
		logTrace("Updating global identifier {}", globalIdentifier);
		return globalIdentifierDao.update(globalIdentifier);
	}

}
