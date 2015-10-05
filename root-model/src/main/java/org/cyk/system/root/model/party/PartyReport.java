package org.cyk.system.root.model.party;

import java.io.InputStream;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.ContactReport;
import org.cyk.utility.common.generator.AbstractGeneratable;

@Getter @Setter
public class PartyReport extends AbstractGeneratable<PartyReport> implements Serializable {

	private static final long serialVersionUID = 5692082425509998915L;
	
	protected String code,name;
	
	protected InputStream image;
	protected Boolean generateImage=Boolean.FALSE;
	
	protected ContactReport contact = new ContactReport();
	
	@Override
	public void generate() {
		contact.generate();
	}
	
}
