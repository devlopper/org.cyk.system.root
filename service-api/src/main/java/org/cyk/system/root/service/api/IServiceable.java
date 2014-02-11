package org.cyk.system.root.service.api;

import java.util.Collection;

import org.cyk.system.root.model.IModel;

public interface IServiceable <IDENTIFIABLE extends IModel<IDENTIFIER>,IDENTIFIER> {
	
	/* select */	IServiceable<IDENTIFIABLE,IDENTIFIER> find();
	
	/* filter */	IServiceable<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue);

	/* operator */	IServiceable<IDENTIFIABLE,IDENTIFIER> and();
	
					IServiceable<IDENTIFIABLE,IDENTIFIER> or();	
	
	/* read all*/	Collection<IDENTIFIABLE> all();
	
	/* read one*/	IDENTIFIABLE one();	

}
