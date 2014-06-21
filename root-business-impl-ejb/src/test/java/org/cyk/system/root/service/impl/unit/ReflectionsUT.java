package org.cyk.system.root.service.impl.unit;

import java.util.Set;

import org.cyk.system.root.business.impl.AbstractBusinessLayer;
import org.cyk.utility.common.test.AbstractUnitTest;
import org.junit.Assert;
import org.reflections.Reflections;

public class ReflectionsUT extends AbstractUnitTest {

    private static final long serialVersionUID = -6691092648665798471L;

    @Override
    protected void _execute_() {
        super._execute_();
        Reflections reflections = new Reflections("org.cyk.system");
        Set<Class<? extends AbstractBusinessLayer>> subTypes = reflections.getSubTypesOf(AbstractBusinessLayer.class);
        Assert.assertEquals(subTypes.size(), 1);

    }

}
