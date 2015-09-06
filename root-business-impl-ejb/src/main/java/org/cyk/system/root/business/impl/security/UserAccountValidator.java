package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.model.security.UserAccount;

@Getter @Setter
public class UserAccountValidator extends AbstractValidator<UserAccount> implements Serializable {

    private static final long serialVersionUID = -3799482462496328200L;
    
    /**/
    
    
    /*
    @AssertTrue(message="{validation.person.name}",groups=org.cyk.utility.common.validation.Client.class)
    public boolean isValidName(){
    	return valueValidator.isValidPersonName(object.getName());
    }*/
     
}