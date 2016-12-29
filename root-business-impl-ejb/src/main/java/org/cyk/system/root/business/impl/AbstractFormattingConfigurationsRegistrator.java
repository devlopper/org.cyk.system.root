package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.system.root.business.api.FormatterBusiness;

public abstract class AbstractFormattingConfigurationsRegistrator extends AbstractConfigurationsRegistrator implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected <T> void register(Class<T> aClass,AbstractFormatter<T> formatter){
		inject(FormatterBusiness.class).registerFormatter(aClass, formatter);
	}
	
}
