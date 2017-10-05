package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.PostalBoxBusiness;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.system.root.persistence.api.geography.PostalBoxDao;

public class PostalBoxBusinessImpl extends AbstractContactBusinessImpl<PostalBox, PostalBoxDao> implements PostalBoxBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public PostalBoxBusinessImpl(PostalBoxDao dao) {
		super(dao); 
	}
	
}
