package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.ElectronicMail;

@Getter @Setter
public class ElectronicMailDetails extends AbstractContactDetails<ElectronicMail> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;

	public ElectronicMailDetails(ElectronicMail electronicMail) {
		super(electronicMail);
	}

}
