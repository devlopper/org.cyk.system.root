package org.cyk.system.root.business.impl.language;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.language.LanguageCollection;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LanguageCollectionDetails extends AbstractOutputDetails<LanguageCollection> implements Serializable {
	private static final long serialVersionUID = -1498269103849317057L;
	
	@Input @InputText private String languages;
	
	public LanguageCollectionDetails(LanguageCollection languageCollection) {
		super(languageCollection);
		if(languageCollection==null){
			
		}else{
			languages = StringUtils.join(languageCollection.getCollection(),Constant.CHARACTER_COMA);
		}
		
	}
	
	/**/
	public static final String FIELD_LANGUAGES = "languages";
	
}