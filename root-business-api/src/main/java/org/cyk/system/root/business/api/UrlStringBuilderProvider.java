package org.cyk.system.root.business.api;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.builder.UrlStringBuilder;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton
public class UrlStringBuilderProvider extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public UrlStringBuilder getDynamicProcessManyPage(){
		UrlStringBuilder builder = new UrlStringBuilder();
		
		return builder;
	}
	
}
