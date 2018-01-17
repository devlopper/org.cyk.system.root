package org.cyk.system.root.business.impl.globalidentification;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.RudBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.MicrosoftExcelHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;

@Stateless @TransactionAttribute(TransactionAttributeType.NEVER)
public class GlobalIdentifierBusinessImpl extends AbstractBean implements GlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = 7024534251413461778L;

	private static final String[] RELATED = {GlobalIdentifier.FIELD_IMAGE,GlobalIdentifier.FIELD_SUPPORTING_DOCUMENT,GlobalIdentifier.FIELD_BIRTH_LOCATION
			,GlobalIdentifier.FIELD_DEATH_LOCATION};
	
	@Inject private GlobalIdentifierDao globalIdentifierDao;
	
	@Override
	public GlobalIdentifier find(String identifier) {
		return globalIdentifierDao.read(identifier);
	}
	
	@Override
	public Boolean isCreatable(Class<? extends AbstractIdentifiable> aClass) {
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean isReadable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || inject(RudBusiness.class).isReadable(identifiable.getGlobalIdentifier().getRud());
	}

	@Override
	public Boolean isUpdatable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || inject(RudBusiness.class).isUpdatable(identifiable.getGlobalIdentifier().getRud());
	}

	@Override
	public Boolean isDeletable(AbstractIdentifiable identifiable) {
		return identifiable.getGlobalIdentifier()==null || inject(RudBusiness.class).isDeletable(identifiable.getGlobalIdentifier().getRud());
	}

	@Override @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public GlobalIdentifier create(GlobalIdentifier globalIdentifier) {
		if(StringUtils.isBlank(globalIdentifier.getIdentifier())){
			globalIdentifier.setIdentifier(InstanceHelper.getInstance().generateFieldValue(globalIdentifier, GlobalIdentifier.FIELD_IDENTIFIER, String.class));
			/*
			globalIdentifier.setIdentifier(globalIdentifier.getIdentifiable().getClass().getSimpleName()+Constant.CHARACTER_UNDESCORE);
			if(globalIdentifier.getIdentifiable()!=null && StringUtils.isNotBlank(globalIdentifier.getIdentifiable().getCode())){
				globalIdentifier.setIdentifier(globalIdentifier.getIdentifier()+globalIdentifier.getIdentifiable().getCode()+Constant.CHARACTER_UNDESCORE);
			}
			globalIdentifier.setIdentifier(globalIdentifier.getIdentifier()+System.currentTimeMillis()+Constant.CHARACTER_UNDESCORE+RandomStringUtils.randomAlphanumeric(10));
			*/
		}
		
		globalIdentifier.setCreationDate(inject(TimeBusiness.class).findUniversalTimeCoordinated());
		globalIdentifier.setCreatedBy(globalIdentifier.getProcessing().getParty() == null ? RootBusinessLayer.getInstance().getApplication() : globalIdentifier.getProcessing().getParty());
		
		if(globalIdentifier.getOwner()==null)
			globalIdentifier.setOwner(globalIdentifier.getCreatedBy());
		globalIdentifier.setCreationDate(inject(TimeBusiness.class).findUniversalTimeCoordinated());
		
		inject(GenericBusiness.class).create(globalIdentifier,RELATED);
		globalIdentifierDao.create(globalIdentifier);
		logTrace("global identifier {} created", globalIdentifier.getIdentifier());
		return globalIdentifier;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public GlobalIdentifier update(GlobalIdentifier globalIdentifier) {
		//logTrace("Updating global identifier {}", globalIdentifier);
		/*if(inject(GenericBusiness.class).isNotIdentified(globalIdentifier.getImage())){
			inject(FileBusiness.class).create(globalIdentifier.getImage());
		}else if(globalIdentifier.getImage()!=null)
			inject(FileBusiness.class).update(globalIdentifier.getImage());
		*/
		inject(GenericBusiness.class).save(globalIdentifier,RELATED);
		
		globalIdentifier = globalIdentifierDao.update(globalIdentifier);;
		logTrace("global identifier {} updated", globalIdentifier.getIdentifier());
		return globalIdentifier;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public GlobalIdentifier delete(GlobalIdentifier globalIdentifier) {
		inject(GenericBusiness.class).delete(globalIdentifier,RELATED);
		globalIdentifierDao.delete(globalIdentifier);
		logTrace("global identifier {} deleted", globalIdentifier.getIdentifier());
		return globalIdentifier;
	}

	@Override
	public Collection<GlobalIdentifier> findAll() {
		return globalIdentifierDao.readAll();
	}
	
	@Override
	public Long countAll() {
		return globalIdentifierDao.countAll();
	}
	
	@Override
	public Collection<GlobalIdentifier> findAll(DataReadConfiguration dataReadConfiguration) {
		if(dataReadConfiguration==null)
			globalIdentifierDao.getDataReadConfig().clear();
		else
			globalIdentifierDao.getDataReadConfig().set(dataReadConfiguration);
		return globalIdentifierDao.readAll();
	}
	
	@Override
	public Collection<GlobalIdentifier> findByFilter(Filter<GlobalIdentifier> filter,DataReadConfiguration dataReadConfiguration) {
		if(filter.isNull())
    		return findAll(dataReadConfiguration);
		return globalIdentifierDao.readByFilter(filter,dataReadConfiguration);
	}
	
	@Override
	public Long countByFilter(Filter<GlobalIdentifier> filter, DataReadConfiguration dataReadConfiguration) {
		if(filter.isNull())
    		return countAll();
		return globalIdentifierDao.countByFilter(filter,dataReadConfiguration);
	}
	
	@Override
	public Collection<GlobalIdentifier> instanciateMany(MicrosoftExcelHelper.Workbook.Sheet sheet,InstanceHelper.Builder.OneDimensionArray<GlobalIdentifier> instanceBuilder) {
		Collection<GlobalIdentifier> identifiables = new ArrayList<>();
		InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<GlobalIdentifier> instancesBuilder = new InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<GlobalIdentifier>(null);
		instancesBuilder.setOneDimensionArray(instanceBuilder);
		
		if(sheet.getValues()!=null)
			identifiables.addAll((Collection<GlobalIdentifier>)instancesBuilder.setInput(sheet.getValues()).execute());
		if(sheet.getIgnoreds()!=null)
			identifiables.addAll((Collection<GlobalIdentifier>)instancesBuilder.setInput(sheet.getIgnoreds()).execute());
		return identifiables;
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
	
	/**/
	
	public static class BuilderOneDimensionArray extends org.cyk.utility.common.helper.InstanceHelper.Builder.OneDimensionArray.Adapter.Default<GlobalIdentifier> implements Serializable{
    	private static final long serialVersionUID = 1L;
    	
    	public static String IMAGE_DIRECTORY_PATH = null;
    	
    	private String imageDirectoryPath = IMAGE_DIRECTORY_PATH;
    	
    	public BuilderOneDimensionArray() {
			super(GlobalIdentifier.class);
			addParameterArrayElementString(GlobalIdentifier.FIELD_IDENTIFIER,GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME);
			addParameterArrayElementStringIndexInstance(10,FieldHelper.getInstance().buildPath(GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE));
			addParameterArrayElementStringIndexInstance(11,FieldHelper.getInstance().buildPath(GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_TO_DATE));
			addParameterArrayElementStringIndexInstance(12,FieldHelper.getInstance().buildPath(GlobalIdentifier.FIELD_IMAGE,File.FIELD_URI));
			addParameterArrayElementStringIndexInstance(13,FieldHelper.getInstance().buildPath(GlobalIdentifier.FIELD_IMAGE,File.FIELD_EXTENSION));
		}
    	
    	protected GlobalIdentifier __execute__() {
    		GlobalIdentifier globalIdentifier = super.__execute__();
			if(globalIdentifier.getImage()!=null && !StringHelper.getInstance().isBlank(globalIdentifier.getImage().getUri())){
				String name = globalIdentifier.getImage().getUri()+"."+globalIdentifier.getImage().getExtension();
				String path = imageDirectoryPath+name;
				byte[] bytes;
				try {
					bytes = IOUtils.toByteArray(new FileInputStream(path));
					globalIdentifier.setImage(inject(FileBusiness.class).process(bytes, name));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(globalIdentifier.getImage()!=null){
				globalIdentifier.setImage(null);
			}
			return globalIdentifier;
    	}
    	
    	@Override
    	protected GlobalIdentifier instanciate() {
    		GlobalIdentifier globalIdentifier = super.instanciate();
    		if(!StringHelper.getInstance().isBlank((String)getInput()[12]))
    			globalIdentifier.setImage(new File());
    		return globalIdentifier;
    	}
	}

}
