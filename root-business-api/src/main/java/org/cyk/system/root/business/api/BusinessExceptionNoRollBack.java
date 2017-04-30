package org.cyk.system.root.business.api;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=false)
public class BusinessExceptionNoRollBack extends AbstractBusinessException {

	private static final long serialVersionUID = 1310056770109974879L;

	public BusinessExceptionNoRollBack(String message) {
        super(message);
    }

}
