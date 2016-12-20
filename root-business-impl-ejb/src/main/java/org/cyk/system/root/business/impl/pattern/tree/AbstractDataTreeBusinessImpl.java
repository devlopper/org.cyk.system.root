package org.cyk.system.root.business.impl.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;

public abstract class AbstractDataTreeBusinessImpl<ENUMERATION extends AbstractDataTree<TYPE>,DAO extends AbstractDataTreeDao<ENUMERATION,TYPE>,TYPE extends AbstractDataTreeType>  
    extends AbstractDataTreeNodeBusinessImpl<ENUMERATION, DAO> implements AbstractDataTreeBusiness<ENUMERATION,TYPE> {

	private static final long serialVersionUID = -2471233478597735673L;

	public AbstractDataTreeBusinessImpl(DAO dao) {
        super(dao);
    }
	
	@Override
	public Collection<ENUMERATION> findByType(TYPE type) {
		return dao.readByType(type);
	}
	public Long countByType(TYPE type) {
		return dao.countByType(type);
	}
	
	@Override
	public Collection<ENUMERATION> findByParentByType(ENUMERATION parent,TYPE type) {
		return dao.readByParentByType(parent, type);
	}
	
	@Override
	public Long countByParentByType(ENUMERATION parent, TYPE type) {
		return dao.countByParentByType(parent, type);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ENUMERATION instanciateOne(String[] values) {
		ENUMERATION enumeration = super.instanciateOne(values);
		Integer index = getInstanciateOneDataTreeTypeStartIndex(values);
		if(values.length>index)
			enumeration.setType((TYPE) inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>) commonUtils.getClassParameterAt(getClass(), 2))
				.read(values[index++]));
		return enumeration;
	}

	protected Integer getInstanciateOneDataTreeTypeStartIndex(String[] values) {
		return 15;
	}
	
}
