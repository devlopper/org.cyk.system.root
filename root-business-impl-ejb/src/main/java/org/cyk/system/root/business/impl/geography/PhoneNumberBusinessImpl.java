package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.PhoneNumberBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.persistence.api.geography.CountryDao;
import org.cyk.system.root.persistence.api.geography.PhoneNumberDao;
import org.cyk.system.root.persistence.api.geography.PhoneNumberTypeDao;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.helper.RandomHelper;

import lombok.Getter;
import lombok.Setter;

public class PhoneNumberBusinessImpl extends AbstractContactBusinessImpl<PhoneNumber, PhoneNumberDao> implements PhoneNumberBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public PhoneNumberBusinessImpl(PhoneNumberDao dao) {
		super(dao); 
	}
	
	@Override
	public Contact instanciateOneRandomly() {
		Country country = inject(CountryDao.class).readOneRandomly();
		return instanciateOne(null,country==null ?null:country.getCode(), RootConstant.Code.PhoneNumberType.MOBILE, String.valueOf(RandomHelper.getInstance().getNumeric(8)));
	}
	
	@Override
	public PhoneNumber instanciateOne(ContactCollection collection, String number) {
		return instanciateOne(collection, RootConstant.Code.Country.__DEFAULT__, RootConstant.Code.PhoneNumberType.__DEFAULT__, number);
	}

	@Override
	public PhoneNumber instanciateOne(ContactCollection collection,String countryCode,String typeCode, String number) {
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setCountry(inject(CountryDao.class).read(countryCode));
		phoneNumber.setType(inject(PhoneNumberTypeDao.class).read(typeCode));
		phoneNumber.setNumber(number);
		if(collection==null)
			;
		else
			collection.add(phoneNumber);
		return phoneNumber;
	}

	@Override
	public List<PhoneNumber> instanciateMany(ContactCollection collection, List<String[]> values) {
		List<PhoneNumber> list = new ArrayList<>();
		if(values!=null)
			for(String[] line : values)
				list.add(instanciateOne(collection,line[0],line[1],line[2]));
		return list;
	}

	@Override
	public List<PhoneNumber> instanciateMany(ContactCollection collection, String[] numbers) {
		List<PhoneNumber> list = new ArrayList<>();
		if(numbers!=null)
			for(String number : numbers)
				list.add(instanciateOne(collection,inject(CountryDao.class).readOneRandomly().getCode(),RootConstant.Code.PhoneNumberType.MOBILE,number));
		return list;
	}

	@Getter @Setter
	public static class Details extends AbstractContactBusinessImpl.Details<PhoneNumber> implements Serializable {

		private static final long serialVersionUID = 4444472169870625893L;

		@Input @InputText private FieldValue country,type,locationType;
		@Input @InputText private String number;
		
		public Details(PhoneNumber phoneNumber) {
			super(phoneNumber);
		}

		@Override
		public void setMaster(PhoneNumber phoneNumber) {
			super.setMaster(phoneNumber);
			if(phoneNumber!=null){
				if(phoneNumber.getCountry()!=null)
					country = new FieldValue(phoneNumber.getCountry());
				if(phoneNumber.getType()!=null)
					type = new FieldValue(phoneNumber.getType());
				number = phoneNumber.getNumber();
				if(phoneNumber.getLocationType()!=null)
					locationType = new FieldValue(phoneNumber.getLocationType());
			}
		}
		
		public static final String FIELD_COUNTRY = "country";
		public static final String FIELD_TYPE = "type";
		public static final String FIELD_NUMBER = "number";
		public static final String FIELD_LOCATION_TYPE = "locationType";
	}

}
