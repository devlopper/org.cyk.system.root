package org.cyk.system.root.business.impl.helper;

import java.io.Serializable;

public class MapHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class EntryStringifierListener extends org.cyk.utility.common.helper.MapHelper.Stringifier.Entry.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

		/*@Override
		public Object getValue(Object object) {
			if(object instanceof AbstractModelElement)
				return ((AbstractIdentifiable)object).getUiString();
			return super.getValue(object);
		}*/
			
	}
}
