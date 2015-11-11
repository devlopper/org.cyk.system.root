package org.cyk.system.root.business.impl;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractFormatter<T> extends org.cyk.system.root.model.AbstractFormatter<T> implements Serializable{

	private static final long serialVersionUID = 3566948449829233213L;
	
	protected RootBusinessLayer rootBusinessLayer = RootBusinessLayer.getInstance();
	
	
}