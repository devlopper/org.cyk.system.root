package org.cyk.system.root.model.file;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE) @Accessors(chain=true)
/**
 * A series of instructions that is capable of being executed.
 * @author Christian Yao Komenan
 *
 */
public class Script extends AbstractIdentifiable implements Serializable{
	private static final long serialVersionUID = 129506142716551683L;
	
	@ManyToOne @JoinColumn(name=COLUMN_FILE) @NotNull private File file;
	@ManyToOne @JoinColumn(name=COLUMN_EVALUATION_ENGINE) @NotNull private ScriptEvaluationEngine evaluationEngine;
	@ManyToOne @JoinColumn(name=COLUMN_VARIABLE_COLLECTION) private ScriptVariableCollection variableCollection;
	
	@Transient private Map<String, Object> inputs,outputs;
	@Transient private Object returned;
	
	public File getFile(Boolean instanciateIfValueIsNull){
		return readFieldValue(FIELD_FILE, instanciateIfValueIsNull);
	}
	
	public Script setFileBytesFromString(String string){
		getFile(Boolean.TRUE).setBytesFromString(string);
		return this;
	}
	
	public ScriptVariableCollection getVariableCollection(Boolean instanciateIfValueIsNull){
		return ((ScriptVariableCollection)readFieldValue(FIELD_VARIABLE_COLLECTION, instanciateIfValueIsNull)).setItemsSynchonizationEnabled(Boolean.TRUE);
	}
	
	public Script addVariableCollectionItemsByName(String...names){
		getVariableCollection(Boolean.TRUE).addItemsByName(names);
		return this;
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
	public static final String FIELD_VARIABLE_COLLECTION = "variableCollection";
	
	public static final String COLUMN_FILE = FIELD_FILE;
	public static final String COLUMN_EVALUATION_ENGINE = FIELD_EVALUATION_ENGINE;
	public static final String COLUMN_VARIABLE_COLLECTION = FIELD_VARIABLE_COLLECTION;
}
