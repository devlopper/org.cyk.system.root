package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Singleton;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.test.TestEnvironmentListener.Try;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Singleton
public class RootTestHelper extends AbstractTestHelper implements Serializable {

	private static final long serialVersionUID = 7237631838579839333L;

	/* Setters */
	
	public void set(Interval interval,String code,String name,String low,String high){
		interval.setCode(code);
		interval.setName(name);
		interval.getLow().setValue(commonUtils.getBigDecimal(low));
		interval.getHigh().setValue(commonUtils.getBigDecimal(high));
	}
	
	public void set(MovementCollection movementCollection,String code,String value,String low,String high,String incrementActionName,String decrementActionName){
		movementCollection.setCode(code);
		movementCollection.setName(code);
		movementCollection.setValue(value ==null ? null : new BigDecimal(value));
		movementCollection.setInterval(new Interval());
		set(movementCollection.getInterval(),code+"int", code+"intname", low,high);
		
		movementCollection.setIncrementAction(new MovementAction());
		set(movementCollection.getIncrementAction(), code+"inc",incrementActionName, "0", null);
		
		movementCollection.setDecrementAction(new MovementAction());
		set(movementCollection.getDecrementAction(), code+"dec",decrementActionName, "0", null);
		
	}
	
	public void set(MovementAction movementAction,String code,String name,String low,String high){
		movementAction.setCode(code);
		movementAction.setName(name);
		movementAction.setInterval(new Interval());
		set(movementAction.getInterval(),code, name, low,high);
	}
	
	public void set(Movement movement,String movementCollectionCode,String value){
		movement.setValue(value==null?null : new BigDecimal(value));
		movement.setCollection(rootBusinessLayer.getMovementCollectionBusiness().find(movementCollectionCode));
		movement.setCode(movementCollectionCode+"_"+RandomStringUtils.randomAlphabetic(3));
		movement.setName(movement.getCode());
		movement.setAction(movement.getValue() == null ? null : movement.getValue().signum() == 1 ? movement.getCollection().getIncrementAction() : movement.getCollection().getDecrementAction());
	}
	
	/* Businesses */
	
	public void createMovement(String movementCollectionCode,String value,String expectedValue,String expectedThrowableMessage){
    	final Movement movement = new Movement();
    	set(movement,movementCollectionCode, value);
    	
    	if(expectedThrowableMessage!=null){
    		new Try(expectedThrowableMessage){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {rootBusinessLayer.getMovementBusiness().create(movement);}
    		}.execute();
    	}else{
    		rootBusinessLayer.getMovementBusiness().create(movement);
    		assertMovementCollection(movement.getCollection(), expectedValue);
    	}
    }
	public void createMovement(String movementCollectionCode,String value,String expectedBalance){
		createMovement(movementCollectionCode,value, expectedBalance,null);
	}
	
	/* Assertions */
	
	private void assertMovementCollection(MovementCollection movementCollection,String expectedValue){
    	movementCollection = (MovementCollection) genericBusiness.use(MovementCollection.class).find(movementCollection.getIdentifier());
    	assertEquals("Value",new BigDecimal(expectedValue), movementCollection.getValue());
    }
	
	/* Exceptions */
	
	private void valueMustNotBeOffThanActionIntervalExtremity(String movementCollectionCode,Boolean incrementAction,Boolean lowExtemity){
		MovementCollection movementCollection = rootBusinessLayer.getMovementCollectionBusiness().find(movementCollectionCode);
		MovementAction action = Boolean.TRUE.equals(incrementAction) ? movementCollection.getIncrementAction() : movementCollection.getDecrementAction();
		BigDecimal value = Boolean.TRUE.equals(lowExtemity) ? action.getInterval().getLow().getValue() : action.getInterval().getHigh().getValue();
		if(value==null)
			return;
		createMovement(movementCollectionCode,value.toString(), null,getThrowableMessage(movementCollectionCode, isIncrementAction(value.toString()),0));
	}
	
	public void incrementValueMustNotBeLessThanIntervalLow(String movementCollectionCode){
		valueMustNotBeOffThanActionIntervalExtremity(movementCollectionCode, Boolean.TRUE, Boolean.TRUE);
	}
	public void incrementValueMustNotBeGreaterThanIntervalHigh(String movementCollectionCode){
		valueMustNotBeOffThanActionIntervalExtremity(movementCollectionCode, Boolean.TRUE, Boolean.FALSE);
	}
	public void decrementValueMustNotBeLessThanIntervalLow(String movementCollectionCode){
		valueMustNotBeOffThanActionIntervalExtremity(movementCollectionCode, Boolean.FALSE, Boolean.TRUE);
	}
	public void decrementValueMustNotBeGreaterThanIntervalHigh(String movementCollectionCode){
		valueMustNotBeOffThanActionIntervalExtremity(movementCollectionCode, Boolean.FALSE, Boolean.FALSE);
	}
	
	public void valueLowerThanActionMinimum(String movementCollectionCode,String value){
		createMovement(movementCollectionCode,value, null,getThrowableMessage(movementCollectionCode, isIncrementAction(value),1));
    }
	public void valueGreaterThanActionMaximum(String movementCollectionCode,String value){
		createMovement(movementCollectionCode,value, null,getThrowableMessage(movementCollectionCode, isIncrementAction(value),2));
    }
	
	public void valueGreaterThanHigh(String movementCollectionCode,String value){
		createMovement(movementCollectionCode,value, null,getThrowableMessage(movementCollectionCode,isIncrementAction(value),3));
    }
	public void valueLowerThanLow(String movementCollectionCode,String value){
		value = "-"+value;
		createMovement(movementCollectionCode,value, null,getThrowableMessage(movementCollectionCode, isIncrementAction(value),3));
    }
	
	
	private String getThrowableMessage(String movementCollectionCode,Boolean increment,Integer actionId){
		MovementCollection movementCollection = rootBusinessLayer.getMovementCollectionBusiness().find(movementCollectionCode);
		MovementAction action = Boolean.TRUE.equals(increment) ? movementCollection.getIncrementAction() : movementCollection.getDecrementAction();
		if(actionId==0)
			return String.format("%s doit être supérieur à %s",action.getName(),action.getInterval().getLow().getValue());
		
		if(actionId==1)
			return String.format("%s doit être supérieur à %s",action.getName(),action.getInterval().getLow().getValue());
		
		if(actionId==2)
			return String.format("%s doit être inférieur à %",action.getName(),action.getInterval().getHigh().getValue());
		
		if(actionId==3)
			return String.format("%s doit être entre %s et %s",action.getName()
				,rootBusinessLayer.getNumberBusiness().format(movementCollection.getInterval().getLow().getValue())
				,rootBusinessLayer.getNumberBusiness().format(movementCollection.getInterval().getHigh().getValue()));
		
		return Constant.EMPTY_STRING;
	}
	
	private Boolean isIncrementAction(String value){
		BigDecimal v = commonUtils.getBigDecimal(value);
		return v == null ? null : v.signum() == 0 ? null : v.signum() == 1;
	}
	
	/**/
    
    
	
}
