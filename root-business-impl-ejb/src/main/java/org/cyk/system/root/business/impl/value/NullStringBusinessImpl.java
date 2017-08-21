package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.value.NullStringBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.value.NullString;
import org.cyk.system.root.persistence.api.value.NullStringDao;

public class NullStringBusinessImpl extends AbstractEnumerationBusinessImpl<NullString, NullStringDao> implements NullStringBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public NullStringBusinessImpl(NullStringDao dao) {
		super(dao); 
	}

	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<NullString> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(NullString.class);
		}
		
	}
}
