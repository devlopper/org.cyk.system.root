package org.cyk.system.root.business.api.userinterface;

import org.cyk.system.root.model.markuplanguage.MarkupLanguageTag;

public interface HtmlBusiness {

    String format(MarkupLanguageTag tag,String body);
    
}
