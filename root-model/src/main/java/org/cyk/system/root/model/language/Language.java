package org.cyk.system.root.model.language;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class Language extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	/**
	 * iso code (fr,en,...)
	 */
	private String isoCode;
	
	@Transient private Collection<LanguageEntry> entries = new HashSet<>();
	
	public Language(String code, String libelle) {
		super(code, libelle,libelle, null);
		this.isoCode = code.toLowerCase();
	}
	
	

}
