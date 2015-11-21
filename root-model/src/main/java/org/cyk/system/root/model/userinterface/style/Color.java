package org.cyk.system.root.model.userinterface.style;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Color implements Serializable {

	private static final long serialVersionUID = -1829008070896183284L;

	private String hexademicalCode;
	private java.awt.Color systemColor;
	
	public java.awt.Color getSystemColor(){
		if(systemColor==null){
			if(StringUtils.isBlank(hexademicalCode))
				;
			else
				systemColor = java.awt.Color.decode("0x"+hexademicalCode);
		}
		return systemColor;	
		
	}
	
}
