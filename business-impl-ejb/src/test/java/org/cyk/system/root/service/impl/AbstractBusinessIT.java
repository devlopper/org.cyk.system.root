package org.cyk.system.root.service.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.cyk.system.root.persistence.impl.PersistenceIntegrationTestHelper;
import org.cyk.utility.common.test.ArchiveBuilder;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractBusinessIT {

	public static ArchiveBuilder deployment(Class<?>[] classes){
		ArchiveBuilder builder = new ArchiveBuilder();
		builder.create().addClasses(PersistenceIntegrationTestHelper.BASE_CLASSES).business(classes);
		return builder;
	}
	
	@Inject protected UserTransaction transaction;
	@PersistenceContext protected EntityManager entityManager;
	private Boolean dataInited=Boolean.FALSE;
	
	protected void initialData() throws Exception {
		transaction.begin();
		entityManager.joinTransaction();
		_initialData_();
		transaction.commit();
	}
	
	protected void _initialData_() throws Exception {}
	
	protected Boolean initData(){
		return Boolean.TRUE;
	}
	
	protected Boolean initDataOnce(){
		return Boolean.FALSE;
	}
	
	@Before
	public final void before() throws Exception {
		_before_();
	}
	
	protected void _before_() throws Exception{
		if(Boolean.TRUE.equals(initData())){
			if(Boolean.FALSE.equals(initDataOnce()) )
				initialData();
			else if(Boolean.TRUE.equals(initDataOnce()) && Boolean.FALSE.equals(dataInited) ){
				initialData();
				dataInited = Boolean.TRUE;
			}
		}
	}
	
	@After
	public final void after() throws Exception {
		_after_();
	}
	
	protected void _after_() throws Exception {
		
	}
	
	
}
