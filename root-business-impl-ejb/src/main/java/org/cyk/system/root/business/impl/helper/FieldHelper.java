package org.cyk.system.root.business.impl.helper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.helper.ArrayHelper;

public class FieldHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Collection<String> COPYABLE_FIELD_NAMES = new HashSet<>();
	static {
		for(String index : org.cyk.utility.common.helper.FieldHelper.getInstance().getNamesWhereReferencedByStaticField(GlobalIdentifier.class)){
			if(!ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_IMAGE,GlobalIdentifier.FIELD_SUPPORTING_DOCUMENT}, index))
				COPYABLE_FIELD_NAMES.add(org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,index));	
		}
	}
	
	/**/
	
	public void copy(AbstractIdentifiable source,AbstractIdentifiable destination,Boolean overwrite,String...fieldNames){
		org.cyk.utility.common.helper.FieldHelper.getInstance().copy(source, destination, ArrayHelper.getInstance().isEmpty(fieldNames) ? COPYABLE_FIELD_NAMES : Arrays.asList(fieldNames),overwrite);
	}
	
	public void copy(AbstractIdentifiable source,AbstractIdentifiable destination){
		copy(source, destination, Boolean.TRUE);
	}
	
	/**/
	
	private static FieldHelper INSTANCE;
	
	public static FieldHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new FieldHelper();
		return INSTANCE;
	}
}
