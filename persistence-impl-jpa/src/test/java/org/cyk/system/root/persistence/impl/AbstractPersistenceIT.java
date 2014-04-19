package org.cyk.system.root.persistence.impl;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import lombok.Getter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.test.AbstractIntegrationTest;
import org.cyk.utility.common.test.ArchiveBuilder;
import org.cyk.utility.common.test.TestMethod;

/**
 * Persistence integration test (IT)
 * @author Komenan Y .Christian
 *
 */ 
public abstract class AbstractPersistenceIT extends AbstractIntegrationTest {
	 
	private static final long serialVersionUID = -3977685343817022628L;

	public static ArchiveBuilder deployment(){
		ArchiveBuilder builder = new ArchiveBuilder();
		builder.create().addClasses(PersistenceIntegrationTestHelper.BASE_CLASSES);
		return builder;
	}
	
	static int i=1;
	static {
		AFTER_CLASS_METHOD = new AbstractMethod<Object, Object>() {
			@Override
			protected Object __execute__(Object parameter) {
				data = false;
				identifiable = null;
				return null;
			}
		};
	}
	
	protected static Boolean data = Boolean.FALSE;
	protected static AbstractIdentifiable identifiable=null;
	
	@Inject @Getter private GenericDao genericDao;
	@Inject private UserTransaction transaction;
	
	@Override
	protected void _before_() throws Exception {
		super._before_();
		if(Boolean.TRUE.equals(data))
			return;
		transaction(new TestMethod() { @Override protected void test() { populate(); data = Boolean.TRUE; } });	
		afterCommit();
	}
	
	protected void afterCommit(){}
	
	@Override
	protected void _execute_() {
		super._execute_();
		transaction(new TestMethod() { @Override protected void test() { create(); } });	
		read(); 
		transaction(new TestMethod() { @Override protected void test() { update(); } });	
		transaction(new TestMethod() { @Override protected void test() { delete(); } });	
		queries();
	}
	
	/**/
	
	protected abstract void populate();
	protected abstract void create();
	protected abstract void read();
	protected abstract void update();
	protected abstract void delete();
	protected abstract void queries();
	
	/* Shortcut */
	
	protected void transaction(final TestMethod method,Class<? extends SQLException> exceptionClassExpected){
		new Transaction(this,transaction,exceptionClassExpected) {
			@Override
			public void _execute_() {
				method.execute();
			}
		}.run();
	}
	
	protected void transaction(final TestMethod method){
		transaction(method, null);
	}
	
	protected void create(AbstractIdentifiable object){
		genericDao.create(object.getClass(), object);
	}
	
	protected void update(AbstractIdentifiable object){
		genericDao.update(object.getClass(), object);
	}
	
}
