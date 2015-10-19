package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.persistence.api.geography.LocationDao;

public class LocationDaoImpl extends AbstractContactDaoImpl<Location> implements LocationDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        //registerNamedQuery(readByValue, _select().where(PhoneNumber.FIELD_NUMBER));
    }
    
	@Override
	public Collection<Location> readByValue(String location) {
		return null;//namedQuery(readByValue).parameter(PhoneNumber.FIELD_NUMBER, location).resultMany();
	}
	
	@Override
	public Long countByValue(String address) {
		return 0l;//countNamedQuery(countByValue).parameter(PhoneNumber.FIELD_NUMBER, address).resultOne();
	}
	
   
 
}
 