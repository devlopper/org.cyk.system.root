package org.cyk.system.root.business.impl;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Deprecated
public abstract class AbstractEnumerationDetails<ENUMERATION extends AbstractIdentifiable> extends AbstractOutputDetails<ENUMERATION> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	public AbstractEnumerationDetails(ENUMERATION enumeration) {
		super(enumeration);
	}
	
	/**/
	
	public static abstract class AbstractDefault<ENUMERATION extends AbstractEnumeration> extends AbstractEnumerationDetails<ENUMERATION> implements Serializable {
		private static final long serialVersionUID = 1L;

		public AbstractDefault(ENUMERATION enumeration) {
			super(enumeration);
		}
		
	}
	
}