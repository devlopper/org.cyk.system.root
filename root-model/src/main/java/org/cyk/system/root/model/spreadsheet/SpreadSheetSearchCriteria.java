package org.cyk.system.root.model.spreadsheet;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.search.AbstractPeriodSearchCriteria;

@Getter @Setter
public class SpreadSheetSearchCriteria extends AbstractPeriodSearchCriteria implements Serializable {

	private static final long serialVersionUID = 6796076474234170332L;

	
	public SpreadSheetSearchCriteria(Date fromDate,Date toDate) {
		super(fromDate,toDate);
	}
	
}
