package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.persistence.api.geography.LocalityDao;
import org.junit.Test;

public class LocalityBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 8691254326402622637L;
	
    @Override
    protected void businesses() {
    	//traceLocalityType(localityTypeBusiness.findAll(),"");
    	
    	/*traceLocality(localityBusiness.findByParent(traceLocality(localityBusiness.find("AF"),"")),"\t");
    	traceLocality(localityBusiness.findByParent(traceLocality(localityBusiness.find("AL"),"")),"\t");
    	traceLocality(localityBusiness.findByParent(traceLocality(localityBusiness.find("DZ"),"")),"\t");
    	*/
    	/*String countryCode =  "CI";
    	Locality coteDivoire = inject(LocalityDao.class).read(countryCode);
    	System.out.println(coteDivoire.getNode().getLeftIndex()+":"+coteDivoire.getNode().getRightIndex());
    	Locality parent = (Locality) inject(LocalityBusiness.class).findParent(coteDivoire);
    	coteDivoire.setParent(parent);
    	update(coteDivoire);
    	System.out.println(coteDivoire.getNode().getLeftIndex()+":"+coteDivoire.getNode().getRightIndex());
    	coteDivoire = inject(LocalityDao.class).read(countryCode);
    	*/
    }
    
    //@Test
    public void moveCoteDIvoireAndMoveItBackUsingMove(){
    	moveAndMoveItBack(RootConstant.Code.Country.COTE_DIVOIRE, RootConstant.Code.Locality.AMERICA, 8l);
    }

    @Test
    public void moveCoteDIvoireAndMoveItBackUsingUpdate(){
    	updateAndMoveAndUpdateAndMoveItBack(RootConstant.Code.Country.COTE_DIVOIRE, RootConstant.Code.Locality.AMERICA, 8l);
    	updateAndMoveAndUpdateAndMoveItBack(RootConstant.Code.Country.COTE_DIVOIRE, RootConstant.Code.Locality.ASIA, 8l);
    	updateAndMoveAndUpdateAndMoveItBack(RootConstant.Code.Country.COTE_DIVOIRE, RootConstant.Code.Locality.AUSTRALIA, 8l);
    	updateAndMoveAndUpdateAndMoveItBack(RootConstant.Code.Country.COTE_DIVOIRE, RootConstant.Code.Locality.EUROPE, 8l);
    	updateAndMoveAndUpdateAndMoveItBack(RootConstant.Code.Country.COTE_DIVOIRE, RootConstant.Code.Locality.AFRICA, 8l);
    	
    }
    /**/

    private void moveAndMoveItBack(String localityCode,String parentCode,Long expectedNumberOfChildren){
    	Locality locality = inject(LocalityDao.class).read(localityCode);
    	assertThat("Locality is not null", locality!=null);
    	Locality oldParent = inject(LocalityDao.class).readParent(locality);
    	String oldParentCode = oldParent == null ? null : oldParent.getCode();
    	
    	assertNumberOfChildren(localityCode, expectedNumberOfChildren);
    	inject(LocalityBusiness.class).move(localityCode, parentCode);//move
    	assertNumberOfChildren(localityCode, expectedNumberOfChildren);
    	inject(LocalityBusiness.class).move(localityCode, oldParentCode);//move it back
    	assertNumberOfChildren(localityCode, expectedNumberOfChildren);
    	
    }
    
    private void updateAndMoveAndUpdateAndMoveItBack(String localityCode,String parentCode,Long expectedNumberOfChildren){
    	Locality locality = inject(LocalityDao.class).read(localityCode);
    	assertThat("Locality is not null", locality!=null);
    	Locality oldParent = inject(LocalityDao.class).readParent(locality);
    	String oldParentCode = oldParent == null ? null : oldParent.getCode();
    	
    	assertUpdateAndMove(localityCode, parentCode, expectedNumberOfChildren);
    	assertUpdateAndMove(localityCode, oldParentCode, expectedNumberOfChildren);
    	assertUpdateAndMove(localityCode, parentCode, expectedNumberOfChildren);
    	assertUpdateAndMove(localityCode, oldParentCode, expectedNumberOfChildren);
    	assertUpdateAndMove(localityCode, parentCode, expectedNumberOfChildren);
    	assertUpdateAndMove(localityCode, oldParentCode, expectedNumberOfChildren);
    }
    
    private void assertUpdateAndMove(String localityCode,String parentCode,Long expectedNumberOfChildren){
    	assertNumberOfChildren(localityCode, expectedNumberOfChildren);
    	Locality locality = inject(LocalityDao.class).read(localityCode);
    	locality.setAutomaticallyMoveToNewParent(Boolean.TRUE);
    	locality.setNewParent(parentCode == null ? null : inject(LocalityDao.class).read(parentCode));
    	inject(LocalityBusiness.class).update(locality);
    	assertNumberOfChildren(localityCode, expectedNumberOfChildren);
    }
    
    private void assertNumberOfChildren(String localityCode,Long expectedNumberOfChildren){
    	Locality locality = inject(LocalityDao.class).read(localityCode);
    	assertThat("Locality is not null", locality!=null);
    	Long numberOfChildren = inject(LocalityDao.class).countByParent(locality);
    	assertEquals("Number of children", expectedNumberOfChildren, numberOfChildren);
    }

}
