package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.CountryBusiness;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.persistence.api.geography.CountryDao;

@Stateless
public class CountryBusinessImpl extends AbstractTypedBusinessService<Country, CountryDao> implements CountryBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject private LocalityBusiness localityBusiness;
	
	@Inject
	public CountryBusinessImpl(CountryDao dao) {
		super(dao); 
	}

	@Override
	public Country create(Country country) {
		localityBusiness.create(country.getLocality());
		country = super.create(country);
		return country;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Country findByCode(String code) {
		return dao.readByCode(code);
	}   
	
	
	
}
