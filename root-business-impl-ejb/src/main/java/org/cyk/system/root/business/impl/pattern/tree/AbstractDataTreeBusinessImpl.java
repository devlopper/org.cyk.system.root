package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeBusiness;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeDao;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

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
	
	protected Integer getInstanciateOneDataTreeTypeStartIndex(String[] values) {
		return 15;
	}
	
	/**/
	
	public static class BuilderOneDimensionArray<T extends AbstractDataTree<?>> extends AbstractDataTreeNodeBusinessImpl.BuilderOneDimensionArray<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public BuilderOneDimensionArray(Class<T> outputClass) {
			super(outputClass);
			addParameterArrayElementStringIndexInstance(15,AbstractDataTree.FIELD_TYPE);
		}		        		
	}		

	public static class Details<NODE extends AbstractDataTree<TYPE>,TYPE extends AbstractDataTreeType> extends AbstractDataTreeNodeBusinessImpl.Details<NODE> implements Serializable {

		private static final long serialVersionUID = 7515356383413863619L;

		@Input @InputText protected String type;
		
		public Details(NODE node) {
			super(node);
			setMaster(node);
			
		}
		
		@Override
		public void setMaster(NODE master) {
			super.setMaster(master);
			if(master!=null){
				type = formatUsingBusiness(master.getType());
			}
		}
		
		/**/
		
		public static final String FIELD_TYPE = "type";
		
		/**/
		
		
	}
}
