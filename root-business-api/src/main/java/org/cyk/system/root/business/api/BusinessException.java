package org.cyk.system.root.business.api;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class BusinessException extends AbstractBusinessException {

    public BusinessException(String message) {
        super(message);
    }

}
