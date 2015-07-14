package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Getter
public abstract class AbstractBusinessException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 108726134018949961L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBusinessException.class);
	
	@Setter protected String identifier;
	protected Set<String> messages = new LinkedHashSet<>();
    
    public AbstractBusinessException(String message) {
        super(message);
        messages.add(message);
        LOGGER.info(message);
    }
    
    public AbstractBusinessException(Set<String> messages) {
        super(StringUtils.join(messages,"\r\n"));
        this.messages.addAll(messages);
        LOGGER.info(StringUtils.join(messages,"\r\n"));
    }

}
