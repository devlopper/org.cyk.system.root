package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

@Getter
public abstract class AbstractBusinessException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 108726134018949961L;
	
	@Setter protected String identifier;
	protected Set<String> messages = new LinkedHashSet<>();
    
    public AbstractBusinessException(String message) {
        super(message);
        messages.add(message);
    }
    
    public AbstractBusinessException(Set<String> messages) {
        super(StringUtils.join(messages,"\r\n"));
        this.messages.addAll(messages);
    }

}
