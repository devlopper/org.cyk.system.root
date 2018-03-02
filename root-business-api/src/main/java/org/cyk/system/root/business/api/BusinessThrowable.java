package org.cyk.system.root.business.api;

import java.util.Set;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class BusinessThrowable extends AbstractBusinessThrowable {
	private static final long serialVersionUID = -2839733742847798770L;

	public BusinessThrowable(String message) {
        super(message);
    }

    public BusinessThrowable(Set<String> messages) {
        super(messages);
    }

}
