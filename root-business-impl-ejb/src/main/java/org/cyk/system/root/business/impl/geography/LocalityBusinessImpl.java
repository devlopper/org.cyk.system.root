package org.cyk.system.root.business.impl.geography;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.persistence.api.geography.LocalityDao;

@Stateless
public class LocalityBusinessImpl extends AbstractDataTreeBusinessImpl<Locality,LocalityDao,LocalityType> implements LocalityBusiness {
 
	private static final long serialVersionUID = 2801588592108008404L;

	@Inject
    public LocalityBusinessImpl(LocalityDao dao) {
        super(dao);
    } 
	
	@Override
	public Collection<Locality> instanciateMany(LocalityType type,List<String[]> list) {
		List<Locality> localities = new ArrayList<>();
		for(String[] values : list){
			Locality parent = inject(LocalityDao.class).read(values[0]);
			if(parent==null)
				continue;
			Locality instance = instanciateOne();
			instance.setParent(parent);
			instance.setType(type);
			instance.setCode(values[1]+RandomStringUtils.randomAlphabetic(2));//TODO to be rethink : we can load at once all existing codes in a list and do the check
    		instance.setName(values[1]);
    		localities.add(instance);
    	}
		return localities;
	}
	
}
