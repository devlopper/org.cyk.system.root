package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeTypeBusiness;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeTypeDao;

public abstract class AbstractDataTreeTypeBusinessImpl<DATA_TREE_TYPE extends AbstractDataTreeType,DAO extends AbstractDataTreeTypeDao<DATA_TREE_TYPE>>  
    extends AbstractDataTreeNodeBusinessImpl<DATA_TREE_TYPE, DAO> implements AbstractDataTreeTypeBusiness<DATA_TREE_TYPE> {

	private static final long serialVersionUID = -538465405208352342L;

	public AbstractDataTreeTypeBusinessImpl(DAO dao) {
        super(dao);
    }
    
	public static class BuilderOneDimensionArray<T extends AbstractDataTreeType> extends AbstractDataTreeNodeBusinessImpl.BuilderOneDimensionArray<T> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray(Class<T> outputClass) {
			super(outputClass);
		}		    		
	}

}
