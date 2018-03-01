package org.cyk.system.root.business.impl.__data__;

import java.io.Serializable;

import org.cyk.system.root.business.impl.RootBusinessLayer;

public class FakedDataSet extends DataSet implements Serializable {
	private static final long serialVersionUID = 1L;

	public FakedDataSet() {
		super(RootBusinessLayer.class);
		
	}
	
}
