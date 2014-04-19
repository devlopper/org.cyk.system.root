package org.cyk.system.root.persistence.impl.integration.tree;

import javax.inject.Inject;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.impl.AbstractPersistenceIT;
import org.cyk.system.root.persistence.impl.data.UnTypedPlace;
import org.cyk.system.root.persistence.impl.data.UnTypedPlaceDao;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.test.TestMethod;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class UnTypedPlaceIT extends AbstractPersistenceIT {

	@Deployment
	public static Archive<?> createDeployment() {
		return deployment(new Class<?>[]{NestedSet.class,NestedSetNode.class,UnTypedPlace.class,UnTypedPlaceDao.class,AbstractDataTreeNode.class})
				.getArchive();
	} 
	
	static UnTypedPlace continent,country,city;
	
	static UnTypedPlace afrique,europe;
	static UnTypedPlace afriqueCI,afriqueGH,europeFR,europeIT;
	static UnTypedPlace afriqueCIAbidjan,afriqueCIBouake,afriqueGHAccra,afriqueGHNoe,europeFRParis,europeFRLyon,europeITRome,europeITViennes;
	
	@Inject private UnTypedPlaceDao dao;
	
	@Override
	protected void populate() {
		continent = dao.create(new UnTypedPlace(null, "Continent"));
		country = dao.create(new UnTypedPlace(null, "Country"));
		city = dao.create(new UnTypedPlace(null, "City"));
		
		afrique = dao.create(new UnTypedPlace(continent,"Afrique"));
		afriqueCI = dao.create(new UnTypedPlace(afrique,"Cote Ivoire"));
		afriqueCIAbidjan = dao.create(new UnTypedPlace(afriqueCI,"Abidjan"));
		afriqueCIBouake = dao.create(new UnTypedPlace(afriqueCI,"Bouake"));
		afriqueGH = dao.create(new UnTypedPlace(afrique,"Ghana"));
		afriqueGHAccra = dao.create(new UnTypedPlace(afriqueGH,"Accra"));
		afriqueGHNoe = dao.create(new UnTypedPlace(afriqueGH,"Noe"));
		
		europe = dao.create(new UnTypedPlace(continent,"Europe"));
		europeFR = dao.create(new UnTypedPlace(europe,"France"));
		europeFRParis = dao.create(new UnTypedPlace(europeFR,"Paris"));
		europeFRLyon = dao.create(new UnTypedPlace(europeFR,"Lyon"));
		europeIT = dao.create(new UnTypedPlace(europe,"Italie"));
		europeITRome = dao.create(new UnTypedPlace(europeIT,"Rome"));
		europeITViennes = dao.create(new UnTypedPlace(europeIT,"Viennes"));
	}

	@Override
	protected void create() {}

	@Override
	protected void read() {}

	@Override
	protected void update() {}

	@Override
	protected void delete() {}

	@Override
	protected void queries() {
		System.out.println("All : "+dao.select().all()+" / "+dao.select(Function.COUNT).oneLong());
		showByParent(afrique);
		showByParent(europe);
		showByParent(afriqueCI);
		showByParent(afriqueGH);
		showByParent(europeFR);
		showByParent(europeIT);
		showByParent(afriqueCIAbidjan);
		
		cascadeDelete(afrique);
	
	}
	
	private void showByParent(UnTypedPlace parent){
		System.out.println(parent+" : "+dao.readByParent(parent)+" / "+dao.countByParent(parent));
	}
		
	private void cascadeDelete(final UnTypedPlace tree){
		long allCount = dao.select(Function.COUNT).oneLong();
		long deletedCount = dao.countByParent(tree)+1;
		transaction(new TestMethod() { @Override protected void test() {dao.delete(tree);} }); 
		long remainingCount = dao.select(Function.COUNT).oneLong();
		Assert.assertEquals("Cascade delete",allCount-deletedCount,remainingCount);
		System.out.println("All : "+dao.select().all()+" / "+dao.select(Function.COUNT).oneLong());
	}

}
