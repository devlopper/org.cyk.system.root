package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostalBoxDetails extends AbstractContactDetails<PostalBox> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;

	@Input @InputText protected String address;
	
	public PostalBoxDetails(PostalBox postalBox) {
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
