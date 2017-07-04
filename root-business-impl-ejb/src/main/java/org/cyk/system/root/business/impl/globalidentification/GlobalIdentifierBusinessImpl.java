package org.cyk.system.root.business.impl.globalidentification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Rud;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

@Stateless @TransactionAttribute(TransactionAttributeType.NEVER)
public class GlobalIdentifierBusinessImpl extends AbstractBean implements GlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = 7024534251413461778L;

	@Inject private GlobalIdentifierDao globalIdentifierDao;
	
	@Override
	public Boolean isCreatable(Class<? extends AbstractIdentifiable> aClass) {
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean isReadable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || isReadable(identifiable.getGlobalIdentifier().getRud());
	}

	@Override
	public Boolean isUpdatable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || isUpdatable(identifiable.getGlobalIdentifier().getRud());
	}

	@Override
	public Boolean isDeletable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || isDeletable(identifiable.getGlobalIdentifier().getRud());
	}

	@Override @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public GlobalIdentifier create(GlobalIdentifier globalIdentifier) {
		if(StringUtils.isBlank(globalIdentifier.getIdentifier())){
			if(globalIdentifier.getIdentifiable()!=null && StringUtils.isNotBlank(globalIdentifier.getIdentifiable().getCode())){
				globalIdentifier.setIdentifier(globalIdentifier.getIdentifiable().getClass().getSimpleName()+Constant.CHARACTER_UNDESCORE
						+globalIdentifier.getIdentifiable().getCode()+Constant.CHARACTER_UNDESCORE);
			}
			globalIdentifier.setIdentifier(StringUtils.defaultString(globalIdentifier.getIdentifier())+System.currentTimeMillis()+Constant.CHARACTER_UNDESCORE
					+RandomStringUtils.randomAlphanumeric(10));
		}
		
		inject(GenericBusiness.class).createIfNotIdentified(globalIdentifier.getImage(),globalIdentifier.getBirthLocation(),globalIdentifier.getDeathLocation());
		
		globalIdentifier.setCreationDate(inject(TimeBusiness.class).findUniversalTimeCoordinated());
		globalIdentifier.setCreatedBy(globalIdentifier.getProcessing().getParty() == null ? RootBusinessLayer.getInstance().getApplication() : globalIdentifier.getProcessing().getParty());
		
		if(globalIdentifier.getOwner()==null)
			globalIdentifier.setOwner(globalIdentifier.getCreatedBy());
		globalIdentifier.setCreationDate(inject(TimeBusiness.class).findUniversalTimeCoordinated());
		return globalIdentifierDao.create(globalIdentifier);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public GlobalIdentifier update(GlobalIdentifier globalIdentifier) {
		logTrace("Updating global identifier {}", globalIdentifier);
		if(inject(GenericBusiness.class).isNotIdentified(globalIdentifier.getImage())){
			inject(FileBusiness.class).create(globalIdentifier.getImage());
		}else if(globalIdentifier.getImage()!=null)
			inject(FileBusiness.class).update(globalIdentifier.getImage());
		return globalIdentifierDao.update(globalIdentifier);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public GlobalIdentifier delete(GlobalIdentifier globalIdentifier) {
		logTrace("Deleting global identifier {}", globalIdentifier);
		inject(GenericBusiness.class).deleteIfIdentified(globalIdentifier,GlobalIdentifier.FIELD_IMAGE,GlobalIdentifier.FIELD_SUPPORTING_DOCUMENT);
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
	
	/**/
	
	public static interface Listener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;

			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				
				
			}
			
		}
		
	}
}
