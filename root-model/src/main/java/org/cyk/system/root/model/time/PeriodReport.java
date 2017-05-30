package org.cyk.system.root.model.time;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.value.LongValueReport;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.builder.DatePeriodStringBuilder;
import org.cyk.utility.common.generator.AbstractGeneratable;

@Getter @Setter
public class PeriodReport extends AbstractGeneratable<PeriodReport> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fromDate,toDate;
	private String fromDateToDate,fromYearToYear;
	private LongValueReport numberOfMillisecond = new LongValueReport();
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		fromDate = format(((Period)source).getFromDate());
		toDate = format(((Period)source).getToDate());
		numberOfMillisecond.setSource(((Period)source).getNumberOfMillisecond());
		fromDateToDate = new DatePeriodStringBuilder(((Period)source).getFromDate(), ((Period)source).getToDate(), Constant.Date.Part.DATE_ONLY).build();
		fromYearToYear = new DatePeriodStringBuilder(((Period)source).getFromDate(), ((Period)source).getToDate(), Constant.Date.Part.DATE_YEAR_ONLY)
			.setSeparator(Constant.CHARACTER_SLASH.toString()).build();
	}
	
	@Override
	public void generate() {
		fromDate = "12/10/2013";
		toDate = "23/11/2014";
		fromDateToDate = "Du "+fromDate+" Au "+toDate;
		fromYearToYear = "2000/2001";
		numberOfMillisecond.generate();
	}

}
