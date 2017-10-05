package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.PostalBox;

@Getter @Setter
public class PostalBoxDetails extends AbstractContactDetails<PostalBox> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;

	private FieldValue country,type,locationType;
	
	public PostalBoxDetails(PostalBox postalBox) {
		super(postalBox);
	}

	
}
