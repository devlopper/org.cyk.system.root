package org.cyk.system.root.business.impl.pattern.tree;

import javax.inject.Inject;

import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.DataTreeTypeDao;

public class DataTreeTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<DataTreeType,DataTreeTypeDao> implements DataTreeTypeBusiness {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1313235130025226125L;

	@Inject
    public DataTreeTypeBusinessImpl(DataTreeTypeDao dao) {
        super(dao);
    } 

}
