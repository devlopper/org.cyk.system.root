package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.TimeDivisionTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.persistence.api.time.TimeDivisionTypeDao;

public class TimeDivisionTypeBusinessImpl extends AbstractEnumerationBusinessImpl<TimeDivisionType, TimeDivisionTypeDao> implements TimeDivisionTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public TimeDivisionTypeBusinessImpl(TimeDivisionTypeDao dao) {
		super(dao); 
	}   
	
}
