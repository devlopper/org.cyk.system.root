package org.cyk.system.root.dao.impl;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.cyk.system.root.dao.api.GenericDao;
import org.cyk.system.root.dao.api.PersistenceService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.EnumerationTree;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.utility.common.AbstractTest;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

/**
 * Persistence integration test (IT)
 * @author Komenan Y .Christian
 *
 */
@RunWith(Arquillian.class)
public abstract class AbstractPersistenceIT extends AbstractTest {
	
	@Inject protected GenericDao genericDao;
	@Inject protected UserTransaction transaction;
	
	public static Archive<?> createDeployment(Package...packages){
		return ShrinkWrap
				.create(JavaArchive.class)
				.addPackage(PersistenceService.class.getPackage()).addPackage(AbstractPersistenceService.class.getPackage())
				.addPackages(false,packages)
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	public static Archive<?> createDeployment(Class<?>...classes){
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClass(PersistenceService.class).addClass(AbstractPersistenceService.class).addClass(GenericDao.class).addClass(GenericDaoImpl.class)
				.addClasses(classes)
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	protected void cleanDatabase(){
		for(Class<? extends AbstractIdentifiable> from : entitiesToDelete()){
			for(AbstractIdentifiable identifiable : genericDao.use(from).select().all()){
				detach(identifiable);
				genericDao.delete(from, identifiable);
			}
			//System.out.println("Entities "+from.getSimpleName()+" deleted");
		}
	}
	
	protected void detach(AbstractIdentifiable identifiable){
		if(identifiable instanceof NestedSetNode){
			((NestedSetNode)identifiable).getSet().setRoot(null);
			genericDao.update(NestedSet.class, ((NestedSetNode)identifiable).getSet());
			((NestedSetNode)identifiable).setParent(null);
			((NestedSetNode)identifiable).setSet(null);
			genericDao.update(NestedSetNode.class, identifiable);
		}else if(identifiable instanceof NestedSet){
			//((NestedSet)identifiable).setRoot(null);
		}else if(identifiable instanceof EnumerationTree){
			((EnumerationTree)identifiable).setNode(null);
			((EnumerationTree)identifiable).setType(null);
		}
	}
	
	protected Class<? extends AbstractIdentifiable>[] entitiesToDelete(){return null;}
	
}
