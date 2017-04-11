package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.pattern.tree.NestedSetBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetDao;

public class NestedSetBusinessImpl extends AbstractTypedBusinessService<NestedSet, NestedSetDao> implements NestedSetBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public NestedSetBusinessImpl(NestedSetDao dao) {
		super(dao); 
	}
	
	
}
