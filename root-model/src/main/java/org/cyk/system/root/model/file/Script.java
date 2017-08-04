package org.cyk.system.root.model.file;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
/**
 * A series of instructions that is capable of being executed.
 * @author Christian Yao Komenan
 *
 */
public class Script extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 129506142716551683L;
	
	@OneToOne @NotNull private File file;
	
	@ManyToOne @NotNull private ScriptEvaluationEngine evaluationEngine;
	
	@Transient private IdentifiableRuntimeCollection<ScriptVariable> variables;

	@Transient private Map<String, Object> inputs,outputs;
	@Transient private Object returned;
	
	public IdentifiableRuntimeCollection<ScriptVariable> getVariables(){
		if(variables==null)
			variables = new IdentifiableRuntimeCollection<>();
		return variables;
	}
	
	public Map<String, Object> getInputs(){
		if(inputs==null)
			inputs = new HashMap<>();
		return inputs;
	}
	
	public Map<String, Object> getOutputs(){
		if(outputs==null)
			outputs = new HashMap<>();
		return outputs;
	}
	
	public static final String FIELD_FILE = "file";
	public static final String FIELD_EVALUATION_ENGINE = "evaluationEngine";
}
