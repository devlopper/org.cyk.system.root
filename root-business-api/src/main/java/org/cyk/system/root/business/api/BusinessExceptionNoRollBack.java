package org.cyk.system.root.business.api;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=false)
public class BusinessExceptionNoRollBack extends AbstractBusinessException {

    public BusinessExceptionNoRollBack(String message) {
        super(message);
    }

}
