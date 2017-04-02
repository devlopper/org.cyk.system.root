package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocationBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.persistence.api.geography.CountryDao;
import org.cyk.system.root.persistence.api.geography.LocationDao;
import org.cyk.system.root.persistence.api.geography.LocationTypeDao;
import org.cyk.utility.common.generator.RandomDataProvider;

public class LocationBusinessImpl extends AbstractContactBusinessImpl<Location, LocationDao> implements LocationBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public LocationBusinessImpl(LocationDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Location instanciateOneRandomly() {
		Location location = new Location();
		location.setOtherDetails(RandomDataProvider.getInstance().randomWord(RandomDataProvider.WORD_LOCATION, 5, 10));
		Country country = inject(CountryDao.class).readOneRandomly();
		if(country!=null)
			location.setLocality(country.getLocality());
		location.setType(inject(LocationTypeDao.class).read(RootConstant.Code.LocationType.HOME));
		return location; 
	}

}
