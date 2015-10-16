package org.cyk.system.root.business.impl;

import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.RootReportProducer;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.LabelValueCollectionReport;
import org.cyk.system.root.model.file.report.LabelValueReport;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ContactReport;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalReport;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.ActorReport;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.party.person.PersonReport;
import org.cyk.utility.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRootReportProducer extends AbstractRootBusinessBean implements RootReportProducer,Serializable {

	private static final long serialVersionUID = 7126711234011563710L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRootReportProducer.class);
	
	protected LabelValueCollectionReport currentLabelValueCollection;
	
	protected LabelValueCollectionReport labelValueCollection(String labelId){
		currentLabelValueCollection = new LabelValueCollectionReport();
		currentLabelValueCollection.setName(rootBusinessLayer.getLanguageBusiness().findText(labelId));
		return currentLabelValueCollection;
	}
	
	protected LabelValueReport labelValue(LabelValueCollectionReport collection,String id,String value,Boolean condition){
		if(!Boolean.TRUE.equals(condition))
			return null;
		currentLabelValueCollection = collection;
		return currentLabelValueCollection.add(id,languageBusiness.findText(id), value);
	}
	protected LabelValueReport labelValue(String labelId,String value,Boolean condition){
		return labelValue(currentLabelValueCollection, labelId, value,condition);
	}
	
	protected LabelValueReport labelValue(LabelValueCollectionReport collection,String labelId,String value){
		return labelValue(collection, labelId, value,Boolean.TRUE);
	}
	protected LabelValueReport labelValue(String labelId,String value){
		return labelValue(currentLabelValueCollection,labelId, value);
	}
	
	protected LabelValueReport getLabelValue(String id){
		return currentLabelValueCollection.getById(id);
	}
	
	protected void set(ContactCollection contactCollection,ContactReport report){
		report.setPhoneNumbers(StringUtils.join(contactCollection.getPhoneNumbers(),Constant.CHARACTER_COLON));
		report.setEmails(StringUtils.join(contactCollection.getElectronicMails(),Constant.CHARACTER_COLON));
		report.setLocations(StringUtils.join(contactCollection.getLocations(),Constant.CHARACTER_COLON));
		report.setPostalBoxs(StringUtils.join(contactCollection.getPostalBoxs(),Constant.CHARACTER_COLON));
		report.setWebsites(StringUtils.join(contactCollection.getWebsites(),Constant.CHARACTER_COLON));
	}
	
	protected void set(Person person,PersonReport report){
		rootBusinessLayer.getPersonBusiness().load(person);
		set(person.getContactCollection(), report.getContact());
		
		report.setName(person.getName());
		report.setLastName(person.getLastName());
		report.setNames(person.getNames());
		report.setSurname(person.getSurname());
		report.setBirthDate(format(person.getBirthDate()));
		report.setCode(person.getCode());
		
		if(person.getImage()!=null)
			report.setImage(findInputStream(person.getImage()));
		
		if(person.getNationality()!=null)
			report.setNationality(person.getNationality().getUiString());
		if(person.getSex()!=null)
			report.setSex(person.getSex().getName());
		
		if(person.getExtendedInformations()!=null){
			PersonExtendedInformations extendedInformations = person.getExtendedInformations();
			if(extendedInformations.getBirthLocation()!=null)
				report.setBirthLocation(extendedInformations.getBirthLocation().getUiString());
			if(extendedInformations.getTitle()!=null)
				report.setTitle(extendedInformations.getTitle().getUiString());
			if(extendedInformations.getSignatureSpecimen()!=null)
				report.setSignatureSpecimen(findInputStream(extendedInformations.getSignatureSpecimen()));
			if(extendedInformations.getMaritalStatus()!=null)
				report.setMaritalStatus(extendedInformations.getMaritalStatus().getName());
		}
		
		if(person.getJobInformations()!=null){
			JobInformations jobInformations = person.getJobInformations();
			if(jobInformations.getFunction()!=null)
				report.setJobFonction(jobInformations.getFunction().getName());
			if(jobInformations.getTitle()!=null)
				report.setJobTitle(jobInformations.getTitle().getName());
		}
	}
	
	protected void set(AbstractActor actor,ActorReport report){
		set(actor.getPerson(), report.getPerson());
		report.setRegistrationCode(actor.getRegistration().getCode());
		report.setRegistrationDate(format(actor.getRegistration().getDate()));
	}
	
	protected void set(Person person,ActorReport report){
		set(person, report.getPerson());
	}
	
	protected void set(Interval interval,IntervalReport report){
		report.setCode(interval.getCode());
		report.setName(/*format(interval.getLow())+" - "+format(interval.getHigh())+" "+*/interval.getName());
	}
	
	protected InputStream findInputStream(File file){
		return RootBusinessLayer.getInstance().getFileBusiness().findInputStream(file);
	}

	@Override
	protected Logger __logger__() {
		return LOGGER;
	}

}
