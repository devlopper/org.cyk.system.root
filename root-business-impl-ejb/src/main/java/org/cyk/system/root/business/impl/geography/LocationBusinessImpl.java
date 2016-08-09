package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocationBusiness;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.persistence.api.geography.LocationDao;

public class LocationBusinessImpl extends AbstractContactBusinessImpl<Location, LocationDao> implements LocationBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public LocationBusinessImpl(LocationDao dao) {
		super(dao); 
	}

}
