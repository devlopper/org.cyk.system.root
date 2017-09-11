package org.cyk.system.root.business.impl.unit;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.impl.RootValueValidatorImpl;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.business.impl.validation.DefaultValidator;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.hibernate.validator.constraints.Email;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ValidatorUT extends AbstractUnitTest {

    private static final long serialVersionUID = -6691092648665798471L;

    //@Test
    public void namePattern() {
        System.out.println(RootValueValidatorImpl.NAME_PATTERN.matcher("0paul").find());
        System.out.println(RootValueValidatorImpl.NAME_PATTERN.matcher("p0aul").find());
        System.out.println(RootValueValidatorImpl.NAME_PATTERN.matcher("pa0ul").find());
        System.out.println(RootValueValidatorImpl.NAME_PATTERN.matcher("pau0l").find());
        System.out.println(RootValueValidatorImpl.NAME_PATTERN.matcher("paul0").find());
        System.out.println(RootValueValidatorImpl.NAME_PATTERN.matcher("paul").find());
    }
    
    @Test
    public void electronicMailFormat() {
    	ElectronicMail electronicMail = new ElectronicMail().setValue("a..@mail.com");
    	DefaultValidator validator = new DefaultValidator();
    	validator.validate(electronicMail);
    	System.out.println(validator.getMessagesAsString());
    }
    
    /**/
    
    @Getter @Setter @Accessors(chain=true)
    public static class ElectronicMail implements Serializable {
    	private static final long serialVersionUID = 1L;
    	
    	@Email @NotNull
		private String value;
    	
    }

}
