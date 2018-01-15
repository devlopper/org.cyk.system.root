package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.api.pattern.tree.NestedSetNodeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeNodeDao;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.StringHelper;

public abstract class AbstractDataTreeNodeBusinessImpl<NODE extends AbstractDataTreeNode,DAO extends AbstractDataTreeNodeDao<NODE>>  
    extends AbstractEnumerationBusinessImpl<NODE, DAO> implements AbstractDataTreeNodeBusiness<NODE> {

	private static final long serialVersionUID = 8279530282390587764L;

	@Inject private NestedSetNodeBusiness nestedSetNodeBusiness;
	
	public AbstractDataTreeNodeBusinessImpl(DAO dao) {
        super(dao);
    }
	
	@Override
	protected void beforeCreate(NODE enumeration) {
		if(enumeration.getNode()==null){
			if(enumeration.getNewParent()==null){
				enumeration.setNode(new NestedSetNode(new NestedSet(), null));
				enumeration.getNode().getSet().setName(enumeration.getName());
			}else{
				enumeration.setParentNode(enumeration.getNewParent());
				//enumeration.setNode(new NestedSetNode(new NestedSet(), enumeration.getNewParent().getNode()));
			}
			//enumeration.setNode(new NestedSetNode(new NestedSet(), enumeration.getNewParent()==null ? null : enumeration.getNewParent().getNode()));
		}
		
		super.beforeCreate(enumeration);
		if(enumeration.getNode().getIdentifier()==null){
			if(StringUtils.isBlank(enumeration.getNode().getCode()))
				enumeration.getNode().setCode(enumeration.getCode());
			if(StringUtils.isBlank(enumeration.getNode().getName()))
				enumeration.getNode().setName(enumeration.getName());
			inject(NestedSetNodeBusiness.class).create(enumeration.getNode());
		}
	}
	
	@Override
	protected void beforeUpdate(NODE node){
		super.beforeUpdate(node);
		exceptionUtils().exception(node.getIdentifier()!=null && node.getNode().getIdentifier()==null, "thisisamove");
		/*NODE parent = dao.readParent(node);
		NODE databaseRecord = dao.read(node.getIdentifier());
		NODE databaseRecordParent = dao.readParent(databaseRecord);
		System.out.println("AbstractDataTreeNodeBusinessImpl.beforeUpdate() : "+node.getIdentifier()+":"+node.getNode().getIdentifier());
		*/
		//exceptionUtils().exception(databaseRecordParent!=null && !databaseRecordParent.equals(parent), "thisisamove");
		
	}
	
	@Override
	protected void afterUpdate(NODE node){
		super.afterUpdate(node);
		if(Boolean.TRUE.equals(node.getAutomaticallyMoveToNewParent())){
			NODE parent = dao.readParent(node);
			if(parent==null && node.getNewParent()!=null || node.getNewParent()==null && parent!=null || !parent.equals(node.getNewParent()))
				move(node.getCode(), node.getNewParent().getCode());
		}
	}
	
	@Override
	public NODE delete(NODE enumeration) {
		logTrace("Deleting {} on node {}. Children are the followings :", enumeration,enumeration.getNode());
		NestedSetNode rootNode = enumeration.getNode();
		Collection<NODE> list = dao.readByParent(enumeration);
		for(NODE element : list)
			logTrace("\tChild {} on node {}", element,element.getNode());
		list.add(enumeration);
		for(NODE e : list){
			e.setNode(null);
			dao.delete(e);
			GlobalIdentifier gid = e.getGlobalIdentifier();
			e.setGlobalIdentifier(null);
			inject(GlobalIdentifierBusiness.class).delete(gid);
			logTrace("\t\tElement {} deleted", e);
		}
		nestedSetNodeBusiness.delete(rootNode);
		return enumeration;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<AbstractIdentifiable> findParentRecursively(NODE node) {
		Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
		for(NODE n : dao.readParentRecursively(node))
			identifiables.add(n);
		return identifiables;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void setParents(NODE node) {
		for(AbstractIdentifiable identifiable : findParentRecursively(node))
			node.getParents().add(identifiable);
	} 

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void setParents(Collection<NODE> nodes) {
		for(NODE node : nodes)
			setParents(node);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public NODE findParent(String code){
		return findParent(dao.read(code));
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public NODE findParent(NODE child){
		return dao.readParent(child);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<NODE> findByParent(NODE parent){
		return dao.readByParent(parent);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<NODE> findDirectChildrenByParent(NODE parent){
		return dao.readDirectChildrenByParent(parent);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countDirectChildrenByParent(NODE parent){
		return dao.countDirectChildrenByParent(parent);
	}
	
	@Override
	public Collection<NODE> findByString(StringSearchCriteria stringSearchCriteria) {
		Collection<NODE> nodes = super.findByString(stringSearchCriteria);
		setParents(nodes);
		return nodes;
	}

	@Override
	public void move(NODE enumeration, NODE parent) {
		NODE databaseParent = dao.readParent(enumeration);
		if(parent.equals(databaseParent))
			return;
		exceptionUtils().exception(enumeration.equals(parent),"exception.nestedsetnode.move.nodecannotbeitsownparent",new Object[]{parent});
		exceptionUtils().exception(isAncestorOf(enumeration, parent),"exception.nestedsetnode.move.nodecannotbechildofitschild",new Object[]{enumeration,parent});
		Long numberOfChildrenByCountBefore = dao.countByParent(enumeration);
		Long numberOfChildrenOfParentByCountBefore = dao.countByParent(parent);
		NestedSetNode node = enumeration.getNode();
		node = nestedSetNodeBusiness.detach(node);
		nestedSetNodeBusiness.attach(node,parent.getNode());
		
		Long numberOfChildrenByCountAfter = dao.countByParent(dao.read(enumeration.getIdentifier()));
		Long numberOfChildrenOfParentByCountAfter = dao.countByParent(dao.read(parent.getIdentifier()));
		
		exceptionUtils().exception(numberOfChildrenByCountBefore!=numberOfChildrenByCountAfter, "exception.nestedsetnode.move.numberofchildren"
				,new Object[]{enumeration,numberOfChildrenByCountBefore,numberOfChildrenByCountAfter});
		Long expectedNumberOfChildrenOfParentByCount = numberOfChildrenOfParentByCountBefore+numberOfChildrenByCountBefore+1;
		exceptionUtils().exception(expectedNumberOfChildrenOfParentByCount!=numberOfChildrenOfParentByCountAfter, "exception.nestedsetnode.move.numberofchildren"
				,new Object[]{parent,expectedNumberOfChildrenOfParentByCount,numberOfChildrenOfParentByCountAfter});
	}
	
	@Override
	public void move(String code, String parentCode) {
		move(dao.read(code), dao.read(parentCode));
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean isAncestorOf(NODE ancestor,NODE child){
		return ancestor.getNode().getLeftIndex() < child.getNode().getLeftIndex() && ancestor.getNode().getRightIndex() > child.getNode().getRightIndex();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean isAtLeastOneAncestorOf(Collection<NODE> ancestors,NODE child){
		if(ancestors==null)
			return Boolean.FALSE;
		for(NODE node : ancestors)
			if(isAncestorOf(node, child))
				return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void findHierarchy(NODE anEnumeration) {
        loadChildren(anEnumeration);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Collection<NODE> findHierarchies() {
        Collection<NODE> hierarchy = dao.readRoots();
        for(NODE type : hierarchy)
            loadChildren(type);
        return hierarchy;
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public NODE instanciateOne(String parentCode, String code,String name) {
		NODE enumeration = instanciateOne(code,name);
    	enumeration.setParentNode(dao.readByGlobalIdentifierCode(parentCode));
    	return enumeration;
	}
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public NODE instanciateOne(NODE parent) {
		NODE enumeration = instanciateOne();
    	enumeration.setParentNode(parent == null ? null : dao.read(parent.getIdentifier()));
    	return enumeration;
	}

	private void loadChildren(NODE parent){
		parent.setChildren(null);
		buildHierarchy(parent,new ArrayList<>(dao.readByParent(parent)));
    }
    
    @SuppressWarnings("unchecked")
    private void buildHierarchy(NODE parent,List<NODE> children){
    	for(int i=0;i<children.size();){
            
            if(children.get(i).getNode().getParent().equals(parent.getNode())){
                if(parent.getChildren()==null)
                    parent.setChildren(new ArrayList<AbstractIdentifiable>());
                parent.getChildren().add(children.remove(i));
            }else
                i++;
        }if(parent.getChildren()!=null)
            for(AbstractIdentifiable child : parent.getChildren())
                buildHierarchy((NODE) child, children);
    }
    
	@Override
	protected NODE __instanciateOne__(String[] values, InstanciateOneListener<NODE> listener) {
		NODE node = super.__instanciateOne__(values, listener);
		Integer index = 10;
		String value;
		if(index < values.length && StringUtils.isNotBlank(value = values[index++])){
			node.setParentNode(dao.read(value));
			//System.out.println("AbstractDataTreeNodeBusinessImpl.__instanciateOne__() Parent : "+value+" : "+dao.read(value));
		}
		return node;
	}
	
	protected Integer getInstanciateOneDataTreeNodeStartIndex(String[] values) {
		return 10;
	}

	
	@Override
	public Collection<NODE> findRoots(DataReadConfiguration readConfiguration) {
		setDaoDataReadConfiguration(readConfiguration);
		return dao.readRoots();
	}

	
	@Override
	public Collection<NODE> findRoots() {
		return findRoots(null);
	}
	

	@Override
	public Long countRoots() {
		return dao.countRoots();
	}
	

	@Override
	public Collection<NODE> findByParent(NODE parent, DataReadConfiguration readConfiguration) {
		setDaoDataReadConfiguration(readConfiguration);
		return dao.readByParent(parent);
	}
	

	@Override
	public Collection<NODE> findDirectChildrenByParent(NODE parent, DataReadConfiguration readConfiguration) {
		setDaoDataReadConfiguration(readConfiguration);
		return dao.readDirectChildrenByParent(parent);
	}
	
	/**/
	
	public static class BuilderOneDimensionArray<T extends AbstractDataTreeNode> extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<T> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray(Class<T> outputClass) {
			super(outputClass);
		}
		
		@Override
		protected T __execute__() {
			T t = super.__execute__();
			if(getInput().length>10 && !StringHelper.getInstance().isBlank( (java.lang.String)getInput()[10] )){
				//t.setParentNode(org.cyk.utility.common.helper.InstanceHelper.Pool.getInstance().get(getOutputClass(), getInput()[10]));
				t.setNewParent(org.cyk.utility.common.helper.InstanceHelper.Pool.getInstance().get(getOutputClass(), getInput()[10]));
				//System.out.println(getInput()[0]+" : parent = "+t.getNewParent().getCode());
			}
			return t;
		}
		
		
	}

	public static class Details<NODE extends AbstractDataTreeNode> extends AbstractEnumerationBusinessImpl.Details<NODE> implements Serializable {

		private static final long serialVersionUID = 7515356383413863619L;

		@Input @InputText protected String parent;
		
		public Details(NODE node) {
			super(node);
		}
		
		@Override
		public void setMaster(NODE master) {
			super.setMaster(master);
			if(master!=null){
				@SuppressWarnings("unchecked")
				NODE parent = (NODE) inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractDataTreeNode>)master.getClass()).findParent(master);
				this.parent = parent == null ? null : formatUsingBusiness(parent);
			}
		}
		
		/**/
		
		public static final String FIELD_PARENT = "parent";
		
		/**/
		
	}

}
