package org.cyk.system.root.business.impl.integration;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.persistence.api.network.UniformResourceLocatorParameterDao;
import org.junit.Test;


public class UniformResourceLocatorBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void installApplication() {}
    
    @Test
	public void crud() {
    	TestCase testCase = instanciateTestCase();
    	UniformResourceLocator u1 = inject(UniformResourceLocator.Builder.class).setAddress("myadress01").build();
    	testCase.create(u1);
    	u1 = testCase.read(UniformResourceLocator.class, "myadress01");
    	assertEquals(0l, inject(UniformResourceLocatorParameterDao.class).countByUniformResourceLocator(u1));
    	
    	u1.getParameters().addOne(inject(UniformResourceLocatorParameter.Builder.class).set(u1,"p1", "v1").build() );
    	testCase.update(u1);
    	assertEquals(1l, inject(UniformResourceLocatorParameterDao.class).countByUniformResourceLocator(u1));
    	
    	u1 = testCase.read(UniformResourceLocator.class, "myadress01");
    	u1.getParameters().setElements(inject(UniformResourceLocatorParameterDao.class).readByUniformResourceLocator(u1));
    	u1.getParameters().addOne(inject(UniformResourceLocatorParameter.Builder.class).set(u1,"p2", "v2").build() );
    	testCase.update(u1);
    	assertEquals(2l, inject(UniformResourceLocatorParameterDao.class).countByUniformResourceLocator(u1));
    	
    	u1 = testCase.read(UniformResourceLocator.class, "myadress01");
    	u1.getParameters().setElements(inject(UniformResourceLocatorParameterDao.class).readByUniformResourceLocator(u1));
    	u1.getParameters().addOne(inject(UniformResourceLocatorParameter.Builder.class).set(u1,"p3", "v3").build() );
    	testCase.update(u1);
    	assertEquals(3l, inject(UniformResourceLocatorParameterDao.class).countByUniformResourceLocator(u1));
    	
    	u1 = testCase.read(UniformResourceLocator.class, "myadress01");
    	u1.getParameters().setElements(inject(UniformResourceLocatorParameterDao.class).readByUniformResourceLocator(u1));
    	u1.getParameters().addOne(inject(UniformResourceLocatorParameter.Builder.class).set(u1,"p4", "v5").build() );
    	
    	Collection<UniformResourceLocatorParameter> collection = new ArrayList<>();
    	for(UniformResourceLocatorParameter parameter : u1.getParameters().getElements()){
    		if(parameter.getName().equals("p3"))
    			parameter.setValue("newvalue");
    		if(!parameter.getName().equals("p2"))
    			collection.add(parameter);
    	}
    	u1.getParameters().setElements(collection);
    	testCase.update(u1);
    	assertEquals(3l, inject(UniformResourceLocatorParameterDao.class).countByUniformResourceLocator(u1));
    	
    	u1 = testCase.read(UniformResourceLocator.class, "myadress01");
    	u1.getParameters().setElements(inject(UniformResourceLocatorParameterDao.class).readByUniformResourceLocator(u1));
    	for(UniformResourceLocatorParameter parameter : u1.getParameters().getElements())
    		if(parameter.getName().equals("p1"))
    			assertEquals("v1", parameter.getValue());
    		else if(parameter.getName().equals("p3"))
    			assertEquals("newvalue", parameter.getValue());
    		else if(parameter.getName().equals("p4"))
    			assertEquals("v5", parameter.getValue());
    	testCase.clean();
	}
    
    @Override
    protected void businesses() {

    }
    

    
}
