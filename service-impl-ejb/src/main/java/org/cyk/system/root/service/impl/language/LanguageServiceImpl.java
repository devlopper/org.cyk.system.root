package org.cyk.system.root.service.impl.language;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.dao.api.language.LanguageDao;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.service.api.language.LanguageService;
import org.cyk.system.root.service.impl.AbstractTypedService;

@Stateless
public class LanguageServiceImpl extends AbstractTypedService<Language, LanguageDao> implements LanguageService,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	//private static final ResourceBundle I18N = ResourceBundle.getBundle("org.cyk.system.root.model.language.i18n", Locale.ENGLISH);
	
	@Inject
	public LanguageServiceImpl(LanguageDao dao) {
		super(dao);
	} 

	@Override
	public String i18n(String code, Locale locale) {
		try {
			return ResourceBundle.getBundle("org.cyk.system.root.model.language.i18n",locale).getString(code);
		} catch (Exception e) {
			return "##"+code+"##";
		}
		
	}
	
	

}
