package org.cyk.system.root.business.impl;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public abstract class AbstractEnumerationDetails<ENUMERATION extends AbstractEnumeration> extends AbstractOutputDetails<ENUMERATION> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText protected String code,name;
	
	public AbstractEnumerationDetails(ENUMERATION enumeration) {
		super(enumeration);
		code = enumeration.getCode();
		name = enumeration.getName();
	}
	
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
}