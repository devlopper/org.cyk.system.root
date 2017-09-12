package org.cyk.system.root.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//TODO should be moved to common utility
@Getter @AllArgsConstructor @NoArgsConstructor
public enum ContentType {
	TEXT("\r\n"," "),
	HTML("<br/>","&nbsp;"),
	
	;
	
	private String newLineMarker,spaceMarker;

	public static ContentType DEFAULT = TEXT;
	
}	