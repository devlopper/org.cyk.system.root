package org.cyk.system.root.persistence.impl;

import java.io.Serializable;

public class PredicateBuilder implements Serializable {
    
	private static final long serialVersionUID = -5010360136012992645L;

	public PredicateBuilder or(){
        
        return this;
    }
    
    public PredicateBuilder and(){
        
        return this;
    }

}
