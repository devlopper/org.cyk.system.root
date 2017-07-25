package org.cyk.system.root.business.impl.helper;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ArrayHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter @Setter @Accessors(chain=true)
    public static class KeyBuilder extends org.cyk.utility.common.helper.ArrayHelper.Dimension.Key.Builder.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@Override
		protected org.cyk.utility.common.helper.ArrayHelper.Dimension.Key __execute__() {
			return new org.cyk.utility.common.helper.ArrayHelper.Dimension.Key((String)getInput()[0]);
		}

    }
	
}
