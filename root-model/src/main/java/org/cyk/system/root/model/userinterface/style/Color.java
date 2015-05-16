package org.cyk.system.root.model.userinterface.style;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Color implements Serializable {

	private static final long serialVersionUID = -1829008070896183284L;

	private String hexademicalCode;
	
	public java.awt.Color systemColor(){
		if(StringUtils.isBlank(hexademicalCode))
			return null;
		return java.awt.Color.decode("0x"+hexademicalCode);
	}
	
}
