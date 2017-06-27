package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.model.geography.LocalityType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LocalityTypeDetails extends AbstractDataTreeTypeDetails<LocalityType> implements Serializable {

	private static final long serialVersionUID = -4747519269632371426L;

	public LocalityTypeDetails(LocalityType localityType) {
		super(localityType);
	}
	
}
