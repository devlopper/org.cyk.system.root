package org.cyk.system.root.business.impl.integration;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.junit.Test;


public class NumberBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Inject private NumberBusiness numberBusiness;
    
    @Test
	public void parse(){
		String number = numberBusiness.format(5000);
		for(int i = 0; i < number.length();i++)
			System.out.println((int)number.charAt(i) );
		System.out.println(numberBusiness.parse(BigDecimal.class, number));
	}

    @Override
    protected void businesses() {
        
    }

}
