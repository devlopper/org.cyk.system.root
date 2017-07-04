package org.cyk.system.root.persistence.impl.integration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;

public class GraphQueryIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;
	
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	protected void populate() {
		GlobalIdentifier globalIdentifier = new GlobalIdentifier();
		globalIdentifier.setIdentifier("M");
		globalIdentifier.setCode("M");
		globalIdentifier.setName("Male");
		entityManager.persist(globalIdentifier);
		Sex sex = new Sex();
		sex.setGlobalIdentifier(globalIdentifier);
		create(sex);
	}
					
	@Override
	protected void create() {

	}

	@Override
	protected void read() {
		
	}

	@Override
	protected void update() {
		
	}

	@Override
	protected void delete() {
		
	}
	
	@Override
	protected void queries() {
		/*
		 * 
		 * EntityGraph<EmailMessage> eg = em.createEntityGraph(EmailMessage.class);
eg.addAttributeNodes("body");
...
Properties props = new Properties();
props.put("javax.persistence.fetchgraph", eg);
EmailMessage message = em.find(EmailMessage.class, id, props);
		 */
		assertEquals("Male", inject(GlobalIdentifierDao.class).readAll().iterator().next().getName()); 
		
	}
	
	
	
	
}
