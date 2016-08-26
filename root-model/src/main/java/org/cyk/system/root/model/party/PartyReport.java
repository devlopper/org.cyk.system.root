package org.cyk.system.root.model.party;

import java.io.Serializable;

import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;
import org.cyk.system.root.model.geography.ContactCollectionReport;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PartyReport extends AbstractIdentifiableReport<PartyReport> implements Serializable {

	private static final long serialVersionUID = 5692082425509998915L;
	
	protected ContactCollectionReport contactCollection = new ContactCollectionReport();
	
	@Override
	public void generate() {
		super.generate();
		contactCollection.generate();
	}
	
}
