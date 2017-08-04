package org.cyk.system.root.business.api;

import java.util.Set;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class BusinessException extends AbstractBusinessException {

	private static final long serialVersionUID = -2839733742847798770L;

	public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Set<String> messages) {
        super(messages);
    }

}
