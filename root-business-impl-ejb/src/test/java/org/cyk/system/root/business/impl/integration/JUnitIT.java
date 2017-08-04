package org.cyk.system.root.business.impl.integration;

import javax.ejb.EJBException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JUnitIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
 
    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    /**/
    
    @Rule public ExpectedException thrown= ExpectedException.none();
    
    @Override
    protected void businesses() {}
    
    @Test
    public void throwsNothing() {
        // no exception expected, none thrown: passes.
    }

    @Test
    public void throwsExceptionWithSpecificType() {
        thrown.expect(NullPointerException.class);
        throw new NullPointerException();
    }
    
   
    @Test
    public void throwsExceptionConditionnaly() {
    	//Integer a = null;
    	//String n = null;
    	try{
    		//Integer.parseInt(a.toString());
    		try{
    			throw new EJBException("From JUnit ooohhh");
    		}catch(EJBException ejbException){
    			System.out.println("JUnitIT.throwsExceptionConditionnaly() : EJB");
    			Assert.assertTrue(true);
    		}
    		//thrown.expect(EJBException.class);
    		//throw new EJBException("From JUnit ooohhh");
    	}catch(NullPointerException exception){
    		thrown.expect(NullPointerException.class);
    		throw new NullPointerException();
    	}
        
    }

}
