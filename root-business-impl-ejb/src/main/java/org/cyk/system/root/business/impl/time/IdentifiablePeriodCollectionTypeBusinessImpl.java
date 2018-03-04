package org.cyk.system.root.business.impl.time;
import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.IdentifiablePeriodCollectionTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionType;
import org.cyk.system.root.model.time.IdentifiablePeriodType;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodCollectionTypeDao;

import lombok.Getter;
import lombok.Setter;
 
public class IdentifiablePeriodCollectionTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<IdentifiablePeriodCollectionType,IdentifiablePeriodCollectionTypeDao> implements IdentifiablePeriodCollectionTypeBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public IdentifiablePeriodCollectionTypeBusinessImpl(IdentifiablePeriodCollectionTypeDao dao) {
        super(dao);
    } 

	/**/
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<IdentifiablePeriodCollectionType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(IdentifiablePeriodCollectionType.class);
			addParameterArrayElementStringIndexInstance(2,IdentifiablePeriodType.FIELD_TIME_DIVISION_TYPE,3,IdentifiablePeriodType.FIELD_NUMBER_OF_NOT_CLOSED_AT_TIME_INTERVAL);
		}
		
	}
	
	@Getter @Setter
	public static class Details extends AbstractDataTreeTypeBusinessImpl.Details<IdentifiablePeriodCollectionType> implements Serializable {

		private static final long serialVersionUID = -4747519269632371426L;

		public Details(IdentifiablePeriodCollectionType movementCollectionType) {
			super(movementCollectionType);
		}
		
	}

}
