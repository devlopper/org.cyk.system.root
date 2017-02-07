package org.cyk.system.root.business.impl;

import org.cyk.utility.common.LogMessage.Builder;

public class NumberStringFormatter extends org.cyk.utility.common.formatter.NumberFormatter.String.Adapter.Default {
	private static final long serialVersionUID = 1L;

	public NumberStringFormatter(Number number, Builder logMessageBuilder) {
		super(number, logMessageBuilder);
	}
	
}
