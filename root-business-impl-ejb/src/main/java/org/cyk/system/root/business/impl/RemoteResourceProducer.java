package org.cyk.system.root.business.impl;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.system.root.business.api.GenericBusiness;

@Getter
public class RemoteResourceProducer implements Serializable {

    //@EJB @Produces @Business
    private GenericBusiness genericBusiness;
    
}
