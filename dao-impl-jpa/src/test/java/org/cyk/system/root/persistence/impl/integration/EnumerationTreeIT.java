package org.cyk.system.root.persistence.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.persistence.api.AbstractEnumerationTreeDao;
import org.cyk.system.root.persistence.api.EnumerationTreeDao;
import org.cyk.system.root.model.EnumerationTree;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.impl.AbstractEnumerationTreeDaoImpl;
import org.cyk.system.root.persistence.impl.AbstractPersistenceIT;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.test.TestMethod;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert; 

public class EnumerationTreeIT extends AbstractPersistenceIT {

	@Deployment
	public static Archive<?> createDeployment() {
		return createDeployment(new Class<?>[]{NestedSet.class,NestedSetNode.class,EnumerationTree.class,AbstractEnumerationTreeDao.class,
				AbstractEnumerationTreeDaoImpl.class});
	} 
	
	@Inject private EnumerationTreeDao enumerationTreeDao;
	
	private static EnumerationTree continent,country,city;
	
	private static EnumerationTree afrique,europe;
	private static EnumerationTree afriqueCI,afriqueGH,europeFR,europeIT;
	static EnumerationTree afriqueCIAbidjan,afriqueCIBouake,afriqueGHAccra,afriqueGHNoe,europeFRParis,europeFRLyon,europeITRome,europeITViennes;
	
	@Override
	protected void populate() {
		continent = enumerationTreeDao.create(new EnumerationTree(null, "Continent"));
		country = enumerationTreeDao.create(new EnumerationTree(continent, "Country"));
		city = enumerationTreeDao.create(new EnumerationTree(country, "City"));
		
		afrique = enumerationTreeDao.create(new EnumerationTree(null,continent,"Afrique"));
		afriqueCI = enumerationTreeDao.create(new EnumerationTree(afrique,country,"Cote Ivoire"));
		afriqueCIAbidjan = enumerationTreeDao.create(new EnumerationTree(afriqueCI,city,"Abidjan"));
		afriqueCIBouake = enumerationTreeDao.create(new EnumerationTree(afriqueCI,city,"Bouake"));
		afriqueGH = enumerationTreeDao.create(new EnumerationTree(afrique,country,"Ghana"));
		afriqueGHAccra = enumerationTreeDao.create(new EnumerationTree(afriqueGH,city,"Accra"));
		afriqueGHNoe = enumerationTreeDao.create(new EnumerationTree(afriqueGH,city,"Noe"));
		
		europe = enumerationTreeDao.create(new EnumerationTree(null,continent,"Europe"));
		europeFR = enumerationTreeDao.create(new EnumerationTree(europe,country,"France"));
		europeFRParis = enumerationTreeDao.create(new EnumerationTree(europeFR,city,"Paris"));
		europeFRLyon = enumerationTreeDao.create(new EnumerationTree(europeFR,city,"Lyon"));
		europeIT = enumerationTreeDao.create(new EnumerationTree(europe,country,"Italie"));
		europeITRome = enumerationTreeDao.create(new EnumerationTree(europeIT,city,"Rome"));
		europeITViennes = enumerationTreeDao.create(new EnumerationTree(europeIT,city,"Viennes"));
	}

	@Override
	protected void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void read() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void queries() {
		System.out.println("All : "+enumerationTreeDao.select().all()+" / "+enumerationTreeDao.select(Function.COUNT).oneLong());
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
		
		cascadeDelete(afrique);
	
	}
	
	private void showByParent(EnumerationTree parent){
		System.out.println(parent+" : "+enumerationTreeDao.readByParent(parent)+" / "+enumerationTreeDao.countByParent(parent));
	}
	
	private void showByType(EnumerationTree type){
		System.out.println("All "+type+" : "+enumerationTreeDao.readByType(type)+" / "+enumerationTreeDao.countByType(type));
	}
	
	private void showByParentByType(EnumerationTree parent,EnumerationTree type){
		System.out.println("All "+type+" of "+parent+": "+enumerationTreeDao.readByParentByType(parent,type)+" / "+enumerationTreeDao.countByParentByType(parent, type));
	}
	
	private void cascadeDelete(final EnumerationTree tree){
		long allCount = enumerationTreeDao.select(Function.COUNT).oneLong();
		long deletedCount = enumerationTreeDao.countByParent(tree)+1;
		transaction(new TestMethod() { @Override protected void test() {enumerationTreeDao.delete(tree);} }); 
		long remainingCount = enumerationTreeDao.select(Function.COUNT).oneLong();
		Assert.assertEquals("Cascade delete",allCount-deletedCount,remainingCount);
		System.out.println("All : "+enumerationTreeDao.select().all()+" / "+enumerationTreeDao.select(Function.COUNT).oneLong());
	}

}
