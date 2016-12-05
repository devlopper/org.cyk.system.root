package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.value.ValuePropertiesBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.system.root.persistence.api.value.ValuePropertiesDao;

public class ValuePropertiesBusinessImpl extends AbstractTypedBusinessService<ValueProperties, ValuePropertiesDao> implements ValuePropertiesBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ValuePropertiesBusinessImpl(ValuePropertiesDao dao) {
		super(dao); 
	}

	

}
