package org.cyk.system.root.business.impl.integration;

import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.FilterClassLocator;
import org.cyk.system.root.business.api.ActorBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Actor;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.system.root.persistence.api.geography.ElectronicMailAddressDao;
import org.cyk.system.root.persistence.api.geography.LocalityDao;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.junit.Test;

public class Fonctionnality_Filter_IT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Override
    protected void populate() {
    	super.populate();
    	/*Actor actor = inject(ActorBusiness.class).instanciateOne();
    	actor.setCode("c001").getPerson().setCode("c001").setName("konan").setLastnames("marius").addElectronicMail("mymail@yahoo.fr"); 
    	actor.setName("konan");
    	actor.getPerson().getContactCollection().getItems().setSynchonizationEnabled(Boolean.TRUE);
    	create(actor);
    	
    	actor = inject(ActorBusiness.class).instanciateOne();
    	actor.setCode("c002").getPerson().setCode("c002").setName("zanga").setLastnames("alice").addElectronicMail("konan@mail.com");
    	actor.setName("zanga");
    	actor.getPerson().getContactCollection().getItems().setSynchonizationEnabled(Boolean.TRUE);
    	create(actor);
    	
    	actor = inject(ActorBusiness.class).instanciateOne();
    	actor.setCode("c003a").getPerson().setCode("c003a").setName("doudou").setLastnames("cherif");
    	actor.setName("doudou");
    	create(actor);
    	*/
    	create(new JobFunction(null, null));
    	create(new JobFunction("mycode", null));
    	create(new JobFunction(null, "mylibeller"));
    	
    }
    
	@Test
	public void filterJobFunction(){
		assertWithBlankStringFindByString(JobFunction.class);
		assertFilter(JobFunction.class,"rec",2);
		assertFilter(JobFunction.class,"m",2);
		assertFilter(JobFunction.class,"my",2);
		assertFilter(JobFunction.class,"myc",1);
		assertFilter(JobFunction.class,"myl",1);
		assertFilter(JobFunction.class,"e",4);
		assertFilter(JobFunction.class,"m",2);
		assertFilter(JobFunction.class,"c",3);
		assertFilter(JobFunction.class,"r",3);
	}
	
	@Test
	public void findLocality(){
		assertWithBlankStringFindByString(Locality.class);
		assertFilter(Locality.class,null,null,new DataReadConfiguration().setMaximumResultCount(10l),10,inject(LocalityDao.class).countAll().intValue());
		assertFilter(Locality.class,"ivoire",1);
	}
	
	@Test
	public void findCredentials(){
		assertWithBlankStringFindByString(Credentials.class);
		assertFilter(Credentials.class,"admin",1);
	}
	
	@Test
	public void findElectronicMails(){
		assertWithBlankStringFindByString(ElectronicMailAddress.class);
		Integer allCount = inject(ElectronicMailAddressDao.class).countAll().intValue();
		assertFilter(ElectronicMailAddress.class,"@",allCount);
		assertFilter(ElectronicMailAddress.class,"kycdev@gmail.com",1);
		assertFilter(ElectronicMailAddress.class,"mymail",1);
	}
	
	@Test
	public void findPerson(){
		assertEquals(4l, inject(PersonDao.class).countAll());
		assertWithBlankStringFindByString(Person.class);
		
		assertFilter(Person.class,"WXWX",0);
		
		assertFilter(Person.class,"ko",2);
		
		assertFilter(Person.class,"ko",Arrays.asList("c002"),1);
		assertFilter(Person.class,"a",4);
		assertFilter(Person.class,"a",Arrays.asList("c002"),3);
		
		assertFilter(Person.class,"ius",1);
		assertFilter(Person.class,"konan@mail.com",1);
		assertFilter(Person.class,"@",2);
		assertFilter(Person.class,"konan",2);
		assertFilter(Person.class,"konan@",1);
	}
	
	@Test
	public void findActor(){
		assertWithBlankStringFindByString(Actor.class);
		
		assertFilter(Actor.class,"WXWX",0);
		
		assertFilter(Actor.class,"ko",Arrays.asList("c002"),1);
		assertFilter(Actor.class,"a",3);
		assertFilter(Actor.class,"a",Arrays.asList("c002"),2);

		assertFilter(Actor.class,"ius",1);
		assertFilter(Actor.class,"konan@mail.com",1);
		assertFilter(Actor.class,"@",2);
		assertFilter(Actor.class,"konan",2);
		assertFilter(Actor.class,"konan@",1);
	}
	
	
	private <T extends AbstractIdentifiable> void assertFilter(Class<T> aClass,String string,Collection<String> excludedCodes,DataReadConfiguration configuration,Integer expectedReturnCount,Integer expectedDatabaseCount){
		Collection<T> excludedIdentifiables = excludedCodes == null ? null : inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(excludedCodes);
		@SuppressWarnings("unchecked")
		FilterHelper.Filter<T> filter = (Filter<T>) ClassHelper.getInstance().instanciateOne(aClass.equals(Actor.class) ? Actor.Filter.class : FilterClassLocator.getInstance().locate(aClass));
		filter.set(string).setExcluded(excludedIdentifiables);
		
		TypedDao<T> persistence = inject(PersistenceInterfaceLocator.class).injectTyped(aClass);
		assertEquals(expectedReturnCount.intValue(), persistence.readByFilter(filter,configuration).size());
		assertEquals(expectedDatabaseCount.longValue(), persistence.countByFilter(filter,configuration));

		TypedBusiness<T> business = inject(BusinessInterfaceLocator.class).injectTyped(aClass);
		assertEquals(expectedReturnCount.intValue(), business.findByFilter(filter,configuration).size());
		assertEquals(expectedDatabaseCount.longValue(), business.countByFilter(filter,configuration));
	}
	
	private <T extends AbstractIdentifiable> void assertFilter(Class<T> aClass,String string,Collection<String> excludedCodes,DataReadConfiguration configuration,Integer expectedReturnCount){
		assertFilter(aClass, string, excludedCodes, configuration, expectedReturnCount, expectedReturnCount);
	}
	
	private <T extends AbstractIdentifiable> void assertFilter(Class<T> aClass,String string,Collection<String> excludedCodes,Integer expected){
		assertFilter(aClass, string, excludedCodes, null, expected);
	}
	
	private <T extends AbstractIdentifiable> void assertFilter(Class<T> aClass,String string,DataReadConfiguration configuration,Integer expected){
		assertFilter(aClass, string, null,configuration, expected);
	}
	
	private <T extends AbstractIdentifiable> void assertFilter(Class<T> aClass,String string,Integer expected){
		assertFilter(aClass, string, (DataReadConfiguration)null, expected);
	}
	
	private <T extends AbstractIdentifiable> void assertWithBlankStringFindByString(Class<T> aClass){
		Integer countAll = inject(PersistenceInterfaceLocator.class).injectTyped(aClass).countAll().intValue();
		assertFilter(aClass, (String)null, (DataReadConfiguration)null, countAll);
		assertFilter(aClass, Constant.EMPTY_STRING, (DataReadConfiguration)null, countAll);
	}
	
	
	/**/
    
}
