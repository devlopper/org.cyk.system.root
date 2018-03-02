package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessThrowable;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.pattern.tree.NestedSetBusiness;
import org.cyk.system.root.business.api.pattern.tree.NestedSetNodeBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetDao;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.RandomHelper;

@Stateless
public class NestedSetNodeBusinessImpl extends AbstractTypedBusinessService<NestedSetNode, NestedSetNodeDao> implements NestedSetNodeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public NestedSetNodeBusinessImpl(NestedSetNodeDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<NestedSetNode> findByParent(NestedSetNode parent) {
		return dao.readByParent(parent);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByParent(NestedSetNode parent) {
		return dao.countByParent(parent);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<NestedSetNode> findBySet(NestedSet set) {
		return dao.readBySet(set);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countBySet(NestedSet set) {
		return dao.countBySet(set);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<NestedSetNode> findWhereDetachedIdentifierIsNullBySet(NestedSet set) {
		return dao.readWhereDetachedIdentifierIsNullBySet(set);
	}
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countWhereDetachedIdentifierIsNullBySet(NestedSet set) {
		return dao.countWhereDetachedIdentifierIsNullBySet(set);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<NestedSetNode> findByDetachedIdentifier(String identifier) {
		return dao.readByDetachedIdentifier(identifier);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByDetachedIdentifier(String identifier) {
		return dao.countByDetachedIdentifier(identifier);
	}

	@Override
	protected void beforeCreate(NestedSetNode node) {
		super.beforeCreate(node);
		LoggingHelper.Message.Builder loggingMessageBuilder = node.getLoggingMessageBuilder(Boolean.TRUE);
		
		if(node.getParent()!=null)
			loggingMessageBuilder.addNamedParameters("parent",node.getParent().getCode());
		
		NestedSet set = node.getSet();
		if(set == null && node.getParent()!=null)
			set = node.getParent().getSet();
		createIfNotIdentified(set);
		
		loggingMessageBuilder.addNamedParameters("set",set.getCode());
		
		if(set.getRoot()==null){//first node of the set
			node.setParent(null);
			set.setRoot(node);
			node.setLeftIndex(NestedSetNode.FIRST_LEFT_INDEX);
			node.setRightIndex(NestedSetNode.FIRST_RIGHT_INDEX);
		}else{
			node.setSet(set);
			NestedSetNode parent = node.getParent();
			
			Integer parentRightIndex = parent.getRightIndex();
			node.setLeftIndex(parentRightIndex);
			node.setRightIndex(node.getLeftIndex()+1);
			Collection<NestedSetNode> nestedSetNodesWhereIndexesToBeRecomputedCandidate = dao.readBySetByLeftOrRightGreaterThanOrEqualTo(set, parentRightIndex);
			Collection<NestedSetNode> nestedSetNodesWhereIndexesToBeRecomputed = new ArrayList<>();
			
			nestedSetNodesWhereIndexesToBeRecomputed.add(parent);
			for(NestedSetNode index : nestedSetNodesWhereIndexesToBeRecomputedCandidate){
				// Because node parent can be changed by attach so to be ignore
				// only one instance of parent must be handled to avoid inconsistent update
				if(index.equals(node) || index.equals(parent)){
					
				}else
					nestedSetNodesWhereIndexesToBeRecomputed.add(index);
			}
			//logTrace("On create : recomputing indexes of nodes. size = {} , elements = {}", nestedSetNodesWhereIndexesToBeRecomputed.size(),nestedSetNodesWhereIndexesToBeRecomputed);
			loggingMessageBuilder.addNamedParameters("#node_indexes_to_recompute",nestedSetNodesWhereIndexesToBeRecomputed.size());
			dao.executeIncrementLeftIndex(getWhereBoundariesGreaterThanOrEqualTo(nestedSetNodesWhereIndexesToBeRecomputed, Boolean.TRUE, parentRightIndex), 2l);
			dao.executeIncrementRightIndex(getWhereBoundariesGreaterThanOrEqualTo(nestedSetNodesWhereIndexesToBeRecomputed, Boolean.FALSE, parentRightIndex), 2l);
		}
		loggingMessageBuilder.addNamedParameters("left",node.getLeftIndex(),"right",node.getRightIndex());
		node.setDetachedIdentifier(null);
	}
	
	@Override
	protected void __create__(NestedSetNode node) {
		if(node.getIdentifier()==null){
			dao.create(node);
			node.getSet().setNumberOfChildren(NumberHelper.getInstance().add(node.getSet().getNumberOfChildren(), 1l).intValue());
		}else{
			dao.update(node);
		}
		
		inject(NestedSetBusiness.class).update(node.getSet());
	}
	
	@Override
	protected void afterCreate(NestedSetNode node) {
		super.afterCreate(node);
		throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("numberOfChildrenSet")
				.setDomainNameIdentifier("nestedset").setNumber1(node.getSet().getNumberOfChildren()).setNumber2(inject(NestedSetNodeDao.class).countBySet(node.getSet()))
				.setEqual(Boolean.FALSE));
		
		if(node.getParent() != null) {
			throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("numberOfChildrenNode")
					.setDomainNameIdentifier("nestedsetnode").setNumber1(node.getParent().getNumberOfChildren()).setNumber2(inject(NestedSetNodeDao.class)
							.countByParent(node.getParent())));
			
			throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("numberOfDirectChildrenNode")
					.setDomainNameIdentifier("nestedsetnode").setNumber1(node.getParent().getNumberOfDirectChildren()).setNumber2(inject(NestedSetNodeDao.class)
							.countDirectChildrenByParent(node.getParent())));
		}						
	}
	
	@Override
	protected void beforeDelete(NestedSetNode node) {
		super.beforeDelete(node);
		LoggingHelper.Message.Builder loggingMessageBuilder = node.getLoggingMessageBuilder(Boolean.TRUE);
		node.setChildren(new ArrayList<AbstractIdentifiable>());
		List<NestedSetNode> tree = new ArrayList<>(dao.readByParent(node));
		node.getChildren().addAll(tree);
		Collections.reverse(tree);
		tree.add(node);
		Collection<NestedSetNode> nodes = dao.readBySetByLeftOrRightGreaterThanOrEqualTo(node.getSet(), node.getRightIndex()+1);
		loggingMessageBuilder.addNamedParameters("#node_indexes_to_recompute",nodes.size());
		computeIndexesOnDelete(node.getRightIndex(), tree.size(), nodes);
		if(node.getParent()==null){
			node.getSet().setRoot(null);
			inject(NestedSetDao.class).update(node.getSet());	
		}
		
		//delete tree
		for(NestedSetNode n : tree){
			if(n.getCode().equals(node.getCode()))
				continue;
			n.computeLogMessage();
			n.setParent(null);
			dao.delete(n);
			inject(GlobalIdentifierBusiness.class).delete(n.getGlobalIdentifier());
		}
	}

	@Override
	protected void afterDelete(NestedSetNode node) {
		super.afterDelete(node);
		node.getSet().setNumberOfChildren(NumberHelper.getInstance().add(node.getSet().getNumberOfChildren(), -1l).intValue());
		
		inject(NestedSetBusiness.class).update(node.getSet());
		
		throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("numberOfChildrenSet")
				.setDomainNameIdentifier("nestedset").setNumber1(node.getSet().getNumberOfChildren()).setNumber2(inject(NestedSetNodeDao.class).countBySet(node.getSet()))
				.setEqual(Boolean.FALSE), BusinessThrowable.class);
		//if(dao.countBySet(node.getSet()) == 0)
		//	inject(NestedSetBusiness.class).delete(node.getSet());
	}
		
	private void computeIndexesOnDelete(Integer subTreeRootNodeRightIndex,Integer subTreeNodesCount,Collection<NestedSetNode> nestedSetNodesWhereIndexesToBeRecomputed) {
		int step = subTreeNodesCount*2;
		for(NestedSetNode n : nestedSetNodesWhereIndexesToBeRecomputed){
			updateBoundaries(n,-step, n.getLeftIndex()>subTreeRootNodeRightIndex?null:false);//both bounds or right only
		}		
	}
	
	@Override
	public NestedSetNode detach(NestedSetNode node) {
		//LogMessage.Builder logMessageBuilder = new LogMessage.Builder("DETACH", node);
		exceptionUtils().exception(node.getLeftIndex()<=0 && node.getRightIndex()<=0, "exception.nestedsetnode.alreadydetached");
		List<NestedSetNode> tree = new ArrayList<>(dao.readByParent(node));
		tree.add(0, node);
		//logMessageBuilder.addParameters("tree",tree,"size",tree.size());
		//logTrace(SUB_TREE_ACTION_LOG_FORMAT,"Detaching", node,tree.size(),tree);
		computeIndexesOnDelete(node.getRightIndex(), tree.size(), dao.readBySetByLeftOrRightGreaterThanOrEqualTo(node.getSet(), node.getRightIndex()+1));
		String detachedIdentifier = System.currentTimeMillis()+RandomHelper.getInstance().getAlphanumeric(12);
		for(NestedSetNode n : tree){
			n.computeLogMessage();
			//n.setParent(null); this link should not be lost : it might help finding the subtree
			Integer leftIndex = n.getLeftIndex(),rightIndex = n.getRightIndex();
			n.setLeftIndex(-1 * rightIndex);
			n.setRightIndex(-1 * leftIndex);
			n.setDetachedIdentifier(detachedIdentifier);
			dao.update(n);
			//logTrace("{} detached -> {}", n.getLastComputedLogMessage(),n.getLogMessage());
			//logMessageBuilder.addParameters("Detach",n);
		}
		//logTrace(logMessageBuilder);
		return node;
	}

	@Override
	public NestedSetNode attach(NestedSetNode node, NestedSetNode parent) {
		//LogMessage.Builder logMessageBuilder = new LogMessage.Builder("ATTACH", node);
		exceptionUtils().exception(node.getLeftIndex()>=0 && node.getRightIndex()>=0, "exception.nestedsetnode.alreadyattached");
		List<NestedSetNode> treeNodes = new ArrayList<>(dao.readByDetachedIdentifier(node.getDetachedIdentifier()));
		//logMessageBuilder.addParameters("nodes",treeNodes);
		//logTrace(SUB_TREE_ACTION_LOG_FORMAT,"Attaching", node,treeNodes.size(),treeNodes);
		treeNodes.get(0).setParent(parent);//Attaching to the new parent
		for(NestedSetNode treeNode : treeNodes){
			treeNode.setSet(parent.getSet());//attaching to the parent's set
			create(treeNode);
			//logTrace("{} attached", treeNode);
			//System.out.println("Children of "+node+" : "+dao.readByParent(node));
			//System.out.println("Children of "+node+" : "+dao.readAll());
		}
		//logTrace(logMessageBuilder);
		return node;
	}
	
	/**/
	
	/*private void updateBoundariesGreaterThanOrEqualTo(NestedSetNode node,Boolean increase,Integer index){
		int sign = increase?+1:-1;
		if(node.getLeftIndex()>=index)
			updateBoundaries(node, sign*2, Boolean.TRUE);
		if(node.getRightIndex()>=index)
			updateBoundaries(node, sign*2, Boolean.FALSE);
	}*/
	private Collection<NestedSetNode> getWhereBoundariesGreaterThanOrEqualTo(Collection<NestedSetNode> nestedSetNodes,Boolean left,Integer index){
		Collection<NestedSetNode> result = new ArrayList<>();
		for(NestedSetNode nestedSetNode : nestedSetNodes)
			if( (Boolean.TRUE.equals(left) && nestedSetNode.getLeftIndex()>=index) || (Boolean.FALSE.equals(left) && nestedSetNode.getRightIndex()>=index) )
				result.add(nestedSetNode);
		return result;
	}
	
	private void updateBoundaries(NestedSetNode node,Integer step,Boolean left){
		if(left==null || left)
			commonUtils.increment(Integer.class, node, NestedSetNode.FIELD_LEFT_INDEX, step);
		if(left==null || !left)
			commonUtils.increment(Integer.class, node, NestedSetNode.FIELD_RIGHT_INDEX, step);
	}
	
	/**/
		
}
