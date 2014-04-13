package org.cyk.system.root.dao.impl;


import java.sql.SQLException;

import javax.transaction.UserTransaction;

public abstract class Transaction {
	
	private AbstractPersistenceIT persistenceIT;
	private UserTransaction transaction;
	//private PersistenceService<?, ?> dao;
	private Class<? extends SQLException> exceptionClassExpected;
	
	public Transaction(AbstractPersistenceIT persistenceIT, UserTransaction transaction, Class<? extends SQLException> exceptionClassExpected) {
		super();
		this.persistenceIT = persistenceIT;
		this.transaction = transaction;
		this.exceptionClassExpected = exceptionClassExpected;
	}

	public void run(){
		try {
			transaction.begin();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		((AbstractPersistenceService<?>)persistenceIT.getGenericDao()).getEntityManager().joinTransaction();
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
