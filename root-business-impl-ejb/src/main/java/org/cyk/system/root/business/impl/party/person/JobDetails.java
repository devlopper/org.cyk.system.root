package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JobDetails extends AbstractOutputDetails<Person> implements Serializable {
	
	private static final long serialVersionUID = -1498269103849317057L;
	
	@Input @InputText private String company,function,title,contacts;
	
	public JobDetails(Person person) {
		super(person);
	}
	
	@Override
	public void setMaster(Person person) {
		super.setMaster(person);
		if(person.getJobInformations()!=null){
			company = formatUsingBusiness(person.getJobInformations().getCompany());
			if(person.getJobInformations().getFunction()!=null)
				function = formatUsingBusiness(person.getJobInformations().getFunction());
			if(person.getJobInformations().getTitle()!=null)
				title = formatUsingBusiness(person.getJobInformations().getTitle());
			if(person.getJobInformations().getContactCollection()!=null)
				contacts = StringUtils.join(person.getJobInformations().getContactCollection().getPhoneNumbers(),Constant.CHARACTER_COMA);
		}
	}
	
	public static final String LABEL_IDENTIFIER = "job";
	
	public static final String FIELD_COMPANY = "company";
	public static final String FIELD_FUNCTION = "function";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_CONTACTS = "contacts";
}