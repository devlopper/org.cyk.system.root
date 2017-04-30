package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.file.FileValidator;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.FileExtensionGroup;

@Getter @Setter
public class PersonValidator extends AbstractValidator<Person> implements Serializable {

    private static final long serialVersionUID = -3799482462496328200L;
    
    /**/
    
    @Inject private FileValidator fileValidator;
    
    @Override
    protected void initialisation() {
    	super.initialisation();
    	fileValidator.getExtensions().clear();
    	fileValidator.getExtensions().addAll(FileExtensionGroup.IMAGE.getExtensions());
    	fileValidator.setMaximumSize(1l * 1024 * 1024);// 1M
    	registerFieldValidatorMap("image",fileValidator);
    }
    
    @AssertTrue(message="{validation.person.name}",groups=org.cyk.utility.common.validation.Client.class)
    public boolean isValidName(){
    	return valueValidator.isValidPersonName(object.getName());
    }
     
}