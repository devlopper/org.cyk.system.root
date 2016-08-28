package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.persistence.api.geography.ElectronicMailDao;

public class ElectronicMailDaoImpl extends AbstractContactDaoImpl<ElectronicMail> implements ElectronicMailDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByValue, _select().where(ElectronicMail.FIELD_ADDRESS));
    }
    
	@Override
	public Collection<ElectronicMail> readByValue(String address) {
		return castCollection(namedQuery(readByValue).parameter(ElectronicMail.FIELD_ADDRESS, address).resultMany(),ElectronicMail.class);
	}
	
	@Override
	public Long countByValue(String address) {
		return countNamedQuery(countByValue).parameter(ElectronicMail.FIELD_ADDRESS, address).resultOne();
	}
	
   
 
}
 