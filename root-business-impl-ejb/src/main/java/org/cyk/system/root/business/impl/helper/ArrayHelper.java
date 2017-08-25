package org.cyk.system.root.business.impl.helper;

import java.io.Serializable;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ArrayHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter @Setter @Accessors(chain=true)
    public static class KeyBuilder extends org.cyk.utility.common.helper.ArrayHelper.Dimension.Key.Builder.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		private Boolean isGlobalIdentified;
		
		public KeyBuilder(Boolean isGlobalIdentified) {
			this.isGlobalIdentified = isGlobalIdentified;
		}
		
		public KeyBuilder() {
			this(null);
		}
		
		@Override
		protected org.cyk.utility.common.helper.ArrayHelper.Dimension.Key __execute__() {
			String identifier = (String)getInput()[0];
			return new org.cyk.utility.common.helper.ArrayHelper.Dimension.Key(Boolean.TRUE.equals(getIsGlobalIdentified(identifier)) 
					? org.cyk.utility.common.helper.InstanceHelper.Pool.getInstance().get(GlobalIdentifier.class, identifier).getCode() : identifier);
		}
		
		public Boolean getIsGlobalIdentified(String identifier){
			Boolean result = getIsGlobalIdentified();
			return result == null ? Boolean.FALSE : result;
		}
			
	}
	
}
