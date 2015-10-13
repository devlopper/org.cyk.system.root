package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractModelElement;

@Getter @Setter @Embeddable @NoArgsConstructor @AllArgsConstructor
public class Sort extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = -971774360718770716L;

	@Embedded private Average average = new Average();
	@Embedded private Rank rank = new Rank();
	@Column(length=1024) private String comments;
	
	@Transient private Interval averageInterval;
	
	@Override
	public String getUiString() {
		return average+" "+rank+" "+comments;
	} 
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}