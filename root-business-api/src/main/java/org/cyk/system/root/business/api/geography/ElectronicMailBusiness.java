package org.cyk.system.root.business.api.geography;

import java.util.Collection;
import java.util.List;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;

public interface ElectronicMailBusiness extends AbstractContactBusiness<ElectronicMail> {

	ElectronicMail instanciateOne(ContactCollection collection,String address);
	List<ElectronicMail> instanciateMany(ContactCollection collection,List<String[]> values);
	List<ElectronicMail> instanciateMany(ContactCollection collection,String[] addresses);
	
	Collection<String> findAddresses(Collection<ElectronicMail> electronicMails);
}
