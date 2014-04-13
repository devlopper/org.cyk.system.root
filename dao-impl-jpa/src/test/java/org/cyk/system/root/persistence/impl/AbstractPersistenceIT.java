package org.cyk.system.root.persistence.impl;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import lombok.Getter;

import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.AbstractPersistenceService;
import org.cyk.system.root.persistence.impl.GenericDaoImpl;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.system.root.persistence.impl.Utils;
import org.cyk.utility.common.test.AbstractIntegrationTest;
import org.cyk.utility.common.test.TestMethod;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * Persistence integration test (IT)
 * @author Komenan Y .Christian
 *
 */
public abstract class AbstractPersistenceIT extends AbstractIntegrationTest {
	
	private static final long serialVersionUID = -3977685343817022628L;

	static final Utils UTILS = new Utils();
	
	@SuppressWarnings("unchecked")
	public static Archive<?> createDeployment(Class<?>[] classes){
		JavaArchive archive = ShrinkWrap
				.create(JavaArchive.class)
				.addClass(QueryStringBuilder.class)
				.addClass(PersistenceService.class).addClass(AbstractPersistenceService.class).addClass(GenericDao.class).addClass(GenericDaoImpl.class)
				//.addClasses(classes)
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		
		for(Class<?> clazz : classes)
			if(AbstractIdentifiable.class.isAssignableFrom(clazz))
				for(String name : UTILS.componentNames((Class<? extends AbstractIdentifiable>) clazz))
					archive.addClass(name);
			else
				archive.addClass(clazz);
		
		return archive;
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
