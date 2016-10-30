package org.cyk.system.root.business.impl.language;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.language.Language;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LanguageDetails extends AbstractOutputDetails<Language> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	public LanguageDetails(Language language) {
		super(language);
		
	}
	
	/**/

}