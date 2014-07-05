package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;

@Getter
public abstract class AbstractBusinessException extends RuntimeException implements Serializable {

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
