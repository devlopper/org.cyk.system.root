package org.cyk.system.root.business.impl.utils;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;

public class IdentifiableCrudExecution<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements Runnable,Serializable {
	private static final long serialVersionUID = 1L;

	@Getter private Collection<IDENTIFIABLE> identifiables;
	@Getter private Crud crud;

	public IdentifiableCrudExecution(Collection<IDENTIFIABLE> identifiables,Crud crud) {
		super();
		this.identifiables = identifiables;
		this.crud = crud;
	}
	
	@Override
	public void run() {
		Collection<AbstractIdentifiable> identifiables = commonUtils.castCollection(this.identifiables, AbstractIdentifiable.class);
		switch(crud){
		case CREATE:
			inject(GenericBusiness.class).create(identifiables);
			break;
		case UPDATE:
			inject(GenericBusiness.class).update(identifiables);
			break;
		case DELETE:
			inject(GenericBusiness.class).delete(identifiables);
			break;
		default:
		}
	}
	
}
