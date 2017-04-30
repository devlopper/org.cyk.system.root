package org.cyk.system.root.business.impl.security;

import org.cyk.system.root.business.api.security.AbstractSecuredViewBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.security.AbstractSecuredView;
import org.cyk.system.root.persistence.api.security.AbstractSecuredViewDao;

public abstract class AbstractSecuredViewBusinessImpl<VIEW extends AbstractSecuredView<ACCESSOR>,DAO extends AbstractSecuredViewDao<VIEW,ACCESSOR>,ACCESSOR> 
    extends AbstractTypedBusinessService<VIEW, DAO> implements AbstractSecuredViewBusiness<VIEW,ACCESSOR> {
  
	private static final long serialVersionUID = -2383681421917333298L;
	
	public AbstractSecuredViewBusinessImpl(DAO dao) {
		super(dao);
	}

}
