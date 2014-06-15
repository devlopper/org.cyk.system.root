package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.AlarmBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.event.Alarm;
import org.cyk.system.root.persistence.api.event.AlarmDao;

public class AlarmBusinessImpl extends AbstractTypedBusinessService<Alarm, AlarmDao> implements AlarmBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public AlarmBusinessImpl(AlarmDao dao) {
		super(dao); 
	} 
	
}
