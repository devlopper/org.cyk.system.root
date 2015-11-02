package org.cyk.system.root.persistence.impl.spreadsheet;

import java.util.Collection;

import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheet;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetCell;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;
import org.cyk.system.root.model.spreadsheet.SpreadSheetSearchCriteria;
import org.cyk.system.root.persistence.api.spreadsheet.AbstractSpreadSheetDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.event.AbstractIdentifiablePeriodDaoImpl;

public class AbstractSpreadSheetDaoImpl<SPREADSHEET extends AbstractSpreadSheet<TEMPLATE,ROW, COLUMN,CELL>,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,CELL extends AbstractSpreadSheetCell<SPREADSHEET, ROW, COLUMN, VALUE>,VALUE,SEARCH_CRITERIA extends SpreadSheetSearchCriteria> extends AbstractIdentifiablePeriodDaoImpl<SPREADSHEET> 
	implements AbstractSpreadSheetDao<SPREADSHEET,TEMPLATE,ROW,COLUMN,CELL,VALUE,SEARCH_CRITERIA> {

	private static final long serialVersionUID = 6920278182318788380L;
	
	private static final String READ_BY_CRITERIA_SELECT_FORMAT = "SELECT ps FROM Production ps ";
	private static final String READ_BY_CRITERIA_WHERE_FORMAT = "WHERE ps.period.fromDate BETWEEN :fromDate AND :toDate ";
	
	private static final String READ_BY_CRITERIA_NOTORDERED_FORMAT = READ_BY_CRITERIA_SELECT_FORMAT+READ_BY_CRITERIA_WHERE_FORMAT;
	private static final String READ_BY_CRITERIA_ORDERED_FORMAT = READ_BY_CRITERIA_SELECT_FORMAT+READ_BY_CRITERIA_WHERE_FORMAT+ORDER_BY_FORMAT;
	
	private String readAllSortedByDate,readByCriteria,countByCriteria,readByCriteriaDateAscendingOrder,readByCriteriaDateDescendingOrder;

	@Override
    protected void namedQueriesInitialisation() {
    	super.namedQueriesInitialisation();
    	/*registerNamedQuery(readAllSortedByDate,READ_BY_CRITERIA_SELECT_FORMAT+" ORDER BY ps.period.fromDate ASC");
    	registerNamedQuery(readByCriteria,READ_BY_CRITERIA_NOTORDERED_FORMAT+" ORDER BY ps.period.fromDate ASC");
        registerNamedQuery(readByCriteriaDateAscendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "ps.period.fromDate ASC") );
        registerNamedQuery(readByCriteriaDateDescendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "ps.period.fromDate DESC") );*/
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<SPREADSHEET> readByCriteria(SEARCH_CRITERIA searchCriteria) {
		String queryName = null;
		if(searchCriteria.getFromDateSearchCriteria().getAscendingOrdered()!=null){
			queryName = Boolean.TRUE.equals(searchCriteria.getFromDateSearchCriteria().getAscendingOrdered())?
					readByCriteriaDateAscendingOrder:readByCriteriaDateDescendingOrder;
		}else
			queryName = readByCriteriaDateAscendingOrder;
		QueryWrapper<?> queryWrapper = namedQuery(queryName);
		applyPeriodSearchCriteriaParameters(queryWrapper, searchCriteria);
		return (Collection<SPREADSHEET>) queryWrapper.resultMany();
	}

	@Override
	public Long countByCriteria(SEARCH_CRITERIA searchCriteria) {
		QueryWrapper<?> queryWrapper = countNamedQuery(countByCriteria);
		applyPeriodSearchCriteriaParameters(queryWrapper, searchCriteria);
		return (Long) queryWrapper.resultOne();
	}	
	
}
