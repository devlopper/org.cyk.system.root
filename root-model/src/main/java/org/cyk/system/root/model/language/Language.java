package org.cyk.system.root.model.language;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.INTERNAL)
public class Language extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	/**
	 * iso code (fr,en,...)
	 */
	@Input
	@InputText
	private String isoCode;
	
	public Language(String code, String libelle) {
		super(code, libelle,libelle, null);
		this.isoCode = code.toLowerCase();
	}
	
	private Collection<LanguageEntry> entries = new HashSet<>();

}
