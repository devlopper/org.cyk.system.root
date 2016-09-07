package org.cyk.system.root.business.impl.globalidentification;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Rud;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;
import org.cyk.utility.common.cdi.AbstractBean;

public class GlobalIdentifierBusinessImpl extends AbstractBean implements GlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = 7024534251413461778L;

	@Inject private GlobalIdentifierDao globalIdentifierDao;
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isCreatable(Class<? extends AbstractIdentifiable> aClass) {
		return Boolean.TRUE;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isReadable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || isReadable(identifiable.getGlobalIdentifier().getRud());
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isUpdatable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || isUpdatable(identifiable.getGlobalIdentifier().getRud());
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isDeletable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || isDeletable(identifiable.getGlobalIdentifier().getRud());
	}

	@Override
	public GlobalIdentifier create(GlobalIdentifier globalIdentifier) {
		logTrace("Creating global identifier {}", globalIdentifier);
		/*if(StringUtils.isBlank(globalIdentifier.getCode()))
			globalIdentifier.setCode(RootBusinessLayer.getInstance().getStringGeneratorBusiness()
					.generate(ValueGenerator.GLOBAL_IDENTIFIER_CODE_IDENTIFIER, globalIdentifier.getIdentifiable()));*///TODO handle duplicate by using lock write
		if(globalIdentifier.getOwner()==null)
			globalIdentifier.setOwner(globalIdentifier.getCreatedBy());
		globalIdentifier.setCreationDate(inject(TimeBusiness.class).findUniversalTimeCoordinated());
		return globalIdentifierDao.create(globalIdentifier);
	}
	
	@Override
	public GlobalIdentifier update(GlobalIdentifier globalIdentifier) {
		logTrace("Updating global identifier {}", globalIdentifier);
		return globalIdentifierDao.update(globalIdentifier);
	}
	
	@Override
	public GlobalIdentifier delete(GlobalIdentifier globalIdentifier) {
		logTrace("Deleting global identifier {}", globalIdentifier);
		if(globalIdentifier.getImage()!=null){
			inject(FileBusiness.class).delete(globalIdentifier.getImage());
			globalIdentifier.setImage(null);
		}
		return globalIdentifierDao.delete(globalIdentifier);
	}

	@Override
	public Collection<GlobalIdentifier> findAll() {
		return globalIdentifierDao.readAll();
	}
	
	/**/
	
	public static Boolean isReadable(Rud rud) {
		return rud.getReadable()==null || rud.getReadable();
	}

	public static Boolean isUpdatable(Rud rud) {
		return rud.getUpdatable()==null || rud.getUpdatable();
	}

	public static Boolean isDeletable(Rud rud) {
		return rud.getDeletable()==null || rud.getDeletable();
	}
}
