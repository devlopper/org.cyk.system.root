package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.PhoneNumberTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.persistence.api.geography.PhoneNumberTypeDao;

import lombok.Getter;
import lombok.Setter;

public class PhoneNumberTypeBusinessImpl extends AbstractEnumerationBusinessImpl<PhoneNumberType, PhoneNumberTypeDao> implements PhoneNumberTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public PhoneNumberTypeBusinessImpl(PhoneNumberTypeDao dao) {
		super(dao); 
	}   
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<PhoneNumberType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(PhoneNumberType.class);
		}
		
	}
	
	@Getter @Setter
	public static class PhoneNumberTypeDetails extends AbstractEnumerationBusinessImpl.Details<PhoneNumberType> implements Serializable {

		private static final long serialVersionUID = 4444472169870625893L;

		
		public PhoneNumberTypeDetails(PhoneNumberType phoneNumberType) {
			super(phoneNumberType);
			
		}

		
	}
}
