package org.cyk.system.root.model.file;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class Script extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 129506142716551683L;
	
	@OneToOne private File file;
	
	@Transient private Collection<ScriptVariable> variables;

	public Collection<ScriptVariable> getVariables(){
		if(variables==null)
			variables = new ArrayList<>();
		return variables;
	}
	
}
