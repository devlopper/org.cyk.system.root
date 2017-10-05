package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.WebsiteBusiness;
import org.cyk.system.root.model.geography.Website;
import org.cyk.system.root.persistence.api.geography.WebsiteDao;

public class WebsiteBusinessImpl extends AbstractContactBusinessImpl<Website, WebsiteDao> implements WebsiteBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public WebsiteBusinessImpl(WebsiteDao dao) {
		super(dao); 
	}
	
}
