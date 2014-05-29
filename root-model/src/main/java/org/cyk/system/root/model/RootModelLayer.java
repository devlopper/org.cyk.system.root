package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.annotation.Layer;
import org.cyk.utility.common.annotation.Layer.Type;

@Layer(type=Type.MODEL)
public class RootModelLayer extends AbstractModelLayer implements Serializable {

    @Override
    public Collection<Object> data() {
        System.out.println("RootModelLayer.data()");
        return null;
    }

   
    
}
