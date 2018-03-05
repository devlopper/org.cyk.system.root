package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.IdentifiablePeriodTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.time.IdentifiablePeriodType;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodTypeDao;

public class IdentifiablePeriodTypeBusinessImpl extends AbstractEnumerationBusinessImpl<IdentifiablePeriodType, IdentifiablePeriodTypeDao> implements IdentifiablePeriodTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public IdentifiablePeriodTypeBusinessImpl(IdentifiablePeriodTypeDao dao) {
		super(dao); 
	}
	
	/**/
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<IdentifiablePeriodType> implements Serializable {

		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(IdentifiablePeriodType.class);
			
		}
	}
	
}
