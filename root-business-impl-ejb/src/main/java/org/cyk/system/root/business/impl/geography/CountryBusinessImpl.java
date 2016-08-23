package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.geography.CountryBusiness;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.persistence.api.geography.CountryDao;
import org.cyk.system.root.persistence.api.geography.LocalityDao;
import org.cyk.system.root.persistence.api.geography.LocalityTypeDao;

public class CountryBusinessImpl extends AbstractTypedBusinessService<Country, CountryDao> implements CountryBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject private LocalityBusiness localityBusiness;
	@Inject private LocalityTypeDao localityTypeDao;
	
	@Inject
	public CountryBusinessImpl(CountryDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<Country> instanciateMany(List<String[]> list) {
		List<Country> countries = new ArrayList<>();
		for(String[] values : list){
			Locality continent = inject(LocalityDao.class).read(values[0]);
			if(continent==null)
				continue;
			Country instance = instanciateOne();
			instance.setContinent(continent);
    		instance.setCode(values[1]);
    		instance.setName(values[2]);
    		instance.setPhoneNumberCode(Integer.parseInt(StringUtils.defaultIfBlank(values.length>3 ? values[3] : null, "0")));
    		countries.add(instance);
    	}
		return countries;
	}

	@Override
	public Country create(Country country) {
		Locality locality = country.getLocality();
		if(locality==null){
			locality = new Locality(country.getContinent(), localityTypeDao.read(LocalityType.COUNTRY), country.getCode(), country.getName());
			country.setLocality(locality);
		}
		if(locality.getIdentifier()==null)
			localityBusiness.create(locality);
		/*
		if(StringUtils.isBlank(country.getCode()))
			country.setCode(country.getLocality().getCode());
		if(StringUtils.isBlank(country.getName()))
			country.setName(country.getLocality().getName());
		*/
		super.create(country);
		return country;
	}
	
	@Override
	public Country update(Country country) {
		if(StringUtils.isBlank(country.getCode()))
			country.setCode(country.getLocality().getCode());
		else
			country.getLocality().setCode(country.getCode());
		if(StringUtils.isBlank(country.getName()))
			country.setName(country.getLocality().getName());
		else
			country.getLocality().setName(country.getName());
		localityBusiness.update(country.getLocality());
		return super.update(country);
	}
}
