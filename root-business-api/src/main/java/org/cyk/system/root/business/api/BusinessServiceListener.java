package org.cyk.system.root.business.api;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractBean;

@Deprecated
public interface BusinessServiceListener {

	Collection<BusinessServiceListener> COLLECTION = new ArrayList<>();
	
	/**/
	
	void crudDone(Crud crud,AbstractIdentifiable identifiable);
	
	/**/
	/*
	public class Adapter extends AbstractBean implements BusinessServiceListener {

		private static final long serialVersionUID = 5130185664261469119L;

		@Override public void crudDone(Crud crud, AbstractIdentifiable identifiable) {}
		
	}*/
}
