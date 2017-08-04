package org.cyk.system.root.business.impl.network;

import java.io.Serializable;

import org.cyk.system.root.model.network.UniformResourceLocator;

public class UniformResourceLocatorBuilderAdapter extends UniformResourceLocator.Builder.Listener.Adapter.Default implements Serializable {

	private static final long serialVersionUID = -1574930954646341195L;

	public static UniformResourceLocator.Builder.Listener DEFAULT = new UniformResourceLocatorBuilderAdapter();
	
}
