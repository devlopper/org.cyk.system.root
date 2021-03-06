package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.Set;

import org.cyk.utility.common.helper.ThrowableHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractBusinessThrowable extends ThrowableHelper.ThrowableMarkerRunTime implements Serializable {
	private static final long serialVersionUID = 108726134018949961L;

	public AbstractBusinessThrowable(Set<String> messages) {
		super(messages);
	}

	public AbstractBusinessThrowable(String message) {
		super(message);
	}
	
}
