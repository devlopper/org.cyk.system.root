package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.system.root.persistence.api.mathematics.IntervalCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.IntervalDao;

public class IntervalBusinessImpl extends AbstractCollectionItemBusinessImpl<Interval, IntervalDao,IntervalCollection> implements IntervalBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public IntervalBusinessImpl(IntervalDao dao) {
		super(dao); 
	}
	
	@Override
	public Interval create(Interval interval) {
		super.create(interval);
		updateCollection(interval);
		return interval;
	}
	
	@Override
	public Interval update(Interval interval) {
		interval = super.update(interval);
		updateCollection(interval);
		return interval;
	}
	
	private void updateCollection(Interval interval){
		if(interval.getCollection()==null)
			return;
		IntervalCollection collection = interval.getCollection();
		IntervalCollectionDao intervalCollectionDao = inject(IntervalCollectionDao.class);
		collection.setLowestValue(intervalCollectionDao.readLowestValue(collection));
		collection.setHighestValue(intervalCollectionDao.readHighestValue(collection));
		intervalCollectionDao.update(collection);
	}
	
	@Override
	public Interval instanciateOne(String[] values,InstanciateOneListener listener) {
		Interval interval = super.instanciateOne(values, listener);
		interval.getLow().setValue(commonUtils.getBigDecimal(commonUtils.getValueAt(values, 2)));
		interval.getHigh().setValue(commonUtils.getBigDecimal(commonUtils.getValueAt(values, 3)));
		return interval;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Interval instanciateOne(IntervalCollection collection, String code, String low, String high) {
		Interval interval = new Interval(collection, code, code, commonUtils.getBigDecimal(low), commonUtils.getBigDecimal(high));
		
		return interval;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Interval findByCollectionByValue(IntervalCollection collection, BigDecimal value,Integer scale) {
		Collection<Interval> intervals = dao.readByCollection(collection);
		for(Interval interval : intervals)
			if(Boolean.TRUE.equals(contains(interval, value, scale)))
				return interval;
		return null;
	}  
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean isLowerEqualsToHigher(Interval interval) {
		BigDecimal l1=findGreatestLowestValue(interval),l2=findLowestGreatestValue(interval);
		return l1!=null && l2!=null && l1.equals(l2);
	}
	
	private Boolean isOutOfExtremity(IntervalExtremity intervalExtremity,Boolean low, BigDecimal value, Integer scale) {
		if(value==null || intervalExtremity.getValue()==null)
			return false;
		BigDecimal correctedScale = scale==null?value:value.setScale(scale,RoundingMode.DOWN);//truncates
		Integer comparison = correctedScale.compareTo(intervalExtremity.getValue());
		if(Boolean.TRUE.equals(intervalExtremity.getExcluded()))
			if(Boolean.TRUE.equals(low))
				return comparison<0;
			else
				return comparison>0;
		else
			if(Boolean.TRUE.equals(low))
				return comparison<=0;
			else
				return comparison>=0;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean isLower(Interval interval, BigDecimal value, Integer scale) {
		return isOutOfExtremity(interval.getLow(), Boolean.TRUE, value, scale);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean isHigher(Interval interval, BigDecimal value, Integer scale) {
		return isOutOfExtremity(interval.getHigh(), Boolean.FALSE, value, scale);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean contains(Interval interval, BigDecimal value,Integer scale) {		
		if(value==null)
			return false;
		BigDecimal low = interval.getLow().getValue(),high = interval.getHigh().getValue();
		if(low==null && high==null)
			return true;//(value==null);
		//if(value==null)
		//	return false;
		
		BigDecimal correctedScale = scale==null?value:value.setScale(scale,RoundingMode.DOWN);//truncates
		if(low==null)
			if(Boolean.TRUE.equals(interval.getHigh().getExcluded()))
				return correctedScale.compareTo(high)<0;
			else
				return correctedScale.compareTo(high)<=0;
		
		if(high==null)
			if(Boolean.TRUE.equals(interval.getLow().getExcluded()))
				return correctedScale.compareTo(low)>0;
			else
				return correctedScale.compareTo(low)>=0;
				
		int c1 = low.compareTo(correctedScale),c2 = high.compareTo(correctedScale);
		
		Boolean ok1,ok2;
		if(Boolean.TRUE.equals(interval.getLow().getExcluded()))
			ok1 = c1<0;
		else
			ok1 = c1<=0;
		
		if(Boolean.TRUE.equals(interval.getHigh().getExcluded()))
			ok2 = c2 >0;
		else
			ok2 = c2 >=0;	
		
		return ok1 && ok2;
	}

	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<Interval> findByCollection(IntervalCollection collection) {
		return dao.readByCollection(collection);
	}  

    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public BigDecimal findLowestGreatestValue(Interval interval) {
    	/*if(interval==null)
    		return null;*/
    	return interval.getHigh().getValue()==null ? null 
    			: Boolean.FALSE.equals(interval.getHigh().getExcluded()) ? interval.getHigh().getValue() : interval.getHigh().getValue().add(BigDecimal.ONE);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public BigDecimal findGreatestLowestValue(Interval interval) {
    	/*if(interval==null)
    		return null;*/
    	return interval.getLow().getValue()==null ? null 
    			: Boolean.FALSE.equals(interval.getLow().getExcluded()) ? interval.getLow().getValue() : interval.getLow().getValue().subtract(BigDecimal.ONE);
    }

	/*
    @Override
	public <NUMBER extends Number> Collection<Interval> instanciateManyFromNumberSequence(Collection<NUMBER> numbers,InstanciateManyFromNumberSequenceArguments<NUMBER> arguments) {
    	Collection<Interval> intervals = new ArrayList<>();
    	Interval interval = null;
    	for(NUMBER number : numbers){
    		if(interval == null){
    			interval = new Interval();
    			interval.getLow().setValue(new BigDecimal(number.toString()));
    		}else{
    			if()
    			interval.getHigh().setValue(new BigDecimal(number.toString()));
    			
    		}
    	}
		return intervals;
	}*/

    
}
