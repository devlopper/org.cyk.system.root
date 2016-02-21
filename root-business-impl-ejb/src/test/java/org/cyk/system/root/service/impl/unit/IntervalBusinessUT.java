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
	
	@Test
	public void findGreatestLowestValueExcluded() {
		Interval interval = new Interval(null, null, null, new BigDecimal("1"), new BigDecimal("5"));
		interval.getLow().setExcluded(Boolean.TRUE);
		Assert.assertEquals(new BigDecimal("0"),intervalBusiness.findGreatestLowestValue(interval));
	}
	
	@Test
	public void findGreatestLowestValueIncluded() {
		Interval interval = new Interval(null, null, null, new BigDecimal("1"), new BigDecimal("5"));
		interval.getLow().setExcluded(Boolean.FALSE);
		Assert.assertEquals(new BigDecimal("1"),intervalBusiness.findGreatestLowestValue(interval));
	}
	
	@Test
	public void findLowestGreatestValueExcluded() {
		Interval interval = new Interval(null, null, null, new BigDecimal("1"), new BigDecimal("5"));
		interval.getHigh().setExcluded(Boolean.TRUE);
		Assert.assertEquals(new BigDecimal("6"),intervalBusiness.findLowestGreatestValue(interval));
	}
	
	@Test
	public void findLowestGreatestValueIncluded() {
		Interval interval = new Interval(null, null, null, new BigDecimal("1"), new BigDecimal("5"));
		interval.getHigh().setExcluded(Boolean.FALSE);
		Assert.assertEquals(new BigDecimal("5"),intervalBusiness.findLowestGreatestValue(interval));
	}
	
	@Test
	public void isLowerEqualsToHigher() {
		Interval interval1 = new Interval(null, null, null, new BigDecimal("1"), new BigDecimal("5"));
		interval1.getHigh().setExcluded(Boolean.FALSE);
		Assert.assertEquals(Boolean.FALSE,intervalBusiness.isLowerEqualsToHigher(interval1));
		
		Interval interval2 = new Interval(null, null, null, new BigDecimal("1"), new BigDecimal("1"));
		interval2.getLow().setExcluded(Boolean.FALSE);
		interval2.getHigh().setExcluded(Boolean.FALSE);
		Assert.assertEquals(Boolean.TRUE,intervalBusiness.isLowerEqualsToHigher(interval2));
	}
	
	
}
