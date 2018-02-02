package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.helper.FilterHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @MappedSuperclass @ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public abstract class AbstractActor extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2742833783679362737L;

	@ManyToOne @JoinColumn(name=COLUMN_PERSON) @NotNull protected Person person;
	
	@Override
	public AbstractActor setCode(String code) {
		return (AbstractActor) super.setCode(code);
	}
	
	/**/
	
	public static final String FIELD_PERSON = "person";
	
	public static final String COLUMN_PERSON = FIELD_PERSON;
	
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
	
	@Getter @Setter
	public static class Filter<ACTOR extends AbstractActor> extends AbstractIdentifiable.Filter<ACTOR> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;

		protected Person.Filter person = new Person.Filter();
		
		public Filter() {
			addCriterias(person);
		}
		
		@Override
		public FilterHelper.Filter<ACTOR> set(String string) {
			person.set(string);
			return super.set(string);
		}
		
	}

}
