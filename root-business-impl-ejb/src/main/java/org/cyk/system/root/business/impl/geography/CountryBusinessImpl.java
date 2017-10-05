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
import org.cyk.utility.common.helper.StringHelper;

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
		country.setLocality(inject(LocalityDao.class).read(country.getCode()));
		return country;
	}
	
	@Override
	protected void beforeCreate(Country country) {
		Locality locality = country.getLocality();
		if(locality==null){
			if(StringHelper.getInstance().isNotBlank(country.getCode()))
				locality = inject(LocalityDao.class).read(country.getCode());
			if(locality==null)
				locality = new Locality(country.getContinent(), inject(LocalityTypeDao.class).read(RootConstant.Code.LocalityType.COUNTRY), country.getCode(), country.getName());
			country.setLocality(locality);
		}
		super.beforeCreate(country);
		createIfNotIdentified(locality);
	}
	
	/*@Override
	public Country create(Country country) {
		if(country.getContinent()==null)
			return null;
		return super.create(country);
	}*/

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
		Country oldCountry = dao.read(country.getIdentifier());
		oldCountry.setContinent((Locality) inject(LocalityBusiness.class).findParent(country.getLocality()));
		if((oldCountry.getContinent()==null && country.getContinent()!=null) || (oldCountry.getContinent()!=null && country.getContinent()==null)
				|| !oldCountry.getContinent().getIdentifier().equals(country.getContinent().getIdentifier()) ){
			country.getLocality().setAutomaticallyMoveToNewParent(Boolean.TRUE);
			country.getLocality().setNewParent(country.getContinent());
		}
		inject(LocalityBusiness.class).update(country.getLocality());
	}
	
	@Override
	protected void afterDelete(Country country) {
		super.afterDelete(country);
		inject(LocalityBusiness.class).delete(country.getLocality());
	}
	
	/**/
	
	public static class BuilderOneDimensionArray extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<Country> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Country.class);
			addFieldCodeName();
		}
		
	}
	
}
