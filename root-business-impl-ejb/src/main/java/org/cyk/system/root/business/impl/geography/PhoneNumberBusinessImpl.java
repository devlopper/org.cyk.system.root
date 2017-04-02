package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.geography.PhoneNumberBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.persistence.api.geography.CountryDao;
import org.cyk.system.root.persistence.api.geography.PhoneNumberDao;
import org.cyk.system.root.persistence.api.geography.PhoneNumberTypeDao;

public class PhoneNumberBusinessImpl extends AbstractContactBusinessImpl<PhoneNumber, PhoneNumberDao> implements PhoneNumberBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public PhoneNumberBusinessImpl(PhoneNumberDao dao) {
		super(dao); 
	}
	
	@Override
	public Contact instanciateOneRandomly() {
		Country country = inject(CountryDao.class).readOneRandomly();
		return instanciateOne(null,country==null ?null:country.getCode(), RootConstant.Code.PhoneNumberType.MOBILE, RandomStringUtils.randomNumeric(8));
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
	
	
}
