package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.InformationStateBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.information.InformationState;
import org.cyk.system.root.persistence.api.information.InformationStateDao;

public class InformationStateBusinessImpl extends AbstractEnumerationBusinessImpl<InformationState,InformationStateDao> implements InformationStateBusiness,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public InformationStateBusinessImpl(InformationStateDao dao) {
        super(dao);
    } 

	/**/
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<InformationState> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(InformationState.class);
		}
	}
}
