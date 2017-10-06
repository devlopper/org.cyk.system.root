package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.WebsiteBusiness;
import org.cyk.system.root.model.geography.Website;
import org.cyk.system.root.persistence.api.geography.WebsiteDao;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

public class WebsiteBusinessImpl extends AbstractContactBusinessImpl<Website, WebsiteDao> implements WebsiteBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public WebsiteBusinessImpl(WebsiteDao dao) {
		super(dao); 
	}
	
	@Getter @Setter
	public static class Details extends AbstractContactBusinessImpl.Details<Website> implements Serializable {

		private static final long serialVersionUID = 4444472169870625893L;

		@Input @InputText private FieldValue uniformResourceLocator;
		
		public Details(Website website) {
			super(website);
		}
		
		@Override
		public void setMaster(Website website) {
			super.setMaster(website);
			if(website!=null){
				uniformResourceLocator = new FieldValue(website.getUniformResourceLocator());
			}
		}

		public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
	}

	
}
