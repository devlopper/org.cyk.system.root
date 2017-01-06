package org.cyk.system.root.model.time;

import java.io.Serializable;

import org.cyk.utility.common.generator.AbstractGeneratable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PeriodReport extends AbstractGeneratable<PeriodReport> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String from,to,numberOfMillisecond;
	
	@Override
	public void generate() {
		from = "12/10/2013";
		to = "23/11/2014";
	}

}
