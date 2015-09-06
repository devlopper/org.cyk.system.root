package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocationTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.persistence.api.geography.LocationTypeDao;

public class LocationTypeBusinessImpl extends AbstractEnumerationBusinessImpl<LocationType, LocationTypeDao> implements LocationTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public LocationTypeBusinessImpl(LocationTypeDao dao) {
		super(dao); 
	}   
	
}
