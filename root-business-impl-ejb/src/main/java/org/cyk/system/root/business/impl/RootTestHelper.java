package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Singleton;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.utility.common.Constant;
import org.cyk.utility.test.AbstractTest.Try;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Singleton
public class RootTestHelper extends AbstractTestHelper implements Serializable {

	private static final long serialVersionUID = 7237631838579839333L;

	public void set(MovementCollection movementCollection,String code,String value,String low,String high){
		movementCollection.setCode(code);
		movementCollection.setName(code);
		movementCollection.setValue(value ==null ? null : new BigDecimal(value));
		movementCollection.setInterval(new Interval(null, code+"int", code+"intname", low ==null ? null : new BigDecimal(low)
				,high==null ? null : new BigDecimal(high)));
	}
	
	public void set(Movement movement,String movementCollectionCode,String value){
		movement.setValue(value==null?null : new BigDecimal(value));
		movement.setCollection(rootBusinessLayer.getMovementCollectionBusiness().find(movementCollectionCode));
		movement.setCode(movementCollectionCode+"_"+RandomStringUtils.randomAlphabetic(3));
		movement.setName(movement.getCode());
	}
	
	public void doMovement(String movementCollectionCode,String amount,String expectedValue,Boolean throwableExpected){
    	final Movement movement = new Movement();
    	set(movement,movementCollectionCode, amount);
    	
    	if(Boolean.TRUE.equals(throwableExpected)){
    		new Try(Constant.EMPTY_STRING){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {rootBusinessLayer.getMovementBusiness().create(movement);}
    		}.execute();
    	}else{
    		rootBusinessLayer.getMovementBusiness().create(movement);
    		assertMovementCollection(movement.getCollection(), expectedValue);
    	}
    }
	public void doMovement(String movementCollectionCode,String amount,String expectedBalance){
		doMovement(movementCollectionCode,amount, expectedBalance,null);
	}
	public void valueGreaterThanHigh(String movementCollectionCode,String amount){
		doMovement(movementCollectionCode,amount, null,Boolean.TRUE);
    }
	public void valueLowerThanLow(String movementCollectionCode,String amount){
		doMovement(movementCollectionCode,"-"+amount, null,Boolean.TRUE);
    }
    
    private void assertMovementCollection(MovementCollection movementCollection,String expectedValue){
    	movementCollection = (MovementCollection) genericBusiness.use(MovementCollection.class).find(movementCollection.getIdentifier());
    	assertEquals("Value",new BigDecimal(expectedValue), movementCollection.getValue());
    }
	
}
