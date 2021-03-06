package org.cyk.system.root.business.impl.pattern.tree;

import javax.inject.Inject;

import org.cyk.system.root.business.api.pattern.tree.DataTreeBusiness;
import org.cyk.system.root.model.pattern.tree.DataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.DataTreeDao;

public class DataTreeBusinessImpl extends AbstractDataTreeBusinessImpl<DataTree,DataTreeDao,DataTreeType> implements DataTreeBusiness {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 8843694832726482311L;

	@Inject
    public DataTreeBusinessImpl(DataTreeDao dao) {
        super(dao);
    }
 
}
