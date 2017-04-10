package org.cyk.system.root.business.impl.integration;

import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.persistence.api.geography.LocalityDao;

public class LocalityBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 8691254326402622637L;
	
	@Inject private LocalityBusiness localityBusiness;  
	@Inject private LocalityTypeBusiness localityTypeBusiness;
	
    @Override
    protected void businesses() {
    	traceLocalityType(localityTypeBusiness.findAll(),"");
    	
    	/*traceLocality(localityBusiness.findByParent(traceLocality(localityBusiness.find("AF"),"")),"\t");
    	traceLocality(localityBusiness.findByParent(traceLocality(localityBusiness.find("AL"),"")),"\t");
    	traceLocality(localityBusiness.findByParent(traceLocality(localityBusiness.find("DZ"),"")),"\t");
    	*/
    	String countryCode =  "CI";
    	Locality coteDivoire = inject(LocalityDao.class).read(countryCode);
    	System.out.println(coteDivoire.getNode().getLeftIndex()+":"+coteDivoire.getNode().getRightIndex());
    	Locality parent = (Locality) inject(LocalityBusiness.class).findParent(coteDivoire);
    	coteDivoire.setParent(parent);
    	update(coteDivoire);
    	System.out.println(coteDivoire.getNode().getLeftIndex()+":"+coteDivoire.getNode().getRightIndex());
    	coteDivoire = inject(LocalityDao.class).read(countryCode);
    }

    /**/

    private <T extends AbstractDataTreeNode> T traceDataTreeNode(T dataTreeNode,String tab){
    	if(dataTreeNode==null)
    		;
    	else
    		System.out.println(tab+dataTreeNode.getNode().getLogMessage());
    	return dataTreeNode;
    }
    private <T extends AbstractDataTreeNode> Collection<T> traceDataTreeNode(Collection<T> dataTreeNodes,String tab){
    	for(AbstractDataTreeNode dataTreeNode : dataTreeNodes)
    		traceDataTreeNode(dataTreeNode,tab);
    	return dataTreeNodes;
    }
    
    private Locality traceLocality(Locality locality,String tab){
    	return traceDataTreeNode(locality,tab);
    }
    private Collection<Locality> traceLocality(Collection<Locality> localities,String tab){
    	return traceDataTreeNode(localities, tab);
    }
    
    @SuppressWarnings("unused")
	private LocalityType traceLocalityType(LocalityType localityType,String tab){
    	return traceDataTreeNode(localityType,tab);
    }
    private Collection<LocalityType> traceLocalityType(Collection<LocalityType> localityTypes,String tab){
    	return traceDataTreeNode(localityTypes, tab);
    }

}
