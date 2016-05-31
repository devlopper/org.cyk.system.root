package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.BigDecimalSearchCriteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Embeddable @NoArgsConstructor @AllArgsConstructor
public class Average extends AbstractModelElement implements Serializable{

	private static final long serialVersionUID = -4640228506073441626L;

	@Column(name="average_dividend",precision=20,scale=FLOAT_SCALE) private BigDecimal dividend;
	@Column(name="average_divisor",precision=20,scale=FLOAT_SCALE) private BigDecimal divisor;
	@Column(name="average_value",precision=20,scale=FLOAT_SCALE) private BigDecimal value;
	
	@Override
	public String toString() {
		return dividend+"/"+divisor+" = "+value;
	}

	@Override
	public String getUiString() {
		return value.toString();
	}
	
	@Override
	public String getLogMessage() {
		return String.format(LOG_FORMAT,dividend,divisor,value);
	}
	private static final String LOG_FORMAT = Average.class.getSimpleName()+"(%s / %s = %s)";
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		private BigDecimalSearchCriteria dividend = new BigDecimalSearchCriteria();
		private BigDecimalSearchCriteria divisor = new BigDecimalSearchCriteria();
		private BigDecimalSearchCriteria value = new BigDecimalSearchCriteria();
		
	}
}
