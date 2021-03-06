package org.cyk.system.root.model;

import java.io.Serializable;

import org.cyk.system.root.model.geography.ContactCollectionReport;
import org.cyk.system.root.model.party.person.ActorReport;
import org.cyk.system.root.model.party.person.PersonReport;

public class SampleData extends AbstractSampleData implements Serializable {

	private static final long serialVersionUID = 5898014003593786829L;

	public SampleData() {}
	
	public static ContactCollectionReport contactReport(){
		ContactCollectionReport report = new ContactCollectionReport();
		report.generate();
		return report;
	}
	
	public static PersonReport personReport(){
		PersonReport report = new PersonReport();
		report.generate();
		return report;
	}
	
	public static ActorReport actorReport(){
		ActorReport report = new ActorReport();
		report.generate();
		return report;
	}
}
