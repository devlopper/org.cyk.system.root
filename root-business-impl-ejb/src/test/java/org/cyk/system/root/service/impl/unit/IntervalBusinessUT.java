package org.cyk.system.root.service.impl.unit;

import java.math.BigDecimal;
import java.util.Collection;

import org.cyk.system.root.business.impl.mathematics.IntervalBusinessImpl;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

public class IntervalBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private IntervalBusinessImpl intervalBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(intervalBusiness);
	}
	
	@Test
	public void contains() {
		Interval interval = new Interval(null, null, null, new BigDecimal("1"), new BigDecimal("5"));
		Assert.assertTrue(intervalBusiness.contains(interval, new BigDecimal("3"), 0));
	}

	@Test
	public void isLower() {
		Interval interval = new Interval(null, null, null, new BigDecimal("1"), new BigDecimal("5"));
		Assert.assertTrue(intervalBusiness.isLower(interval, new BigDecimal("0"), 0));
	}
	
	@Test
	public void isHigher() {
		Interval interval = new Interval(null, null, null, new BigDecimal("1"), new BigDecimal("5"));
		Assert.assertTrue(intervalBusiness.isHigher(interval, new BigDecimal("6"), 0));
	}
}
