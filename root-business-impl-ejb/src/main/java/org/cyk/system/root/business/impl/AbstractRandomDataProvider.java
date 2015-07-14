package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.generator.RandomDataProvider;

public abstract class AbstractRandomDataProvider extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -660371123174061326L;

	protected RandomDataProvider randomDataProvider = RandomDataProvider.getInstance();

	protected RootBusinessLayer rootBusinessLayer(){
		return RootBusinessLayer.getInstance();
	}
	
}
