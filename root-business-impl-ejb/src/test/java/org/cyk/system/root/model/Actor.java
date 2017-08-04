package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.search.StringSearchCriteria;

@Getter @Setter @Entity
public class Actor extends AbstractActor implements Serializable {

	private static final long serialVersionUID = 1548374606816696414L;

	public static class SearchCriteria extends AbstractActor.AbstractSearchCriteria<Actor> {

		private static final long serialVersionUID = -7909506438091294611L;

		public SearchCriteria() {
			this(null);
		} 

		public SearchCriteria(String name) {
			super(name);
		}

		@Override
		public void set(String value) {
			
		}

		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			
		}

	}

	
} 
