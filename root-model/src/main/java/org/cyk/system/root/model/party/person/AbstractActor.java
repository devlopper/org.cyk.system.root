package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @MappedSuperclass @ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public abstract class AbstractActor extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2742833783679362737L;

	@ManyToOne
	protected Person person;
		
	@Embedded
	protected Registration registration = new Registration();
	
	@Override
	public String toString() {
		return getUiString();
	}
	
	@Override
	public String getUiString() {
		return registration.getCode()+" "+person.getUiString();
	}
	
	/**/
	
	public static final String FIELD_PERSON = "person";
	public static final String FIELD_REGISTRATION = "registration";
	
	/**/
	
	@Getter @Setter
	public static abstract class AbstractSearchCriteria<ACTOR extends AbstractActor> extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected Person.SearchCriteria person;
		
		public AbstractSearchCriteria(){
			this(null); 
		}
		
		public AbstractSearchCriteria(String name) {
			person = new Person.SearchCriteria(name);
		}
		
	}

}
