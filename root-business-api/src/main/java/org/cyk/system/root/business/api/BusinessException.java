package org.cyk.system.root.business.api;

import java.util.Set;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class BusinessException extends AbstractBusinessException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Set<String> messages) {
        super(messages);
    }

}
