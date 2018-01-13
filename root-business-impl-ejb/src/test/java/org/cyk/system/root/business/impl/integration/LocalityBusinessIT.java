package org.cyk.system.root.business.impl.integration;

import java.util.Collection;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.persistence.api.geography.LocalityDao;
import org.cyk.system.root.persistence.api.geography.LocalityTypeDao;
import org.junit.Test;

public class LocalityBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 8691254326402622637L;
    
    @Test
    public void moveCoteDIvoireAndMoveItBackUsingMove(){
    	moveAndMoveItBack(RootConstant.Code.Country.COTE_DIVOIRE, RootConstant.Code.Locality.AMERICA, 8l,3l);
    }

    @Test
    public void moveCoteDIvoireAndMoveItBackUsingUpdate(){
    	updateAndMoveAndUpdateAndMoveItBack(RootConstant.Code.Country.COTE_DIVOIRE, RootConstant.Code.Locality.AMERICA, 8l,3l);
    	updateAndMoveAndUpdateAndMoveItBack(RootConstant.Code.Country.COTE_DIVOIRE, RootConstant.Code.Locality.ASIA, 8l,3l);
    	updateAndMoveAndUpdateAndMoveItBack(RootConstant.Code.Country.COTE_DIVOIRE, RootConstant.Code.Locality.AUSTRALIA, 8l,3l);
    	updateAndMoveAndUpdateAndMoveItBack(RootConstant.Code.Country.COTE_DIVOIRE, RootConstant.Code.Locality.EUROPE, 8l,3l);
    	updateAndMoveAndUpdateAndMoveItBack(RootConstant.Code.Country.COTE_DIVOIRE, RootConstant.Code.Locality.AFRICA, 8l,3l);
    	
    }
    
    @Test
    public void crud(){
    	TestCase testCase = instanciateTestCase();
    	Locality locality = inject(LocalityBusiness.class).instanciateOne(null,"ML001","");
    	locality.setType(inject(LocalityTypeDao.class).readOneRandomly());
    	testCase.create(locality);
    	
    	locality = inject(LocalityBusiness.class).instanciateOne("ML002","");
    	locality.setParentNode(inject(LocalityDao.class).read("ML001"));
    	locality.setType(inject(LocalityTypeDao.class).readOneRandomly());
    	testCase.create(locality);
    	
    	Collection<Locality> localities = inject(LocalityDao.class).readByParent(inject(LocalityDao.class).read("ML001"));
    	assertEquals(1, localities.size()); 
    	assertEquals("ML002", localities.iterator().next().getCode());
    	
    	testCase.clean();
    }
    /**/

    private void moveAndMoveItBack(String localityCode,String parentCode,Long expectedNumberOfChildren,Long expectedNumberOfDirectChildren){
    	Locality locality = inject(LocalityDao.class).read(localityCode);
    	assertThat("Locality is not null", locality!=null);
    	Locality oldParent = inject(LocalityDao.class).readParent(locality);
    	String oldParentCode = oldParent == null ? null : oldParent.getCode();
    	
    	assertNumberOfChildren(localityCode, expectedNumberOfChildren,expectedNumberOfDirectChildren);
    	inject(LocalityBusiness.class).move(localityCode, parentCode);//move
    	assertNumberOfChildren(localityCode, expectedNumberOfChildren,expectedNumberOfDirectChildren);
    	inject(LocalityBusiness.class).move(localityCode, oldParentCode);//move it back
    	assertNumberOfChildren(localityCode, expectedNumberOfChildren,expectedNumberOfDirectChildren);
    }
    
    private void updateAndMoveAndUpdateAndMoveItBack(String localityCode,String parentCode,Long expectedNumberOfChildren,Long expectedNumberOfDirectChildren){
    	Locality locality = inject(LocalityDao.class).read(localityCode);
    	assertThat("Locality is not null", locality!=null);
    	Locality oldParent = inject(LocalityDao.class).readParent(locality);
    	String oldParentCode = oldParent == null ? null : oldParent.getCode();
    	
    	assertUpdateAndMove(localityCode, parentCode, expectedNumberOfChildren,expectedNumberOfDirectChildren);
    	assertUpdateAndMove(localityCode, oldParentCode, expectedNumberOfChildren,expectedNumberOfDirectChildren);
    	assertUpdateAndMove(localityCode, parentCode, expectedNumberOfChildren,expectedNumberOfDirectChildren);
    	assertUpdateAndMove(localityCode, oldParentCode, expectedNumberOfChildren,expectedNumberOfDirectChildren);
    	assertUpdateAndMove(localityCode, parentCode, expectedNumberOfChildren,expectedNumberOfDirectChildren);
    	assertUpdateAndMove(localityCode, oldParentCode, expectedNumberOfChildren,expectedNumberOfDirectChildren);
    }
    
    private void assertUpdateAndMove(String localityCode,String parentCode,Long expectedNumberOfChildren,Long expectedNumberOfDirectChildren){
    	assertNumberOfChildren(localityCode, expectedNumberOfChildren,expectedNumberOfDirectChildren);
    	Locality locality = inject(LocalityDao.class).read(localityCode);
    	locality.setAutomaticallyMoveToNewParent(Boolean.TRUE);
    	locality.setNewParent(parentCode == null ? null : inject(LocalityDao.class).read(parentCode));
    	inject(LocalityBusiness.class).update(locality);
    	assertNumberOfChildren(localityCode, expectedNumberOfChildren,expectedNumberOfDirectChildren);
    }
    
    private void assertNumberOfChildren(String localityCode,Long expectedNumberOfChildren,Long expectedNumberOfDirectChildren){
    	Locality locality = inject(LocalityDao.class).read(localityCode);
    	assertThat("Locality is not null", locality!=null);
    	assertEquals("Number of children", expectedNumberOfChildren, inject(LocalityDao.class).countByParent(locality));
    	assertEquals("Number of direct children", expectedNumberOfDirectChildren, inject(LocalityDao.class).countDirectChildrenByParent(locality));
    }

}
