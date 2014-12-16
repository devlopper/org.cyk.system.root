package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.validation.Client;

public class PersonValidator extends AbstractValidator<Person> implements Serializable {

    private static final long serialVersionUID = -3799482462496328200L;
    
    @AssertTrue(message="{validation.person.name}",groups=Client.class)
    public boolean isValidName(){
        return StringUtils.startsWith("bc", object.getName());
    }
    
}
