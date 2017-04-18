package org.cyk.system.root.business.api.geography;

import java.util.Collection;
import java.util.List;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;

public interface ElectronicMailBusiness extends AbstractContactBusiness<ElectronicMail> {

	ElectronicMail instanciateOne(ContactCollection collection,String address);
	List<ElectronicMail> instanciateMany(ContactCollection collection,List<String[]> values);
	List<ElectronicMail> instanciateMany(ContactCollection collection,String[] addresses);
	
	@Deprecated
	Collection<String> findAddresses(Collection<ElectronicMail> electronicMails);
	
	void setAddress(Party party,String value);
	void setAddress(Person person, String personRelationshipTypeCode, String value);
	
	String findAddress(Party party);
	String findAddress(Person person, String personRelationshipTypeCode);
	Collection<String> findAddresses(Person person, Collection<String> personRelationshipTypeCodes);
}
