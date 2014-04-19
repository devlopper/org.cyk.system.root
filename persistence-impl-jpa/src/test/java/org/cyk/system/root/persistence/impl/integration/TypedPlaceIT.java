package org.cyk.system.root.persistence.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.model.pattern.tree.AbstractEnumerationTree;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractEnumerationTreeDao;
import org.cyk.system.root.persistence.impl.AbstractPersistenceIT;
import org.cyk.system.root.persistence.impl.data.PlaceType;
import org.cyk.system.root.persistence.impl.data.PlaceTypeDao;
import org.cyk.system.root.persistence.impl.data.TypedPlace;
import org.cyk.system.root.persistence.impl.data.TypedPlaceDao;
import org.cyk.system.root.persistence.impl.pattern.tree.AbstractEnumerationTreeDaoImpl;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.test.TestMethod;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class TypedPlaceIT extends AbstractPersistenceIT {

	@Deployment
	public static Archive<?> createDeployment() {
		return deployment().persistence(new Class<?>[]{NestedSet.class,NestedSetNode.class,AbstractEnumerationTree.class,AbstractEnumerationTreeDao.class,
				AbstractEnumerationTreeDaoImpl.class,TypedPlace.class,TypedPlaceDao.class,PlaceType.class,PlaceTypeDao.class})
				.getArchive();
	} 
	
	@Inject private TypedPlaceDao dao;
	@Inject private PlaceTypeDao placeTypeDao;
	
	static PlaceType continent,country,city;
	
	static TypedPlace afrique,europe;
	static TypedPlace afriqueCI,afriqueGH,europeFR,europeIT;
	static TypedPlace afriqueCIAbidjan,afriqueCIBouake,afriqueGHAccra,afriqueGHNoe,europeFRParis,europeFRLyon,europeITRome,europeITViennes;
	
	@Override
	protected void populate() {	
		continent = placeTypeDao.create(new PlaceType(null, "Continent"));
		country = placeTypeDao.create(new PlaceType(continent, "Country"));
		city = placeTypeDao.create(new PlaceType(country, "City"));
		
		afrique = dao.create(new TypedPlace(null,continent,"Afrique"));
		afriqueCI = dao.create(new TypedPlace(afrique,country,"Cote Ivoire"));
		afriqueCIAbidjan = dao.create(new TypedPlace(afriqueCI,city,"Abidjan"));
		afriqueCIBouake = dao.create(new TypedPlace(afriqueCI,city,"Bouake"));
		afriqueGH = dao.create(new TypedPlace(afrique,country,"Ghana"));
		afriqueGHAccra = dao.create(new TypedPlace(afriqueGH,city,"Accra"));
		afriqueGHNoe = dao.create(new TypedPlace(afriqueGH,city,"Noe"));
		
		europe = dao.create(new TypedPlace(null,continent,"Europe"));
		europeFR = dao.create(new TypedPlace(europe,country,"France"));
		europeFRParis = dao.create(new TypedPlace(europeFR,city,"Paris"));
		europeFRLyon = dao.create(new TypedPlace(europeFR,city,"Lyon"));
		europeIT = dao.create(new TypedPlace(europe,country,"Italie"));
		europeITRome = dao.create(new TypedPlace(europeIT,city,"Rome"));
		europeITViennes = dao.create(new TypedPlace(europeIT,city,"Viennes"));
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
	
	private void showByParent(TypedPlace parent){
		System.out.println(parent+" : "+dao.readByParent(parent)+" / "+dao.countByParent(parent));
	}
	
	private void showByType(PlaceType type){
		System.out.println("All "+type+" : "+dao.readByType(type)+" / "+dao.countByType(type));
	}
	
	private void showByParentByType(TypedPlace parent,PlaceType type){
		System.out.println("All "+type+" of "+parent+": "+dao.readByParentByType(parent,type)+" / "+dao.countByParentByType(parent, type));
	}
	
	private void cascadeDelete(final TypedPlace tree){
		long allCount = dao.select(Function.COUNT).oneLong();
		long deletedCount = dao.countByParent(tree)+1;
		transaction(new TestMethod() { @Override protected void test() {dao.delete(tree);} }); 
		long remainingCount = dao.select(Function.COUNT).oneLong();
		Assert.assertEquals("Cascade delete",allCount-deletedCount,remainingCount);
		System.out.println("All : "+dao.select().all()+" / "+dao.select(Function.COUNT).oneLong());
	}

}
