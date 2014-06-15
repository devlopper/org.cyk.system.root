package org.cyk.system.root.persistence.impl.event;

import java.io.Serializable;

import org.cyk.system.root.model.event.Alarm;
import org.cyk.system.root.persistence.api.event.AlarmDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class AlarmDaoImpl extends AbstractTypedDao<Alarm> implements AlarmDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

}
 