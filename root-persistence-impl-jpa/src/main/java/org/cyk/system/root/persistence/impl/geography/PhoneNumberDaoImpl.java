package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.persistence.api.geography.PhoneNumberDao;

public class PhoneNumberDaoImpl extends AbstractContactDaoImpl<PhoneNumber> implements PhoneNumberDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByValue, _select().where(PhoneNumber.FIELD_NUMBER));
    }
    
	@Override
	public Collection<PhoneNumber> readByValue(String address) {
		return castCollection(namedQuery(readByValue).parameter(PhoneNumber.FIELD_NUMBER, address).resultMany(),PhoneNumber.class);
	}
	
	@Override
	public Long countByValue(String address) {
		return countNamedQuery(countByValue).parameter(PhoneNumber.FIELD_NUMBER, address).resultOne();
	}
	
   
 
}
 