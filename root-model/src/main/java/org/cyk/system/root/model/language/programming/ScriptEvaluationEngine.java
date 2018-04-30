package org.cyk.system.root.model.language.programming;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class ScriptEvaluationEngine extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public ScriptEvaluationEngine(String code, String libelle, String description) {
		super(code, libelle,null, description);
	}
	
	
}
