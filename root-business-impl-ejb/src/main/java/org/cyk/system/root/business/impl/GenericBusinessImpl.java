package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.AbstractGenericBusinessService;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.utils.IdentifiableCrudExecution;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.system.root.persistence.impl.GenericDaoImpl;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.ThreadPoolExecutor;

@Stateless 
public class GenericBusinessImpl extends AbstractIdentifiableBusinessServiceImpl<AbstractIdentifiable> implements GenericBusiness,Serializable {
	
	private static final long serialVersionUID = -1042342183332719272L;
 
	@Inject private GenericDaoImpl genericDaoImpl;
	
	@Override
	protected PersistenceService<AbstractIdentifiable, Long> getPersistenceService() {
	    return genericDao;
	}
	
	@Override
	protected Class<?> parameterizedClass() {
		return AbstractIdentifiable.class;
	}
	
	@Override
	public AbstractIdentifiable create(AbstractIdentifiable anIdentifiable) {	    
	    TypedBusiness<AbstractIdentifiable> businessBean = inject(BusinessInterfaceLocator.class).injectTypedByObject(anIdentifiable);
	    if(businessBean==null){
	    	System.out.println("GenericBusinessImpl.create() : "+anIdentifiable);
	    	inject(ValidationPolicy.class).validateCreate(anIdentifiable);
	        return genericDao.create(anIdentifiable);
        }else{
        	return businessBean.create(anIdentifiable); 
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AbstractIdentifiable create(AbstractIdentifiable identifiable, Collection<? extends AbstractIdentifiable> identifiables) {
		create(identifiable);
		create((Collection<AbstractIdentifiable>)identifiables);
		return identifiable;
	}
	
	@Override
	public void create(Collection<AbstractIdentifiable> identifiables) {
		if(identifiables==null)
			return;
		for(AbstractIdentifiable identifiable : identifiables)
			create(identifiable);
	}
	
	@Override
	public void create(Collection<AbstractIdentifiable> identifiables,Boolean useThreadPoolExecutor) {
		if(identifiables==null)
			return;
		if(Boolean.TRUE.equals(useThreadPoolExecutor)){
			List<AbstractIdentifiable> list = new ArrayList<>(identifiables);
			Collection<AbstractIdentifiable> identifiables1 = new ArrayList<>(list.subList(0, list.size()/2));
			Collection<AbstractIdentifiable> identifiables2 = new ArrayList<>(list.subList(identifiables1.size(), list.size()));
			ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 150, 1l, TimeUnit.MINUTES, 10000, 1l, TimeUnit.MINUTES, null);
			threadPoolExecutor.execute(new IdentifiableCrudExecution<AbstractIdentifiable>(identifiables1, Crud.CREATE));
			threadPoolExecutor.execute(new IdentifiableCrudExecution<AbstractIdentifiable>(identifiables2, Crud.CREATE));
			threadPoolExecutor.waitTermination();
			//pause(1000 * 3);
		}else{
			for(AbstractIdentifiable identifiable : identifiables)
				create(identifiable);
		}
		
	}
	
	@Override
	public void createIdentifiables(Collection<? extends AbstractIdentifiable> collection,Boolean useThreadPoolExecutor) {
		create(commonUtils.castCollection(collection, AbstractIdentifiable.class),useThreadPoolExecutor);	
	}

	@Override
	public AbstractIdentifiable update(AbstractIdentifiable anObject) {
	    TypedBusiness<AbstractIdentifiable> businessBean = inject(BusinessInterfaceLocator.class).injectTypedByObject(anObject);
	    if(businessBean==null){
	    	inject(ValidationPolicy.class).validateUpdate(anObject);
            return genericDao.update(anObject);
        }else
            return businessBean.update(anObject);   
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AbstractIdentifiable update(AbstractIdentifiable identifiable, Collection<? extends AbstractIdentifiable> identifiables) {
		update(identifiable);
		update((Collection<AbstractIdentifiable>)identifiables);
		return identifiable;
	}
	
	@Override
	public void update(Collection<AbstractIdentifiable> identifiables) {
		if(identifiables==null)
			return;
		for(AbstractIdentifiable identifiable : identifiables)
			update(identifiable);
	}

	@Override
	public AbstractIdentifiable delete(AbstractIdentifiable anObject) {
	    TypedBusiness<AbstractIdentifiable> businessBean = inject(BusinessInterfaceLocator.class).injectTypedByObject(anObject);
        if(businessBean==null){
        	inject(ValidationPolicy.class).validateDelete(anObject);
            return genericDao.delete(anObject);
        }else
            return businessBean.delete(anObject);   
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AbstractIdentifiable delete(AbstractIdentifiable identifiable,Collection<? extends AbstractIdentifiable> identifiables) {
		delete(identifiable);
		delete((Collection<AbstractIdentifiable>)identifiables);
		return identifiable;
	}
	
	@Override
	public void delete(Collection<AbstractIdentifiable> identifiables) {
		if(identifiables==null)
			return;
		for(AbstractIdentifiable identifiable : identifiables)
			delete(identifiable);
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public AbstractGenericBusinessService<AbstractIdentifiable, Long> use(Class<? extends AbstractIdentifiable> aClass) {
		genericDao.use(aClass);
		return this;
	}
	
	@Override
	public AbstractIdentifiable save(AbstractIdentifiable identifiable) {
	    if(identifiable.getIdentifier()==null)
	        return create(identifiable);
	    else 
	        return update(identifiable);
	}
	
	@SuppressWarnings("unchecked")
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public <T extends AbstractIdentifiable> T load(Class<T> aClass, Long identifier) {
	    TypedBusiness<T> businessBean = (TypedBusiness<T>) BusinessInterfaceLocator.getInstance().injectTyped((Class<AbstractIdentifiable>) aClass);
	    if(businessBean==null)
	        return (T) use(aClass).find(identifier);
	    else
	        return businessBean.load(identifier);
	}
	
	@Override 
	public <IDENTIFIABLE extends AbstractIdentifiable> File createReportFile(IDENTIFIABLE identifiable,String reportTemplateCode,Locale locale){
		return inject(BusinessInterfaceLocator.class).injectTypedByObject(identifiable).createReportFile(identifiable, reportTemplateCode, locale);
	}
	
	@Override 
	public <IDENTIFIABLE extends AbstractIdentifiable> File createReportFile(IDENTIFIABLE identifiable,String reportTemplateCode){
		return inject(BusinessInterfaceLocator.class).injectTypedByObject(identifiable).createReportFile(identifiable, reportTemplateCode);
	}
	
	@Override 
	public <IDENTIFIABLE extends AbstractIdentifiable> Collection<File> createReportFiles(Collection<IDENTIFIABLE> identifiables,String reportTemplateCode){
		Collection<File> files = new ArrayList<>();
		for(IDENTIFIABLE identifiable : identifiables)
			files.add(createReportFile(identifiable, reportTemplateCode));
		return files;
	}
	
	@Override
	public AbstractIdentifiable refresh(AbstractIdentifiable identifiable) {
	    if(identifiable.getIdentifier()==null)
	        return identifiable;
	    return genericDao.refresh(identifiable);
	}

	@Override
	public void flushEntityManager() {
		genericDaoImpl.getEntityManager().flush();
		genericDaoImpl.getEntityManager().clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractIdentifiable find(String identifiableClassIdentifier, String code) {
		BusinessEntityInfos businessEntityInfos = inject(ApplicationBusiness.class).findBusinessEntityInfos(identifiableClassIdentifier);
		if(businessEntityInfos==null)
			exceptionUtils().exception("no.businessEntityInfos."+identifiableClassIdentifier);
		return inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)businessEntityInfos.getClazz()).read(code);
	}

	
	@Override
	public <T extends AbstractIdentifiable> void deleteByCodes(Class<T> aClass,Collection<String> codes) {
		inject(BusinessInterfaceLocator.class).injectTyped(aClass).delete(new HashSet<>(codes));
	}

	@Override
	public <T extends AbstractIdentifiable> void deleteByCode(Class<T> aClass,String code) {
		deleteByCodes(aClass, Arrays.asList(code));
	}

	
}
