package org.cyk.system.root.business.impl.helper;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;

public class FieldHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Collection<String> COPYABLE_FIELD_NAMES = new HashSet<>();
	static {
		for(String index : org.cyk.utility.common.helper.FieldHelper.getInstance().getNamesWhereReferencedByStaticField(GlobalIdentifier.class)){
			COPYABLE_FIELD_NAMES.add(org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,index));	
		}
	}
	
	/**/
	
	public void copy(AbstractIdentifiable source,AbstractIdentifiable destination){
		org.cyk.utility.common.helper.FieldHelper.getInstance().copy(source, destination, COPYABLE_FIELD_NAMES);
	}
	
	/**/
	
	private static FieldHelper INSTANCE;
	
	public static FieldHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new FieldHelper();
		return INSTANCE;
	}
}
