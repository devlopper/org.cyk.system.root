package org.cyk.system.root.ui.web.primefaces.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.language.Language;

@Singleton
public class SelectItemProvider implements Serializable {

	private static final long serialVersionUID = -536714923189958050L;

	@Inject private LanguageBusiness languageBusiness;
	
	@Named @Produces @ApplicationScoped
	public List<SelectItem> getLanguageItems(){
		List<SelectItem> list = new ArrayList<>();
		for(Language language : languageBusiness.findAll())
			list.add(new SelectItem(language, language.getName()));
		return list;
	}
	
}
