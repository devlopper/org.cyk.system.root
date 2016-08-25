package org.cyk.system.root.business.impl.language;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.language.LanguageCollection;
import org.cyk.system.root.model.language.LanguageCollectionItem;
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
			Set<String> values = new LinkedHashSet<>();
			for(LanguageCollectionItem item : languageCollection.getCollection()){
				values.add(item.getLanguage().getName());
			}
			languages = StringUtils.join(values,Constant.CHARACTER_COMA);
		}
		
	}
	
	/**/
	public static final String FIELD_LANGUAGES = "languages";
	
}