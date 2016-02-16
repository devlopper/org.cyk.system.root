package org.cyk.system.root.model;

import lombok.Getter;

/**
 * Multipurpose Internet Mail Extensions
 * @author Christian Yao Komenan
 *
 */
public enum Mime {

	PDF("application/pdf"),
	IMAGE_JPEG("image/jpeg"),
	
	;
	
	@Getter private String identifier;
	
	private Mime(String identifier) {
		this.identifier = identifier;
	}
	
	public static Mime getByIdentifier(String identifier){
		for(Mime mime : values())
			if(mime.identifier.equals(identifier))
				return mime;
		return null;
	}
}
