package org.cyk.system.root.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public enum ContentType {
	TEXT("\r\n"," "),
	HTML("<br/>","&nbsp;"),
	
	;
	
	private String newLineMarker,spaceMarker;

	public static ContentType DEFAULT = TEXT;
	
}	