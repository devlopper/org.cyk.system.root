package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.geography.CountryBusiness;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.persistence.api.geography.CountryDao;
import org.cyk.system.root.persistence.api.geography.LocalityDao;
import org.cyk.system.root.persistence.api.geography.LocalityTypeDao;

public class CountryBusinessImpl extends AbstractTypedBusinessService<Country, CountryDao> implements CountryBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public CountryBusinessImpl(CountryDao dao) {
		super(dao); 
	}
	
	@Override
	protected Class<?> parameterizedClass() {
		return Country.class;
	}
	
	@Override
	public Country instanciateOne(String[] values) {
		Country country = instanciateOne();
		country.setCode(values[0]);
		country.setName(values[1]);
		country.setContinent(inject(LocalityDao.class).read(values[2]));
		country.setPhoneNumberCode(Integer.parseInt(StringUtils.defaultIfBlank(values.length>3 ? values[3] : null, "0")));
		return country;
	}
	
	@Override
	protected void beforeCreate(Country country) {
		Locality locality = country.getLocality();
		if(locality==null){
			locality = new Locality(country.getContinent(), inject(LocalityTypeDao.class).read(RootConstant.Code.LocalityType.COUNTRY), country.getCode(), country.getName());
			country.setLocality(locality);
		}
		if(locality.getIdentifier()==null)
			inject(LocalityBusiness.class).create(locality);
		super.beforeCreate(country);
	}

	@Override
	protected void beforeUpdate(Country country) {
		super.beforeUpdate(country);
		if(StringUtils.isBlank(country.getCode()))
			country.setCode(country.getLocality().getCode());
		else
			country.getLocality().setCode(country.getCode());
		if(StringUtils.isBlank(country.getName()))
			country.setName(country.getLocality().getName());
		else
			country.getLocality().setName(country.getName());
		inject(LocalityBusiness.class).update(country.getLocality());
	}
	
}
