package org.cyk.system.root.business.impl.integration;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.__test__.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;
import org.cyk.system.root.persistence.api.party.ApplicationDao;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.impl.EntityGraphBuilder;
import org.cyk.system.root.persistence.impl.GenericDaoImpl;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.utility.common.helper.FieldHelper;
import org.junit.Test;

public class EntityGraphIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 7023768959389316273L;
	
    @Override
    protected void businesses() {}
    
    @Test
    public void fetchGlobalIdentifierIdentifier(){
    	TestCase testCase = instanciateTestCase();
    	testCase.create(inject(PersonBusiness.class).instanciateOneRandomly("gidP001"));
    	Map<String,Object> hints = new HashMap<>();
    	EntityGraphBuilder<GlobalIdentifier> entityGraphBuilder = new EntityGraphBuilder<>();
    	entityGraphBuilder.setResultClass(GlobalIdentifier.class);
    	entityGraphBuilder.setEntityManager( ((GenericDaoImpl)inject(GenericDao.class)).getEntityManager() );
    	entityGraphBuilder.setAttributes("identifier","name");
    	hints.put(QueryWrapper.HINT_FETCH_GRAPH, entityGraphBuilder.build());
    	debug(inject(GlobalIdentifierDao.class).readByCode("gidP001"));
    	fetch(inject(GlobalIdentifierDao.class).readByCode("gidP001"), inject(GlobalIdentifierDao.class).readByCode("gidP001",hints), new String[]{"image"});
    	testCase.clean();
    }
    
    //@Test
    public void fetchApplicationIdentifier(){
    	ApplicationDao applicationDao1 = inject(ApplicationDao.class);
    	Application application1 = applicationDao1.readOneRandomly();
    	
    	ApplicationDao applicationDao2 = inject(ApplicationDao.class);
    	applicationDao2.getDataReadConfig().addAttributes("identifier");
    	Application application2 = applicationDao2.read(application1.getCode());
    	
    	fetch(application1, application2, new String[]{"globalIdentifier"});
    }
    
    /*@Test
    public void fetchGlobalIdentifierCode(){
    	GlobalIdentifierDao globalIdentifierDao1 = inject(GlobalIdentifierDao.class);
    	GlobalIdentifier globalIdentifier1 = globalIdentifierDao1.readAll().iterator().next();
    	
    	GlobalIdentifierDao globalIdentifierDao2 = inject(GlobalIdentifierDao.class);
    	globalIdentifierDao2.getDataReadConfig().addAttributes("globalIdentifier.code");
    	GlobalIdentifier globalIdentifier2 = globalIdentifierDao2.read(globalIdentifier1.getCode());
    	
    	fetch(globalIdentifier1, globalIdentifier2, new String[]{"globalIdentifier.owner"});
    }*/

    //@Test
    public void fetchApplicationGlobalIdentifierUsingBuilder(){
    	EntityGraphBuilder<Application> entityGraphBuilder = new EntityGraphBuilder<>();
    	entityGraphBuilder.setResultClass(Application.class);
    	entityGraphBuilder.setEntityManager( ((GenericDaoImpl)inject(GenericDao.class)).getEntityManager() );
    	entityGraphBuilder.setAttributes("identifier");
    	
    	ApplicationDao applicationDao1 = inject(ApplicationDao.class);
    	Application application1 = applicationDao1.readOneRandomly();
    	
    	ApplicationDao applicationDao2 = inject(ApplicationDao.class);
    	applicationDao2.getDataReadConfig().setHint(QueryWrapper.HINT_FETCH_GRAPH, entityGraphBuilder.build());
    	Application application2 = applicationDao2.read(application1.getCode());
    	
    	fetch(application1, application2, new String[]{"globalIdentifier"});
    }
    
    @Test
    public void fetchPersonGlobalIdentifierUsingBuilderSub(){
    	create(inject(PersonBusiness.class).instanciateOneRandomly("p001"));
    	create(inject(PersonBusiness.class).instanciateOneRandomly("p002"));
    	
    	EntityGraphBuilder<Person> personEntityGraphBuilder = new EntityGraphBuilder<>();
    	personEntityGraphBuilder.setResultClass(Person.class);
    	personEntityGraphBuilder.setEntityManager( ((GenericDaoImpl)inject(GenericDao.class)).getEntityManager() );
    	personEntityGraphBuilder.setAttributes("identifier","globalIdentifier");
    	
    	PersonDao personDao1 = inject(PersonDao.class);
    	Person person1 = personDao1.read("p001");
    	
    	PersonDao personDao2 = inject(PersonDao.class);
    	EntityGraph<Person> entityGraph = personEntityGraphBuilder.build();
    	entityGraph.addSubgraph("globalIdentifier").addAttributeNodes("identifier");
    	personDao2.getDataReadConfig().setHint(QueryWrapper.HINT_FETCH_GRAPH, entityGraph);
    	Person person2 = personDao2.read("p002");
    	
    	assertThat("person1 image is null", person1.getImage()!=null);
    	assertThat("person2 image is not null", person2.getImage()==null);
    	//debug(person1.getGlobalIdentifier());
    	//debug(person2.getGlobalIdentifier());
    	
    	//fetch(person1, person2, new String[]{"globalIdentifier.image"});
    }
    
    @Test
    public void fetchPersonGlobalIdentifierUsingBuilderSub01(){
    	create(inject(PersonBusiness.class).instanciateOneRandomly("pl001"));
    	create(inject(PersonBusiness.class).instanciateOneRandomly("pl002"));
    	
    	PersonDao personDao1 = inject(PersonDao.class);
    	Person person1 = personDao1.read("pl001");
    	
    	PersonDao personDao2 = inject(PersonDao.class);
    	@SuppressWarnings("unchecked")
		EntityGraph<Person> entityGraph = (EntityGraph<Person>) ((GenericDaoImpl)inject(GenericDao.class)).getEntityManager().createEntityGraph("pl");
    	entityGraph.addSubgraph("globalIdentifier").addAttributeNodes("identifier");
    	personDao2.getDataReadConfig().setHint(QueryWrapper.HINT_FETCH_GRAPH, entityGraph);
    	Person person2 = personDao2.read("pl002");
    	
    	assertThat("person1 image is null", person1.getImage()!=null);
    	assertThat("person2 image is not null", person2.getImage()==null);
    	//debug(person1.getGlobalIdentifier());
    	//debug(person2.getGlobalIdentifier());
    	
    	//fetch(person1, person2, new String[]{"globalIdentifier.image"});
    }
    
    /**/
    
    private void fetch(Object instance1,Object instance2,String[] nullAttributes) {
    	FieldHelper fieldHelper = FieldHelper.getInstance();
    	for(String nullAttribute : nullAttributes){
    		assertThat(nullAttribute+" not fetched", fieldHelper.read(instance1, nullAttribute)!=null);
    		assertThat(nullAttribute+" fetched", fieldHelper.read(instance2, nullAttribute)==null);
    	}
    }

}
