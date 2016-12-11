package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.value.MeasureTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.value.MeasureType;
import org.cyk.system.root.persistence.api.value.MeasureTypeDao;

public class MeasureTypeBusinessImpl extends AbstractEnumerationBusinessImpl<MeasureType, MeasureTypeDao> implements MeasureTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MeasureTypeBusinessImpl(MeasureTypeDao dao) {
		super(dao); 
	}

}
