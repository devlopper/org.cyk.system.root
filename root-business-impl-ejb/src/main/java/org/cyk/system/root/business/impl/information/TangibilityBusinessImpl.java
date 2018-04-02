package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.TangibilityBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.information.Tangibility;
import org.cyk.system.root.persistence.api.information.TangibilityDao;

public class TangibilityBusinessImpl extends AbstractTypedBusinessService<Tangibility,TangibilityDao> implements TangibilityBusiness,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public TangibilityBusinessImpl(TangibilityDao dao) {
        super(dao);
    } 

}
