package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.model.party.Person;
import org.cyk.utility.common.validation.Client;

public class PersonValidator extends AbstractValidator<Person> implements Serializable {

    @AssertTrue(message="PROBLEME de NOM oh",groups=Client.class)
    public boolean isValidName(){
        System.out.println("RootPersonValidator.isValidName()");
        return StringUtils.startsWith("bc", object.getName());
    }
    
}
