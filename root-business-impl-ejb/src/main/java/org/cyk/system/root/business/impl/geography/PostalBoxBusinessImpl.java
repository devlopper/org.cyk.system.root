package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.PostalBoxBusiness;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.system.root.persistence.api.geography.PostalBoxDao;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

public class PostalBoxBusinessImpl extends AbstractContactBusinessImpl<PostalBox, PostalBoxDao> implements PostalBoxBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public PostalBoxBusinessImpl(PostalBoxDao dao) {
		super(dao); 
	}
	
	@Getter @Setter
	public static class Details extends AbstractContactBusinessImpl.Details<PostalBox> implements Serializable {

		private static final long serialVersionUID = 4444472169870625893L;

		@Input @InputText protected String address;
		
		public Details(PostalBox postalBox) {
			super(postalBox);
		}

		@Override
		public void setMaster(PostalBox postalBox) {
			super.setMaster(postalBox);
			if(postalBox!=null){
				address = postalBox.getAddress();
			}
		}
		
		public static final String FIELD_ADDRESS = "address";	
	}

	
}
