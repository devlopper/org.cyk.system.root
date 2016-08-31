package org.cyk.system.root.business.impl.unit;

import java.math.BigDecimal;
import java.util.Collection;

import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.business.impl.mathematics.NumberBusinessImpl;
import org.cyk.system.root.business.impl.time.TimeBusinessImpl;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class ExceptionUtilsUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private LanguageBusinessImpl languageBusiness;
	@InjectMocks private ExceptionUtils exceptionUtils;
	@InjectMocks private NumberBusinessImpl numberBusiness;
	@InjectMocks private TimeBusinessImpl timeBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(languageBusiness);
		collection.add(exceptionUtils);
		collection.add(numberBusiness);
		collection.add(timeBusiness);
		exceptionUtils.setLanguageBusiness(languageBusiness);
		exceptionUtils.setNumberBusiness(numberBusiness);
		exceptionUtils.setTimeBusiness(timeBusiness);
	}
	
	@Test
	public void exceptions() {
		new Try("Deux doit être supérieur à un"){ 
			private static final long serialVersionUID = -8176804174113453706L;
			@Override protected void code() {exceptionUtils.comparison(Boolean.TRUE, "deux", ArithmeticOperator.GT, "un");}
		}.execute();
		
		new Try("Deux doit être supérieur à 1"){ 
			private static final long serialVersionUID = -8176804174113453706L;
			@Override protected void code() {exceptionUtils.comparison(Boolean.TRUE, "deux", ArithmeticOperator.GT, BigDecimal.ONE);}
		}.execute();
		
		new Try("Un million doit être supérieur à 12,36"){ 
			private static final long serialVersionUID = -8176804174113453706L;
			@Override protected void code() {exceptionUtils.comparison(Boolean.TRUE, "un million", ArithmeticOperator.GT, new BigDecimal("12.36"));}
		}.execute();
		
	}
	
}
