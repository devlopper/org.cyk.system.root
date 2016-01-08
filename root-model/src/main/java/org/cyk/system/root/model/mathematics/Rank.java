package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.cyk.system.root.model.AbstractModelElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Embeddable
public class Rank extends AbstractModelElement implements Serializable{

	private static final long serialVersionUID = -2665387343931538712L;
	
	@Column(name="rank_sequence_order")
	private Integer sequenceOrder;
	
	@Column(name="rank_value")
	private Integer value;
	
	@Column(name="rank_exaequo")
	private Boolean exaequo;
		
	@Override
	public String toString() {
		return value+" - "+exaequo;
	}

	@Override
	public String getUiString() {
		return toString();
	}
	
	@Override
	public String getLogMessage() {
		return String.format(LOG_FORMAT,sequenceOrder,value,exaequo);
	}
	private static final String LOG_FORMAT = Average.class.getSimpleName()+"(S=%s V=%s E=%s)";
	
}
