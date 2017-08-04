package org.cyk.system.root.business.api;

import java.io.Serializable;

import org.cyk.utility.common.builder.UrlStringBuilder;

public class UrlStringBuilderAdapter extends UrlStringBuilder.Listener.Adapter.Default implements Serializable {
	private static final long serialVersionUID = 1L;

	public UrlStringBuilderAdapter() {
		
	}
	
	public static class QueryStringBuilderAdapter extends UrlStringBuilder.QueryStringBuilder.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		
		
		
	}
	
}
