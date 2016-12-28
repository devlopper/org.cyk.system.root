package org.cyk.system.root.business.impl.file;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.file.Script;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScriptDetails extends AbstractOutputDetails<Script> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String file,evaluationEngine,arguments,returned,variables;
	
	public ScriptDetails(Script script) {
		super(script);
		file = formatUsingBusiness(script.getFile());
		evaluationEngine = formatUsingBusiness(script.getEvaluationEngine());
	}
	
	public static final String FIELD_FILE = "file";
	public static final String FIELD_EVALUATION_ENGINE = "evaluationEngine";
	public static final String FIELD_ARGUMENTS = "arguments";
	public static final String FIELD_RETURNED = "returned";
	public static final String FIELD_VARIABLES = "variables";
}