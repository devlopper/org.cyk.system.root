package org.cyk.system.root.dao.impl;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CdiComponentTest {
	
	@Inject TestModel component;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(TestModel.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
        		;
    }

    @Test
    public void service() {
    	System.out.println("CDI : "+component);
    }
}