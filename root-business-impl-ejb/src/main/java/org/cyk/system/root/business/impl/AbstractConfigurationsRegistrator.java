package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractConfigurationsRegistrator extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract void register();
	
}
