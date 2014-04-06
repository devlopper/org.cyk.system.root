package org.cyk.system.root.model.language;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @NoArgsConstructor
public class LanguageEntry extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public LanguageEntry(String code, String libelle, String description) {
		super(code, libelle,null, description);
	}
	
	
}
