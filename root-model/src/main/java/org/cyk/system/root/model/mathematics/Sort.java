package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.utility.common.Constant;

@Getter @Setter @Embeddable @NoArgsConstructor @AllArgsConstructor
public class Sort extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = -971774360718770716L;

	@Embedded private Average average = new Average();
	@Embedded private Rank rank = new Rank();
	@Column(length=1024) private String comments;
	
	@ManyToOne private Interval averageInterval;
	
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
				,averageInterval==null?Constant.EMPTY_STRING:averageInterval.getCode());
	}
	private static final String LOG_FORMAT = Sort.class.getSimpleName()+"(%s %s %s)";
	
	public static final String FIELD_AVERAGE = "average";
	public static final String FIELD_RANK = "rank";
	public static final String FIELD_COMMENTS = "comments";
	public static final String FIELD_AVERAGEINTERVAL = "averageInterval";
	
}