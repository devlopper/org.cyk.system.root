package org.cyk.system.root.service.impl;

import java.util.Set;

import org.cyk.system.root.business.api.SpecificBusinessManager;
import org.cyk.utility.common.annotation.Model;
import org.cyk.utility.common.annotation.Model.CrudStrategy;
import org.cyk.utility.common.test.AbstractUnitTest;
import org.junit.Assert;
import org.reflections.Reflections;

public class ReflectionsUT extends AbstractUnitTest {

    private static final long serialVersionUID = -6691092648665798471L;

    @Model(crudStrategy = CrudStrategy.BUSINESS)
    public static class ClassA {
    };

    public static class ClassB extends ClassA {
    };

    @Override
    protected void _execute_() {
        super._execute_();
        Reflections reflections = new Reflections("org.cyk.system");
        Set<Class<? extends SpecificBusinessManager>> subTypes = reflections.getSubTypesOf(SpecificBusinessManager.class);
        Assert.assertEquals(subTypes.size(), 1);

    }

}
