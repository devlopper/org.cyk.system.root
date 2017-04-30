package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class DefaultValidator extends AbstractValidator<Object> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2481347537667487474L;
	private static DefaultValidator INSTANCE;
    
    @Override
    protected void initialisation() {
        INSTANCE = this;
        super.initialisation();
    }
    
    public static DefaultValidator getInstance() {
        return INSTANCE;
    }
    
}
