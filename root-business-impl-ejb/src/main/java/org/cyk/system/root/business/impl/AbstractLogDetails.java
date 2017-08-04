package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractLog;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Deprecated
public abstract class AbstractLogDetails<LOG extends AbstractLog> extends AbstractOutputDetails<LOG> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
	
	public AbstractLogDetails(LOG log) {
		super(log);
	}
	
}