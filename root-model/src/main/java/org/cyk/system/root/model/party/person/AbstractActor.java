package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @MappedSuperclass @ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public abstract class AbstractActor extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2742833783679362737L;

	@ManyToOne @NotNull protected Person person;
		
	@Override
	public String toString() {
		return getUiString();
	}
	
	@Override
	public String getUiString() {
		return globalIdentifier.getCode()+Constant.CHARACTER_SPACE+person.getNames();
	}
	
	/**/
	
	public static final String FIELD_PERSON = "person";
	public static final String FIELD_REGISTRATION = "registration";
	
	/**/
	
	@Getter @Setter
	public static abstract class AbstractSearchCriteria<ACTOR extends AbstractActor> extends AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected Person.SearchCriteria person;
		
		public AbstractSearchCriteria(){
			this(null); 
		}
		
		public AbstractSearchCriteria(String name) {
			super(name);
			person = new Person.SearchCriteria(name);
		}
		
		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			super.set(stringSearchCriteria);
			person.set(stringSearchCriteria);
		}
		
		@Override
		public void set(String value) {
			super.set(value);
			person.set(value);
		}
		
		/**/
		
		public static class Default extends AbstractSearchCriteria<AbstractActor> {
			private static final long serialVersionUID = 1L;
			
		}
		
	}

}
