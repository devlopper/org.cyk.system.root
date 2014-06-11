package org.cyk.system.root.persistence.impl;

import java.io.Serializable;

public class PredicateBuilder implements Serializable {
    
    public PredicateBuilder or(){
        
        return this;
    }
    
    public PredicateBuilder and(){
        
        return this;
    }

}
