package org.cyk.system.root.business.api.geography;

import java.util.Collection;
import java.util.List;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;

public interface ElectronicMailAddressBusiness extends AbstractContactBusiness<ElectronicMailAddress> {

	ElectronicMailAddress instanciateOne(ContactCollection collection,String address);
	List<ElectronicMailAddress> instanciateMany(ContactCollection collection,List<String[]> values);
	List<ElectronicMailAddress> instanciateMany(ContactCollection collection,String[] addresses);
	
	@Deprecated
	Collection<String> findAddresses(Collection<ElectronicMailAddress> electronicMails);
	
	@Deprecated //move it to else where
	void setAddress(Party party,String value);
	@Deprecated //move it to else where
	void setAddress(Person person, String personRelationshipTypeCode, String value);
	
	String findAddress(Party party);
	String findAddress(Person person, String personRelationshipTypeCode);
	Collection<String> findAddresses(Person person, Collection<String> personRelationshipTypeCodes);
}
