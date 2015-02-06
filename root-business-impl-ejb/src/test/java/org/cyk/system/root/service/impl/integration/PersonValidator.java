package org.cyk.system.root.service.impl.integration;

import java.io.Serializable;

import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.validation.Client;

@Getter
public class PersonValidator extends AbstractValidator<Person> implements Serializable {

    private static final long serialVersionUID = -3799482462496328200L;
    
    @Inject private FileValidator fileValidator;
    
    @AssertTrue(message="{validation.person.name}",groups=Client.class)
    public boolean isValidName(){
        return !StringUtils.startsWith(object.getName(),"0");
    }
     
    @Override
    protected void manualProcess() {
    	try {
			fileValidator.validate(object.getImage());
		} catch (Exception e) {
			messages.addAll(fileValidator.getMessages());
		}
    }
    
}