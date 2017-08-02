package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;

@Entity 
@Getter @Setter
public class ScheduleItem extends AbstractCollectionItem<Schedule> implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	@Embedded private InstantInterval instantInterval;
	
	{
		if(instantInterval==null)
			instantInterval = new InstantInterval();
	}
	
	public InstantInterval getInstantInterval(){
		if(instantInterval==null)
			instantInterval = new InstantInterval();
		return instantInterval;
	}
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected InstantInterval.SearchCriteria instantInterval = new InstantInterval.SearchCriteria();
		
		public SearchCriteria(){ 
			this(null);
		}
		
		public SearchCriteria(String name) {
			super(name);
		}
		
	}
	
	/**/
	
	public static final String FIELD_INSTANT_INTERVAL = "instantInterval";
	
}
