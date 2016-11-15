package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.ElectronicMailBusiness;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.persistence.api.geography.ElectronicMailDao;
import org.cyk.utility.common.generator.RandomDataProvider;

public class ElectronicMailBusinessImpl extends AbstractContactBusinessImpl<ElectronicMail, ElectronicMailDao> implements ElectronicMailBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ElectronicMailBusinessImpl(ElectronicMailDao dao) {
		super(dao); 
	}
	
	@Override
	public Contact instanciateOneRandomly() {
		return instanciateOne(NULL_CONTACT_COLLECTION,RandomDataProvider.getInstance().randomWord(RandomDataProvider.WORD_EMAIL, 5, 10));
	}

	@Override
	public ElectronicMail instanciateOne(ContactCollection collection, String address) {
		ElectronicMail electronicMail = new ElectronicMail();
		electronicMail.setAddress(address);
		return electronicMail;
	}

	@Override
	public List<ElectronicMail> instanciateMany(ContactCollection collection, List<String[]> values) {
		List<ElectronicMail> list = new ArrayList<>();
		for(String[] line : values)
			list.add(instanciateOne(collection,line[0]));
		return list;
	}

	@Override
	public List<ElectronicMail> instanciateMany(ContactCollection collection, String[] addresses) {
		List<ElectronicMail> list = new ArrayList<>();
		if(addresses!=null)
			for(String address : addresses)
				list.add(instanciateOne(collection,address));
		return list;
	}
	
	@Override
	public Collection<String> findAddresses(Collection<ElectronicMail> electronicMails) {
		Collection<String> addresses = new ArrayList<>();
		for(ElectronicMail electronicMail : electronicMails)
			addresses.add(electronicMail.getAddress());
		return addresses;
	}
	
	protected static final ContactCollection NULL_CONTACT_COLLECTION = null;

}
