package org.cyk.system.root.dao.impl.generic;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public abstract class AbstractDataAccessTest {
	
	@Inject protected UserTransaction transaction;
	
	@Before
	public final void before() throws Exception {
		_before_();
	}
	
	protected void _before_() throws Exception{
		
	}
	
	@After
	public final void after() throws Exception {
		_after_();
	}
	
	protected void _after_() throws Exception {
		
	}
	
	
}
