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
		if(person.getJobInformations()!=null){
			company = person.getJobInformations().getCompany();
			if(person.getJobInformations().getFunction()!=null)
				function = person.getJobInformations().getFunction().getName();
			if(person.getJobInformations().getTitle()!=null)
				title = person.getJobInformations().getTitle().getName();
			if(person.getJobInformations().getContactCollection()!=null)
				contacts = StringUtils.join(person.getJobInformations().getContactCollection().getPhoneNumbers(),Constant.CHARACTER_COMA);
		}
	}
	
	public static final String LABEL_IDENTIFIER = "job";
	
	public static final String FIELD_TITLE = "title";
}