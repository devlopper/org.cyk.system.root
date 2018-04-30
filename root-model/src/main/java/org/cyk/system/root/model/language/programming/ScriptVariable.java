package org.cyk.system.root.model.language.programming;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE) @Accessors(chain=true)
public class ScriptVariable extends AbstractCollectionItem<ScriptVariableCollection> implements Serializable{
	private static final long serialVersionUID = 129506142716551683L;
	
}
