package org.cyk.system.root.service.impl;

import java.io.Serializable;

import org.cyk.system.root.dao.impl.AbstractDao;
import org.cyk.system.root.model.AbstractModel;

public class AbstractTypedService<IDENTIFIABLE extends AbstractModel,TYPED_DAO extends AbstractDao<IDENTIFIABLE>> extends AbstractService implements Serializable {

	private static final long serialVersionUID = 6437552355933877400L;
	
	protected TYPED_DAO dao;
	
	public AbstractTypedService(TYPED_DAO dao) {
		super();
		this.dao = dao;
	}

}
