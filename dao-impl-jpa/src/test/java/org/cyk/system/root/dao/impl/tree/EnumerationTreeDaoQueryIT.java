package org.cyk.system.root.dao.impl.tree;

import javax.inject.Inject;

import org.cyk.system.root.dao.api.AbstractEnumerationTreeDao;
import org.cyk.system.root.dao.api.EnumerationTreeDao;
import org.cyk.system.root.dao.api.TypedDao;
import org.cyk.system.root.dao.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.dao.impl.AbstractEnumerationTreeDaoImpl;
import org.cyk.system.root.dao.impl.AbstractPersistenceService;
import org.cyk.system.root.dao.impl.AbstractQueryIT;
import org.cyk.system.root.dao.impl.generic.Transaction;
import org.cyk.system.root.dao.impl.pattern.tree.NestedSetNodeDaoImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.EnumerationTree;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;

public class EnumerationTreeDaoQueryIT extends AbstractQueryIT<EnumerationTree> {
	
	@Inject private EnumerationTreeDao dao;
	
	private EnumerationTree continent,country,city;
	
	private EnumerationTree afrique,europe;
	private EnumerationTree afriqueCI,afriqueGH,europeFR,europeIT;
	private EnumerationTree afriqueCIAbidjan,afriqueCIBouake,afriqueGHAccra,afriqueGHNoe,europeFRParis,europeFRLyon,europeITRome,europeITViennes;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return createDeployment(NestedSet.class.getPackage(),EnumerationTree.class.getPackage(),
				NestedSetNodeDao.class.getPackage(),AbstractEnumerationTreeDao.class.getPackage(),
				NestedSetNodeDaoImpl.class.getPackage(),AbstractEnumerationTreeDaoImpl.class.getPackage());
	}
	
	@Override
	protected TypedDao<EnumerationTree> dao() {
		return dao;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends AbstractIdentifiable>[] entitiesToDelete() {
		return (Class<? extends AbstractIdentifiable>[]) new Class<?>[]{EnumerationTree.class,NestedSetNode.class,NestedSet.class};
	}
		
	@Override
	protected void populate() {
		
		continent = dao.create(new EnumerationTree(null, "Continent"));
		country = dao.create(new EnumerationTree(continent, "Country"));
		city = dao.create(new EnumerationTree(country, "City"));
		
		afrique = dao.create(new EnumerationTree(null,continent,"Afrique"));
		afriqueCI = dao.create(new EnumerationTree(afrique,country,"Cote Ivoire"));
		afriqueCIAbidjan = dao.create(new EnumerationTree(afriqueCI,city,"Abidjan"));
		afriqueCIBouake = dao.create(new EnumerationTree(afriqueCI,city,"Bouake"));
		afriqueGH = dao.create(new EnumerationTree(afrique,country,"Ghana"));
		afriqueGHAccra = dao.create(new EnumerationTree(afriqueGH,city,"Accra"));
		afriqueGHNoe = dao.create(new EnumerationTree(afriqueGH,city,"Noe"));
		
		europe = dao.create(new EnumerationTree(null,continent,"Europe"));
		europeFR = dao.create(new EnumerationTree(europe,country,"France"));
		europeFRParis = dao.create(new EnumerationTree(europeFR,city,"Paris"));
		europeFRLyon = dao.create(new EnumerationTree(europeFR,city,"Lyon"));
		europeIT = dao.create(new EnumerationTree(europe,country,"Italie"));
		europeITRome = dao.create(new EnumerationTree(europeIT,city,"Rome"));
		europeITViennes = dao.create(new EnumerationTree(europeIT,city,"Viennes"));
		
	}
	
	@Test
	public void show() {
		System.out.println("All : "+dao.select().all());
		showByParent(afrique);
		showByParent(europe);
		showByParent(afriqueCI);
		showByParent(afriqueGH);
		showByParent(europeFR);
		showByParent(europeIT);
		showByParent(afriqueCIAbidjan);
		System.out.println("----------------------------------------");
		showByType(continent);
		showByType(country);
		showByType(city);
		System.out.println("----------------------------------------");
		showByParentByType(afrique, country);
		showByParentByType(afrique, city);
		showByParentByType(europe, country);
		showByParentByType(europe, city);
		
		//Assert.assertTrue(dao.readByParent(a1).size()==5);
	}
	
	private void showByParent(EnumerationTree parent){
		System.out.println(parent+" : "+dao.readByParent(parent));
	}
	
	private void showByType(EnumerationTree type){
		System.out.println("All "+type+" : "+dao.readByType(type));
	}
	
	private void showByParentByType(EnumerationTree parent,EnumerationTree type){
		System.out.println("All "+type+" of "+parent+": "+dao.readByParentByType(parent,type));
	}
	
	@Test
	public void cascadeDeleteContinent() {
		Assert.assertEquals("Not 17 found", 17,dao.select().all().size());
		new Transaction(transaction,(AbstractPersistenceService<?>) genericDao,null) {
			@Override
			public void _execute_() {
				dao.delete(afrique);
				Assert.assertEquals("Not 10 found", 10,dao.select().all().size());
			}
		}.run();
	}
	
	/**/

}
