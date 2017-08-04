package org.cyk.system.root.business.impl.helper;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.helper.FieldHelper;

public class InstanceHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class BuilderOneDimensionArray<T extends AbstractIdentifiable> extends org.cyk.utility.common.helper.InstanceHelper.Builder.OneDimensionArray.Adapter.Default<T> implements Serializable{
    	private static final long serialVersionUID = 1L;
    	
    	public BuilderOneDimensionArray(Class<T> outputClass) {
			super(outputClass);
		}
    	
    	public BuilderOneDimensionArray<T> addFieldCodeName() {
			addParameterArrayElementString(FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE)
    			, FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
			return this;
		}
		
	}
	
}
