package org.cyk.system.root.business.impl.transfer;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.transfer.TransferTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.transfer.TransferType;
import org.cyk.system.root.persistence.api.transfer.TransferTypeDao;
 
public class TransferTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<TransferType,TransferTypeDao> implements TransferTypeBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public TransferTypeBusinessImpl(TransferTypeDao dao) {
        super(dao);
    } 

	/**/
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<TransferType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(TransferType.class);
		}
		
	}
	
}
