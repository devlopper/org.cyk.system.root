package org.cyk.system.root.business.api;

import java.io.Serializable;

public abstract class AbstractBusinessException extends RuntimeException implements Serializable {

    public AbstractBusinessException(String message) {
        super(message);
    }

}
