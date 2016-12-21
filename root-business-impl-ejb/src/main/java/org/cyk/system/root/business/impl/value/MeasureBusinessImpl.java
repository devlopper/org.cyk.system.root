package org.cyk.system.root.business.impl.value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import javax.inject.Inject;

import org.cyk.system.root.business.api.value.MeasureBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.value.Measure;
import org.cyk.system.root.persistence.api.value.MeasureDao;
import org.cyk.system.root.persistence.api.value.MeasureTypeDao;

public class MeasureBusinessImpl extends AbstractEnumerationBusinessImpl<Measure, MeasureDao> implements MeasureBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MeasureBusinessImpl(MeasureDao dao) {
		super(dao); 
	}
	
	@Override
	public Measure instanciateOne(String[] values) {
		Measure measure = super.instanciateOne(values);
		Integer index = 10;
		measure.setType(inject(MeasureTypeDao.class).read(values[index++]));
		measure.setValue(commonUtils.getBigDecimal(values[index++]));
		return measure;
	}

	@Override
	public BigDecimal computeMultiple(Measure measure, BigDecimal value,MathContext mathContext) {
		if(measure==null || value==null)
			return null;
		return value.multiply(measure.getValue(),mathContext);
	}
	
	@Override
	public BigDecimal computeMultiple(Measure measure, BigDecimal value,Integer scale,RoundingMode roundingMode) {
		return computeMultiple(measure,value,new MathContext(scale, roundingMode));
	}
	
	@Override
	public BigDecimal computeMultiple(Measure measure, BigDecimal value) {
		return computeMultiple(measure,value,MATH_CONTEXT);
	}

	@Override
	public BigDecimal computeQuotient(Measure measure, BigDecimal value,MathContext mathContext) {
		if(measure==null || value==null)
			return null;
		return value.divide(measure.getValue(),mathContext);
	}  
	
	@Override
	public BigDecimal computeQuotient(Measure measure, BigDecimal value,Integer scale,RoundingMode roundingMode) {
		return computeQuotient(measure,value,new MathContext(scale,roundingMode));
	}  
	
	@Override
	public BigDecimal computeQuotient(Measure measure, BigDecimal value) {
		return computeQuotient(measure,value,MATH_CONTEXT);
	}  
	
	/**/
	
	public static MathContext MATH_CONTEXT = new MathContext(0, RoundingMode.HALF_DOWN);
}
