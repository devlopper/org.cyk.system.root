package org.cyk.system.root.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;
import org.cyk.system.root.model.geography.ContactCollectionReport;

@Getter @Setter
public class PartyReport extends AbstractIdentifiableReport<PartyReport> implements Serializable {

	private static final long serialVersionUID = 5692082425509998915L;
	
	protected ContactCollectionReport contactCollection = new ContactCollectionReport();
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		if(source!=null){
			contactCollection.setSource( ((Party)source).getContactCollection() );
		}
	}
	
	@Override
	public void generate() {
		super.generate();
		contactCollection.generate();
	}
	
}
