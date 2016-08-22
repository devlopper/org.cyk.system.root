package org.cyk.system.root.model.language;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity  @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class LanguageCollection extends AbstractCollection<LanguageCollectionItem> implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;

	public LanguageCollection(String code) {
		super(code, code,null,null);
	}
	
}