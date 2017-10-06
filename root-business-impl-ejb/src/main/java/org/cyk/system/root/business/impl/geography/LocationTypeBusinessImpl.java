package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocationTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.persistence.api.geography.LocationTypeDao;

import lombok.Getter;
import lombok.Setter;

public class LocationTypeBusinessImpl extends AbstractEnumerationBusinessImpl<LocationType, LocationTypeDao> implements LocationTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public LocationTypeBusinessImpl(LocationTypeDao dao) {
		super(dao); 
	}   
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<LocationType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(LocationType.class);
		}
		
	}
	
	@Getter @Setter
	public static class Details extends AbstractEnumerationBusinessImpl.Details<LocationType> implements Serializable {

		private static final long serialVersionUID = 4444472169870625893L;
		
		public Details(LocationType phoneNumberType) {
			super(phoneNumberType);		
		}

		
	}

	
}
