package org.cyk.system.root.business.api;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=false)
public class BusinessThrowableNoRollBack extends AbstractBusinessThrowable {
	private static final long serialVersionUID = 1310056770109974879L;

	public BusinessThrowableNoRollBack(String message) {
        super(message);
    }

}
