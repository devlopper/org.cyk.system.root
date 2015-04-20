package org.cyk.system.root.service.impl.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.cyk.utility.test.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;

public class BigDecimalRoundingUT extends AbstractUnitTest {

    private static final long serialVersionUID = -6691092648665798471L;

    
    @Override
    protected void _execute_() {
        super._execute_();
    }
    
    @Test
    public void ceiling(){
    	round("10.062", "10.07",RoundingMode.CEILING,2);
    	round("40.25", "40.25",RoundingMode.CEILING,2);
    }
    
    @Test
    public void down(){
    	round("10.062", "10.06",RoundingMode.DOWN,2);
    	round("40.25", "40.25",RoundingMode.DOWN,2);
    }
    
    @Test
    public void floor(){
    	round("10.062", "10.06",RoundingMode.FLOOR,2);
    	round("40.25", "40.25",RoundingMode.FLOOR,2);
    }
    
    @Test
    public void halfDown(){
    	round("10.062", "10.06",RoundingMode.HALF_DOWN,2);
    	round("40.25", "40.25",RoundingMode.HALF_DOWN,2);
    }
    
    @Test
    public void halfEven(){
    	round("10.062", "10.06",RoundingMode.HALF_EVEN,2);
    	round("40.25", "40.25",RoundingMode.HALF_EVEN,2);
    }
    
    @Test
    public void halfUp(){
    	round("10.062", "10.06",RoundingMode.HALF_UP,2);
    	round("40.25", "40.25",RoundingMode.HALF_UP,2);
    }
    
    //@Test
    public void unecessary(){
    	round("10.062", "10.06",RoundingMode.UNNECESSARY,2);
    	round("40.25", "40.25",RoundingMode.UNNECESSARY,2);
    }
    
    @Test
    public void up(){
    	round("10.062", "10.07",RoundingMode.UP,2);
    	round("40.25", "40.25",RoundingMode.UP,2);
    }

    private void round(String number,String expected,RoundingMode roundingMode,Integer scale){
    	BigDecimal b = new BigDecimal(number/*, new MathContext(scale, roundingMode)*/);
    	
    	b = b.setScale(2,roundingMode);
    	Assert.assertEquals(new BigDecimal(expected), b);
    }

}
