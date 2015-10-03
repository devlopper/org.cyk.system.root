package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.party.person.AbstractActor;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Singleton
public class RootTestHelper extends AbstractTestHelper implements Serializable {

	private static final long serialVersionUID = 7237631838579839333L;

	/*
	@Inject protected GenericBusiness genericBusiness;
	
	public <T extends AbstractActor> void assertActorRegistrationCode(Class<T> classActor,T[] actors,String[] registrationCodes){
		for(int i=0;i<actors.length;i++){
			T actor = actors[i];
			assertEquals("Registration code of "+actor.getPerson(), registrationCodes[i], actor.getRegistration().getCode());
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractActor> T registerActor(Class<T> actorClass,String code,String[] names){
		T actor = RootRandomDataProvider.getInstance().actor(actorClass);
		actor.getRegistration().setCode(code);
		if(names!=null){
			if(names.length>0)
				actor.getPerson().setName(names[0]);
			if(names.length>1)
				actor.getPerson().setLastName(names[1]);
			if(names.length>2)
				actor.getPerson().setSurname(names[2]);
		}
		return (T) genericBusiness.create(actor);
	}
	
	public <T extends AbstractActor> Collection<T> registerStudents(Class<T> actorClass,String[] codes){
		Collection<T> students = new ArrayList<>();
		for(String code : codes)
			students.add(registerActor(actorClass,code, null));
		return students;
	}*/
	
}
