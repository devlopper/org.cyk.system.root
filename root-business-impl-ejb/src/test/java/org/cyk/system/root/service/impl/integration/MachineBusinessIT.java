package org.cyk.system.root.service.impl.integration;

public class MachineBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    private String machine1Code = "(a|b)*abb"/*,machine2Code="clone_machine"*/;
    
    @Override
    protected void populate() {
    	super.populate();
    	rootBusinessTestHelper.createFiniteStateMachine(machine1Code, new String[]{"a","b"}, new String[]{"s0","s1","s2","s3"}, "s0", new String[]{"s3"}, new String[][]{
    			{"s0","a","s1"},{"s0","b","s0"}
    			,{"s1","a","s1"},{"s1","b","s2"}
    			,{"s2","a","s1"},{"s2","b","s3"}
    			,{"s3","a","s1"},{"s3","b","s0"}
    	});
    }
    
	@Override
	protected void businesses() {
		rootBusinessTestHelper.readFiniteStateMachine(machine1Code, new String[]{"a","b","b"}, "s3");
		rootBusinessTestHelper.readFiniteStateMachine(machine1Code, new String[]{"a","a","a","a","a","b","b"}, "s3");
		rootBusinessTestHelper.readFiniteStateMachine(machine1Code, new String[]{"a","b","a","a","b","a","b","b"}, "s3");
		
		rootBusinessTestHelper.findByFromStateByAlphabet(machine1Code, "s0", "a", "s1");
		
		/*
		RootBusinessLayer.getInstance().getFiniteStateMachineBusiness()
			.clone(RootBusinessLayer.getInstance().getFiniteStateMachineBusiness().find(machine1Code), machine2Code);
		
		debug(RootBusinessLayer.getInstance().getFiniteStateMachineBusiness().find(machine2Code).getInitialState());
		*/
	}
   
    
    
}
