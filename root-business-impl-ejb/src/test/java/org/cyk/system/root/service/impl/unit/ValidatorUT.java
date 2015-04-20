package org.cyk.system.root.service.impl.unit;

import org.cyk.system.root.business.impl.RootValueValidatorImpl;
import org.cyk.utility.test.AbstractUnitTest;
import org.junit.Test;

public class ValidatorUT extends AbstractUnitTest {

    private static final long serialVersionUID = -6691092648665798471L;

    @Test
    public void namePattern() {
        System.out.println(RootValueValidatorImpl.NAME_PATTERN.matcher("0paul").find());
        System.out.println(RootValueValidatorImpl.NAME_PATTERN.matcher("p0aul").find());
        System.out.println(RootValueValidatorImpl.NAME_PATTERN.matcher("pa0ul").find());
        System.out.println(RootValueValidatorImpl.NAME_PATTERN.matcher("pau0l").find());
        System.out.println(RootValueValidatorImpl.NAME_PATTERN.matcher("paul0").find());
        System.out.println(RootValueValidatorImpl.NAME_PATTERN.matcher("paul").find());
    }

}
