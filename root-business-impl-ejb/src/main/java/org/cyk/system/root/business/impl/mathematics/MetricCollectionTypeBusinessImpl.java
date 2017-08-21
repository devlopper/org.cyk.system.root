package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MetricCollectionTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionTypeDao;

public class MetricCollectionTypeBusinessImpl extends AbstractEnumerationBusinessImpl<MetricCollectionType, MetricCollectionTypeDao> implements MetricCollectionTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MetricCollectionTypeBusinessImpl(MetricCollectionTypeDao dao) {
		super(dao); 
	}

	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<MetricCollectionType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(MetricCollectionType.class);
			
		}
		
	}
}
