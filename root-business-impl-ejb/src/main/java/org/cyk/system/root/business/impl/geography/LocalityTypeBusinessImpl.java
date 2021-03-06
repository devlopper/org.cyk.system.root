package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.persistence.api.geography.LocalityTypeDao;

import lombok.Getter;
import lombok.Setter;
 
public class LocalityTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<LocalityType,LocalityTypeDao> implements LocalityTypeBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public LocalityTypeBusinessImpl(LocalityTypeDao dao) {
        super(dao);
    } 

	/**/
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<LocalityType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(LocalityType.class);
		}
		
	}
	
	@Getter @Setter
	public static class Details extends AbstractDataTreeTypeBusinessImpl.Details<LocalityType> implements Serializable {

		private static final long serialVersionUID = -4747519269632371426L;

		public Details(LocalityType localityType) {
			super(localityType);
		}
		
	}

}
