package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.geography.ElectronicMailBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeDao;

public class NotificationBuilderAdapter extends Notification.Builder.Listener.Adapter.Default implements Serializable {

	private static final long serialVersionUID = -289649019430290091L;

	public static Notification.Builder.Listener DEFAULT = new NotificationBuilderAdapter();
	
	@Override
	public String getSenderIdentifier(Collection<AbstractIdentifiable> identifiables, RemoteEndPoint remoteEndPoint) {
		if(RemoteEndPoint.MAIL_SERVER.equals(remoteEndPoint))
			return inject(ApplicationBusiness.class).findCurrentInstance().getSmtpProperties().getFrom();
		return super.getSenderIdentifier(identifiables, remoteEndPoint);
	}
	
	@Override
	public Set<String> getReceiverIdentifiers(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint,Boolean areIdentifiablesReceivers,Collection<String> partyCodes,Collection<String> personRelationshipTypeCodes) {
		Set<String> receiverIdentifiers = super.getReceiverIdentifiers(identifiables, remoteEndPoint,areIdentifiablesReceivers,partyCodes,personRelationshipTypeCodes);
		Collection<Party> parties = new LinkedHashSet<>();
		Collection<Person> persons = new LinkedHashSet<>(),relatedPersons = new LinkedHashSet<>();
		if(identifiables!=null)
			for(AbstractIdentifiable identifiable : identifiables)
				if(identifiable instanceof Party){
					parties.add((Party) identifiable);
					if(identifiable instanceof Person)
						persons.add((Person)identifiable);
				}else if(identifiable instanceof AbstractActor)
					persons.add( ((AbstractActor)identifiable).getPerson());
				
		if(personRelationshipTypeCodes!=null){
			Collection<PersonRelationshipType> personRelationshipTypes = inject(PersonRelationshipTypeDao.class).readByGlobalIdentifierCodes(personRelationshipTypeCodes);
			for(PersonRelationship personRelationship : inject(PersonRelationshipDao.class).readByPerson2ByTypes(persons, personRelationshipTypes))
				relatedPersons.add(personRelationship.getPerson1());
		}
		
		if(!Boolean.TRUE.equals(areIdentifiablesReceivers)){
			parties.clear();
			persons.clear();
		}
		
		parties.addAll(relatedPersons);
		persons.addAll(relatedPersons);
		
		Collection<ContactCollection> contactCollections = new LinkedHashSet<>();
		for(Party party : parties)
			contactCollections.add(party.getContactCollection());
		Collection<ElectronicMail> electronicMails = inject(ContactDao.class).readByCollectionsByClass(contactCollections, ElectronicMail.class);	
		if(!electronicMails.isEmpty()){
			if(receiverIdentifiers==null)
				receiverIdentifiers = new LinkedHashSet<>();
			receiverIdentifiers.addAll(inject(ElectronicMailBusiness.class).findAddresses(electronicMails));
		}
		return receiverIdentifiers;
	}
	
	@Override
	public Set<File> getFiles(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint,Set<String> fileRepresentationTypeCodes) {
		Set<File> files = super.getFiles(identifiables, remoteEndPoint,fileRepresentationTypeCodes);
		if(fileRepresentationTypeCodes!=null){
			Collection<File> identifiablesFiles = inject(FileBusiness.class)
					.findByRepresentationTypesByIdentifiables(inject(FileRepresentationTypeDao.class).readByGlobalIdentifierCodes(fileRepresentationTypeCodes), identifiables);
			if(identifiablesFiles.isEmpty()){
				
			}else{
				if(files==null)
					files = new LinkedHashSet<>();
				files.addAll(identifiablesFiles);
			}
		}
		
		return files;
	}
	
}
