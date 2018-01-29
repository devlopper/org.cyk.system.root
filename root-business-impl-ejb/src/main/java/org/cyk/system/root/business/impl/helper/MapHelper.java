package org.cyk.system.root.business.impl.helper;

import java.io.Serializable;

public class MapHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class EntryStringifier extends org.cyk.utility.common.helper.MapHelper.Stringifier.Entry.Listener.Adapter.Default implements Serializable {
		
		private static final long serialVersionUID = 1L;
			
	}
	
	public static class Listener extends org.cyk.utility.common.helper.MapHelper.Listener.Adapter.Default implements Serializable {
		
		private static final long serialVersionUID = 1L;
	
		@Override
		public String getSeparatorOfValue() {
			return "&";
		}
		
		@Override
		public String getSeparatorOfKeyValue() {
			return "&";
		}
			
	}
}
