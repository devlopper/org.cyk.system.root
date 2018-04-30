package org.cyk.system.root.model.language.programming;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Entity  @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) @Accessors(chain=true)
public class ScriptVariableCollection extends AbstractCollection<ScriptVariable> implements Serializable {
	private static final long serialVersionUID = -165832578043422718L;

	@Override
	public ScriptVariableCollection setItemsSynchonizationEnabled(Boolean synchonizationEnabled) {
		return (ScriptVariableCollection) super.setItemsSynchonizationEnabled(synchonizationEnabled);
	}
	
}