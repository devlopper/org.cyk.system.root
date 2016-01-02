package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.mathematics.IntervalDao;

public class IntervalBusinessImpl extends AbstractCollectionItemBusinessImpl<Interval, IntervalDao,IntervalCollection> implements IntervalBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public IntervalBusinessImpl(IntervalDao dao) {
		super(dao); 
	}

	@Override
	public Interval findByCollectionByValue(IntervalCollection collection, BigDecimal value,Integer scale) {
		Collection<Interval> intervals = dao.readByCollection(collection);
		for(Interval interval : intervals)
			if(Boolean.TRUE.equals(contains(interval, value, scale)))
				return interval;
		return null;
	}  
	
	@Override
	public Boolean contains(Interval interval, BigDecimal value,Integer scale) {		
		BigDecimal low = interval.getLow().getValue(),high = interval.getHigh().getValue();
		if(value==null)
			return false;
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

	
	@Override
	public Collection<Interval> findByCollection(IntervalCollection collection) {
		return dao.readByCollection(collection);
	}  

    
}
