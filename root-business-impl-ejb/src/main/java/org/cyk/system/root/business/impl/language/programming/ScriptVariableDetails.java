package org.cyk.system.root.business.impl.language.programming;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.language.programming.ScriptVariable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScriptVariableDetails extends AbstractOutputDetails<ScriptVariable> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	public ScriptVariableDetails(ScriptVariable scriptVariable) {
		super(scriptVariable);
		
	}
	
}