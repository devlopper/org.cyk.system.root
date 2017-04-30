package org.cyk.system.root.business.impl;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.system.root.business.api.GenericBusiness;

@Getter
public class RemoteResourceProducer implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8479552983912061281L;
	//@EJB @Produces @Business
    private GenericBusiness genericBusiness;
    
}
