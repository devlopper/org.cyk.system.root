package org.cyk.system.root.dao.impl.generic;


import java.sql.SQLException;

import javax.transaction.UserTransaction;

import lombok.AllArgsConstructor;

import org.cyk.system.root.dao.impl.AbstractPersistenceService;

@AllArgsConstructor
public abstract class Transaction {
	
	private UserTransaction transaction;
	private AbstractPersistenceService<?> dao;
	private Class<? extends SQLException> exceptionClassExpected;
	
	public void run(){
		try {
			transaction.begin();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		dao.getEntityManager().joinTransaction();
		_execute_();
		try {
			transaction.commit();
			//Assert.assertTrue(true);
		} catch (Exception exception) {
			if(exceptionClassExpected==null)
				throw new RuntimeException(exception);
			if(exception(exception)!=null)
				throw new RuntimeException();
		}
	}
	
	private Exception exception(Exception exception){
		Exception e = exception;
		while(e!=null && !e.getClass().equals(exceptionClassExpected))
			e = (Exception) e.getCause();
		return e;
	}
	
	public abstract void _execute_();
}
