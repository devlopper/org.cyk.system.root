package org.cyk.system.root.model.unit;

import org.cyk.system.root.model.EnumHelper;

public enum MyTestEnumHelper {

    V1,V2
    
    ;
    
    @Override
    public String toString() {
        return EnumHelper.getInstance().text(this);
    }
}
