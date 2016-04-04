package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.pattern.tree.NestedSetNodeBusiness;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;

public class NestedSetBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 8691254326402622637L;
	
	private static NestedSet setA,setB,set3;
	private static NestedSetNode a,b;
	private static NestedSetNode a1,a2,a3,a4,b1,b2,b3,b4;
	private static NestedSetNode a11,a12,a13,a14,a21,a22,a23,a24,a31,a32,a33,a34,a41,a42,a43,a44;
	private static NestedSetNode a111,a112,a113,a114,a121,a122,a123,a124,a131,a132,a133,a134,a141,a142,a143,a144
		,a211,a212,a213,a214,a221,a222,a223,a224,a231,a232,a233,a234,a241,a242,a243,a244
		,a311,a312,a313,a314,a321,a322,a323,a324,a331,a332,a333,a334,a341,a342,a343,a344
		,a411,a412,a413,a414,a421,a422,a423,a424,a431,a432,a433,a434,a441,a442,a443,a444;
	
	@Inject private NestedSetNodeBusiness nestedSetNodeBusiness;
	
	private void createAll(){

			NestedSetNode parent =  null;
			
			setA = new NestedSet();
			nestedSetNodeBusiness.create(a = new NestedSetNode(setA,null));
			
			parent = a;
			a1 = createChild(parent);
			a2 = createChild(parent);
			a3 = createChild(parent);
			a4 = createChild(parent);
			
			
			parent = a1;
			a11 = createChild(parent);
			a12 = createChild(parent);
			a13 = createChild(parent);
			a14 = createChild(parent);
			
			parent = a2;
			a21 = createChild(parent);
			a22 = createChild(parent);
			a23 = createChild(parent);
			a24 = createChild(parent);
			
			parent = a3;
			a31 = createChild(parent);
			a32 = createChild(parent);
			a33 = createChild(parent);
			a34 = createChild(parent);
			
			parent = a4;
			a41 = createChild(parent);
			a42 = createChild(parent);
			a43 = createChild(parent);
			a44 = createChild(parent);
			/*
			parent = a11;
			a111 = createChild(parent);
			a112 = createChild(parent);
			a113 = createChild(parent);
			a114 = createChild(parent);
			
			parent = a12;
			a121 = createChild(parent);
			a122 = createChild(parent);
			a123 = createChild(parent);
			a124 = createChild(parent);
			
			parent = a13;
			a131 = createChild(parent);
			a132 = createChild(parent);
			a133 = createChild(parent);
			a134 = createChild(parent);
			
			parent = a14;
			a141 = createChild(parent);
			a142 = createChild(parent);
			a143 = createChild(parent);
			a144 = createChild(parent);
			
			parent = a21;
			a211 = createChild(parent);
			a212 = createChild(parent);
			a213 = createChild(parent);
			a214 = createChild(parent);
			
			parent = a22;
			a221 = createChild(parent);
			a222 = createChild(parent);
			a223 = createChild(parent);
			a224 = createChild(parent);
			
			parent = a23;
			a231 = createChild(parent);
			a232 = createChild(parent);
			a233 = createChild(parent);
			a234 = createChild(parent);
			
			parent = a24;
			a241 = createChild(parent);
			a242 = createChild(parent);
			a243 = createChild(parent);
			a244 = createChild(parent);
			
			parent = a31;
			a311 = createChild(parent);
			a312 = createChild(parent);
			a313 = createChild(parent);
			a314 = createChild(parent);
			
			parent = a32;
			a321 = createChild(parent);
			a322 = createChild(parent);
			a323 = createChild(parent);
			a324 = createChild(parent);
			
			parent = a33;
			a331 = createChild(parent);
			a332 = createChild(parent);
			a333 = createChild(parent);
			a334 = createChild(parent);
			
			parent = a34;
			a341 = createChild(parent);
			a342 = createChild(parent);
			a343 = createChild(parent);
			a344 = createChild(parent);
			
			parent = a41;
			a411 = createChild(parent);
			a412 = createChild(parent);
			a413 = createChild(parent);
			a414 = createChild(parent);
			
			parent = a42;
			a421 = createChild(parent);
			a422 = createChild(parent);
			a423 = createChild(parent);
			a424 = createChild(parent);
			
			parent = a43;
			a431 = createChild(parent);
			a432 = createChild(parent);
			a433 = createChild(parent);
			a434 = createChild(parent);
			
			parent = a44;
			a441 = createChild(parent);
			a442 = createChild(parent);
			a443 = createChild(parent);
			a444 = createChild(parent);
			*/
			
			System.out.println("B Set");
			setB = new NestedSet();
			nestedSetNodeBusiness.create(b = new NestedSetNode(setB,null));
			
			parent = b;
			b1 = createChild(parent);
			b2 = createChild(parent);
			b3 = createChild(parent);
			b4 = createChild(parent);
	}
	
	@Override
    protected void populate() {
	   
    }
		
    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
    	installApplication();
    	createAll();
    	
    	assertDetach(a1,5l,16l);
    	assertDetach(a2,5l,11l);
    	
    	assertAttach(a1, b1,5l,10l);
    	
    }
    
    private NestedSetNode createChild(NestedSetNode parent){
		return nestedSetNodeBusiness.create(new NestedSetNode(parent.getSet(), nestedSetNodeBusiness.find(parent.getIdentifier())));
	}
	
    private NestedSetNode delete(NestedSetNode node){
		return nestedSetNodeBusiness.delete(nestedSetNodeBusiness.find(node.getIdentifier()));
	}
    
    private void assertDetach(NestedSetNode node,Long expectedDetachedNodeCount,Long expectedSetNodeCount){
		node = nestedSetNodeBusiness.find(node.getIdentifier());
		nestedSetNodeBusiness.detach(node);
		node = nestedSetNodeBusiness.find(node.getIdentifier());
		Long detachedNodeCount = nestedSetNodeBusiness.countByDetachedIdentifier(node.getDetachedIdentifier());
		Long setNodeCount = nestedSetNodeBusiness.countWhereDetachedIdentifierIsNullBySet(node.getSet());
		assertEquals("Detached node count", expectedDetachedNodeCount, detachedNodeCount);
		assertEquals("Set node count", expectedSetNodeCount, setNodeCount);
	}
    
    private void assertAttach(NestedSetNode node,NestedSetNode parent,Long expectedAttachedNodeCount,Long expectedSetNodeCount){
    	node = nestedSetNodeBusiness.find(node.getIdentifier());
    	parent = nestedSetNodeBusiness.find(parent.getIdentifier());
    	nestedSetNodeBusiness.attach(node,parent);
    	Long attachedNodeCount = nestedSetNodeBusiness.countByParent(node)+1;
    	node = nestedSetNodeBusiness.find(node.getIdentifier());
    	Long setNodeCount = nestedSetNodeBusiness.countWhereDetachedIdentifierIsNullBySet(node.getSet());
    	assertEquals("Attached node count", expectedAttachedNodeCount, attachedNodeCount);
		assertEquals("Set node count", expectedSetNodeCount, setNodeCount);
	}
    
    private void showChildren(NestedSetNode node){
    	node =  nestedSetNodeBusiness.find(node.getIdentifier());
		System.out.println("Children of "+node+" = "+nestedSetNodeBusiness.findByParent(node));
	}

}
