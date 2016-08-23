package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.pattern.tree.NestedSetNodeBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetDao;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;

public class NestedSetNodeBusinessImpl extends AbstractTypedBusinessService<NestedSetNode, NestedSetNodeDao> implements NestedSetNodeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private NestedSetDao nestedSetDao;
	
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
	public NestedSetNode create(NestedSetNode node) {
		if(node.getSet().getIdentifier()==null){//set not yet created
			nestedSetDao.create(node.getSet());
			logTrace("Set {} auto created",node.getSet());
		}
		if(node.getSet().getRoot()==null){//first node of the set
			node.setParent(null);
			node.getSet().setRoot(node);
			node.setLeftIndex(NestedSetNode.FIRST_LEFT_INDEX);
			node.setRightIndex(NestedSetNode.FIRST_RIGHT_INDEX);	
		}else{
			NestedSet set = node.getParent().getSet();
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
			logTrace("On create : recomputing indexes of nodes. size = {} , elements = {}", nestedSetNodesWhereIndexesToBeRecomputed.size(),nestedSetNodesWhereIndexesToBeRecomputed);
			dao.executeIncrementLeftIndex(getWhereBoundariesGreaterThanOrEqualTo(nestedSetNodesWhereIndexesToBeRecomputed, Boolean.TRUE, parentRightIndex), 2l);
			dao.executeIncrementRightIndex(getWhereBoundariesGreaterThanOrEqualTo(nestedSetNodesWhereIndexesToBeRecomputed, Boolean.FALSE, parentRightIndex), 2l);
		}
		
		node.setDetachedIdentifier(null);
		if(node.getIdentifier()==null){
			logTrace("Node indexes {} computed",node);
			dao.create(node);
			if(node.getSet().getRoot()==null){//first node of the set
				logTrace("First set node {} created",node);
			}else{
				logTrace("Node {} created",node);
			}
		}else{
			dao.update(node);
			logTrace("Node {} updated",node);
		}
		
		return node;
	}

	@Override
	public NestedSetNode delete(NestedSetNode node) {
		List<NestedSetNode> tree = new ArrayList<>(dao.readByParent(node));
		Collections.reverse(tree);
		tree.add(node);
		logTrace(SUB_TREE_ACTION_LOG_FORMAT,"Deleting",node, tree.size(),tree);
		computeIndexesOnDelete(node.getRightIndex(), tree.size(), dao.readBySetByLeftOrRightGreaterThanOrEqualTo(node.getSet(), node.getRightIndex()+1));
		if(node.getParent()==null){
			node.getSet().setRoot(null);
			nestedSetDao.update(node.getSet());	
		}
		
		//delete tree
		for(NestedSetNode n : tree){
			n.computeLogMessage();
			n.setParent(null);
			dao.delete(n);
			logTrace("{} deleted", n.getLastComputedLogMessage());
		}
		
		//dao.delete(tree);
		
		return node;
	}
	
	private void computeIndexesOnDelete(Integer subTreeRootNodeRightIndex,Integer subTreeNodesCount,Collection<NestedSetNode> nestedSetNodesWhereIndexesToBeRecomputed) {
		logTrace("On delete : recomputing indexes of nodes. size = {} , elements = {}", nestedSetNodesWhereIndexesToBeRecomputed.size(),nestedSetNodesWhereIndexesToBeRecomputed);
		
		int step = subTreeNodesCount*2;
		for(NestedSetNode n : nestedSetNodesWhereIndexesToBeRecomputed){
			updateBoundaries(n,-step, n.getLeftIndex()>subTreeRootNodeRightIndex?null:false);//both bounds or right only
			//dao.update(n); //TODO I think it is not necessary! to be confirmed
			logTrace("Node indexes {} recomputed",n);
		}
		
		//dao.incrementLeftIndex(getWhereBoundariesGreaterThanOrEqualTo(nestedSetNodesWhereIndexesToBeRecomputed, Boolean.TRUE, subTreeRootNodeRightIndex), -step*1l);
		//dao.executeIncrementRightIndex(getWhereBoundariesGreaterThanOrEqualTo(nestedSetNodesWhereIndexesToBeRecomputed, Boolean.FALSE, subTreeRootNodeRightIndex), -step*1l);
	}
	
	@Override
	public NestedSetNode detach(NestedSetNode node) {
		exceptionUtils().exception(node.getLeftIndex()<=0 && node.getRightIndex()<=0, "exception.nestedsetnode.alreadydetached");
		List<NestedSetNode> tree = new ArrayList<>(dao.readByParent(node));
		tree.add(0, node);
		logTrace(SUB_TREE_ACTION_LOG_FORMAT,"Detaching", node,tree.size(),tree);
		computeIndexesOnDelete(node.getRightIndex(), tree.size(), dao.readBySetByLeftOrRightGreaterThanOrEqualTo(node.getSet(), node.getRightIndex()+1));
		String detachedIdentifier = System.currentTimeMillis()+RandomStringUtils.randomAlphanumeric(12);
		for(NestedSetNode n : tree){
			n.computeLogMessage();
			//n.setParent(null); this link should not be lost : it might help finding the subtree
			Integer leftIndex = n.getLeftIndex(),rightIndex = n.getRightIndex();
			n.setLeftIndex(-1 * rightIndex);
			n.setRightIndex(-1 * leftIndex);
			n.setDetachedIdentifier(detachedIdentifier);
			dao.update(n);
			logTrace("{} detached -> {}", n.getLastComputedLogMessage(),n.getLogMessage());
		}
		return node;
	}

	@Override
	public NestedSetNode attach(NestedSetNode node, NestedSetNode parent) {
		exceptionUtils().exception(node.getLeftIndex()>=0 && node.getRightIndex()>=0, "exception.nestedsetnode.alreadyattached");
		List<NestedSetNode> treeNodes = new ArrayList<>(dao.readByDetachedIdentifier(node.getDetachedIdentifier()));
		logTrace(SUB_TREE_ACTION_LOG_FORMAT,"Attaching", node,treeNodes.size(),treeNodes);
		treeNodes.get(0).setParent(parent);//Attaching to the new parent
		for(NestedSetNode treeNode : treeNodes){
			treeNode.setSet(parent.getSet());//attaching to the parent's set
			create(treeNode);
			logTrace("{} attached", treeNode);
			//System.out.println("Children of "+node+" : "+dao.readByParent(node));
			//System.out.println("Children of "+node+" : "+dao.readAll());
		}
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
	
	private static final String SUB_TREE_ACTION_LOG_FORMAT = "{} sub tree ({}). size = {}. Elements = {}";
		
}
