package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.cyk.system.root.business.api.geography.ContactBusiness;
import org.cyk.system.root.business.api.geography.ElectronicMailBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;

public class NotificationBuilderAdapter extends Notification.Builder.Listener.Adapter.Default implements Serializable {

	private static final long serialVersionUID = -289649019430290091L;

	public static Notification.Builder.Listener DEFAULT = new NotificationBuilderAdapter();
	
	@Override
	public String getTitle(Collection<AbstractIdentifiable> identifiables, RemoteEndPoint remoteEndPoint) {
		return "TITLE";//super.getTitle(identifiables, remoteEndPoint);
	}
	
	@Override
	public String getMessage(Collection<AbstractIdentifiable> identifiables, RemoteEndPoint remoteEndPoint) {
		return "";//super.getMessage(identifiables, remoteEndPoint);
	}
	
	@Override
	public Set<String> getReceiverIdentifiers(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint,Collection<String> partyCodes,Collection<String> personRelationshipCodes) {
		if(identifiables.size()==1){
			AbstractIdentifiable identifiable = identifiables.iterator().next();
			ContactCollection contactCollection = null;
			if(identifiable instanceof Party){
				contactCollection = ((Party)identifiable).getContactCollection();
			}else if(identifiable instanceof AbstractActor){
				contactCollection = ((AbstractActor)identifiable).getPerson().getContactCollection();
			}
			if(contactCollection==null)
				;
			else{
				Collection<ElectronicMail> electronicMails = inject(ContactBusiness.class).findByCollectionsByClass(Arrays.asList(contactCollection)
		    			, ElectronicMail.class);
				return new LinkedHashSet<>(inject(ElectronicMailBusiness.class).findAddresses(electronicMails));
			}
		}
		return super.getReceiverIdentifiers(identifiables, remoteEndPoint,partyCodes,personRelationshipCodes);
	}
	
	@Override
	public Set<File> getFiles(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint) {
		return super.getFiles(identifiables, remoteEndPoint);
	}
	
}
