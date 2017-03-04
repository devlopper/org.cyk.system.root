package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Embeddable @NoArgsConstructor @AllArgsConstructor
public class Sort extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = -971774360718770716L;

	@Embedded private Average average = new Average();
	@Embedded private Rank rank = new Rank();
	@Column(length=1024) private String comments;
	
	@ManyToOne private Interval averageAppreciatedInterval;
	@ManyToOne private Interval averagePromotedInterval;
	
	public Average getAverage(){
		if(average==null)
			average = new Average();
		return average;
	}
	
	public Rank getRank(){
		if(rank==null)
			rank = new Rank();
		return rank;
	}
	
	@Override
	public String getUiString() {
		return average+" "+rank+" "+comments;
	} 
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	@Override
	public String getLogMessage() {
		return String.format(LOG_FORMAT,average==null?Constant.EMPTY_STRING:average.getLogMessage()
				,rank==null?Constant.EMPTY_STRING:rank.getLogMessage()
				,averageAppreciatedInterval==null?Constant.EMPTY_STRING:averageAppreciatedInterval.getCode()
				,averagePromotedInterval==null?Constant.EMPTY_STRING:averagePromotedInterval.getCode());
	}
	private static final String LOG_FORMAT = Sort.class.getSimpleName()+"(%s %s %s)";
	
	public static final String FIELD_AVERAGE = "average";
	public static final String FIELD_RANK = "rank";
	public static final String FIELD_COMMENTS = "comments";
	public static final String FIELD_AVERAGE_APPRECIATED_INTERVAL = "averageAppreciatedInterval";
	public static final String FIELD_AVERAGE_PROMOTED_INTERVAL = "averagePromotedInterval";
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		private Average.SearchCriteria average = new Average.SearchCriteria();
		private Rank.SearchCriteria rank = new Rank.SearchCriteria();
		
		@Override
		public void set(String value) {
			
		}

		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			
		}
		
		
	}
}