package org.cyk.system.root.business.impl.integration;

import java.util.Arrays;
import java.util.Collection;

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
import org.cyk.system.root.persistence.api.geography.ElectronicMailAddressDao;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.junit.Test;

public class FindByStringIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Override
    protected void populate() {
    	super.populate();
    	/*Actor actor = inject(ActorBusiness.class).instanciateOneRandomly("c001");
    	actor.getPerson().setName("konan").setLastnames("marius").addElectronicMail("mymail@yahoo.fr"); 
    	actor.setName("konan");
    	create(actor);
    	
    	actor = inject(ActorBusiness.class).instanciateOneRandomly("c002");
    	actor.getPerson().setName("zanga").setLastnames("alice").addElectronicMail("konan@mail.com");
    	actor.setName("zanga");
    	create(actor);
    	
    	actor = inject(ActorBusiness.class).instanciateOneRandomly("c003a");
    	actor.getPerson().setName("doudou").setLastnames("cherif").addElectronicMail(null);
    	actor.setName("doudou");
    	create(actor);
    	*/
    	create(new JobFunction(null, null));
    	create(new JobFunction("mycode", null));
    	create(new JobFunction(null, "mylibeller"));
    	
    }
    
	@Override
	protected void businesses() {}
	
	@Test
	public void findPerson(){
		assertEquals(4l, inject(PersonDao.class).countAll());
		assertWithBlankStringFindByString(Person.class);
		
		assertFindByString(Person.class,"WXWX",0);
		
		assertFindByString(Person.class,"ko",Arrays.asList("c002"),1);
		assertFindByString(Person.class,"a",4);
		assertFindByString(Person.class,"a",Arrays.asList("c002"),3);
		
		assertFindByString(Person.class,"ius",1);
		assertFindByString(Person.class,"konan@mail.com",1);
		assertFindByString(Person.class,"@",2);
		assertFindByString(Person.class,"konan",2);
		assertFindByString(Person.class,"konan@",1);
	}
	
	@Test
	public void findActor(){
		assertWithBlankStringFindByString(Actor.class);
		
		assertFindByString(Actor.class,"WXWX",0);
		
		assertFindByString(Actor.class,"ko",Arrays.asList("c002"),1);
		assertFindByString(Actor.class,"a",3);
		assertFindByString(Actor.class,"a",Arrays.asList("c002"),2);

		assertFindByString(Actor.class,"ius",1);
		assertFindByString(Actor.class,"konan@mail.com",1);
		assertFindByString(Actor.class,"@",2);
		assertFindByString(Actor.class,"konan",2);
		assertFindByString(Actor.class,"konan@",1);
	}
	
	@Test
	public void findJobFunction(){
		assertWithBlankStringFindByString(JobFunction.class);
		
		assertFindByString(JobFunction.class,"rec",2);
		assertFindByString(JobFunction.class,"m",2);
		assertFindByString(JobFunction.class,"my",2);
		assertFindByString(JobFunction.class,"myc",1);
		assertFindByString(JobFunction.class,"myl",1);
		assertFindByString(JobFunction.class,"e",4);
		assertFindByString(JobFunction.class,"m",2);
		assertFindByString(JobFunction.class,"c",3);
		assertFindByString(JobFunction.class,"r",3);
	}
	
	@Test
	public void findLocality(){
		assertWithBlankStringFindByString(Locality.class);
		assertFindByString(Locality.class,null,null,new DataReadConfiguration().setMaximumResultCount(10l),10,60);
		assertFindByString(Locality.class,"ivoire",1);
	}
	
	@Test
	public void findElectronicMails(){
		assertWithBlankStringFindByString(ElectronicMailAddress.class);
		Integer allCount = inject(ElectronicMailAddressDao.class).countAll().intValue();
		assertFindByString(ElectronicMailAddress.class,"@",allCount);
		assertFindByString(ElectronicMailAddress.class,"kycdev@gmail.com",1);
		assertFindByString(ElectronicMailAddress.class,"mymail",1);
	}
	
	@Test
	public void findCredentials(){
		assertWithBlankStringFindByString(Credentials.class);
		assertFindByString(Credentials.class,"admin",1);
	}
	
	private <T extends AbstractIdentifiable> void assertFindByString(Class<T> aClass,String string,Collection<String> excludedCodes,DataReadConfiguration configuration,Integer expectedReturnCount,Integer expectedDatabaseCount){
		TypedBusiness<T> business = inject(BusinessInterfaceLocator.class).injectTyped(aClass);
		Collection<T> excludedIdentifiables = excludedCodes == null ? null : inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(excludedCodes);
		assertEquals(expectedReturnCount.intValue(), business.findByString(string, excludedIdentifiables,configuration).size());
		assertEquals(expectedDatabaseCount.longValue(), business.countByString(string,(Collection<T>) excludedIdentifiables));
	}
	
	private <T extends AbstractIdentifiable> void assertFindByString(Class<T> aClass,String string,Collection<String> excludedCodes,DataReadConfiguration configuration,Integer expectedReturnCount){
		assertFindByString(aClass, string, excludedCodes, configuration, expectedReturnCount, expectedReturnCount);
	}
	
	private <T extends AbstractIdentifiable> void assertFindByString(Class<T> aClass,String string,Collection<String> excludedCodes,Integer expected){
		assertFindByString(aClass, string, excludedCodes, null, expected);
	}
	
	private <T extends AbstractIdentifiable> void assertFindByString(Class<T> aClass,String string,DataReadConfiguration configuration,Integer expected){
		assertFindByString(aClass, string, null,configuration, expected);
	}
	
	private <T extends AbstractIdentifiable> void assertFindByString(Class<T> aClass,String string,Integer expected){
		assertFindByString(aClass, string, (DataReadConfiguration)null, expected);
	}
	
	private <T extends AbstractIdentifiable> void assertWithBlankStringFindByString(Class<T> aClass){
		Integer countAll = inject(PersistenceInterfaceLocator.class).injectTyped(aClass).countAll().intValue();
		assertFindByString(aClass, (String)null, (DataReadConfiguration)null, countAll);
		assertFindByString(aClass, Constant.EMPTY_STRING, (DataReadConfiguration)null, countAll);
	}
	
	
	/**/
    
}
