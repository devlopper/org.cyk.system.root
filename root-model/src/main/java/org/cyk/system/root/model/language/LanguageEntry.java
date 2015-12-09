package org.cyk.system.root.model.language;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.INTERNAL)
public class LanguageEntry extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @NotNull private Language language;
	
	public LanguageEntry(Language language,String code, String libelle, String description) {
		super(code, libelle,null, description);
	}
	

	/**/
	
	public static final String YES = "yes";
	public static final String NO = "no";
	
}
