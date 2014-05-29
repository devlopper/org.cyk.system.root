package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.cdi.AbstractLayer;

public abstract class AbstractModelLayer extends AbstractLayer<Identifiable<?>> implements Serializable {
    
    /**
     * Initial data to persist at first module deployment time.
     * @return
     */
    public abstract Collection<Object> data();
    
}
