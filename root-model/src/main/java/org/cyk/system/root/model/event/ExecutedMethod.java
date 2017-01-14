package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.INTERNAL,genderType=GenderType.MALE)
public class ExecutedMethod extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;
	
	private String className;

	private String methodName;
	
	@Embedded @AttributeOverrides(value={
			@AttributeOverride(name=ExecutedMethodInputOutput.FIELD_COUNT,column=@Column(name="input_count"))
			,@AttributeOverride(name=ExecutedMethodInputOutput.FIELD_TEXT,column=@Column(name="input_text"))
			,@AttributeOverride(name=ExecutedMethodInputOutput.FIELD_COMPRESSED,column=@Column(name="input_compressed"))
	})
	private ExecutedMethodInputOutput input;
	
	@Embedded @AttributeOverrides(value={
			@AttributeOverride(name=ExecutedMethodInputOutput.FIELD_COUNT,column=@Column(name="output_count"))
			,@AttributeOverride(name=ExecutedMethodInputOutput.FIELD_TEXT,column=@Column(name="output_text"))
			,@AttributeOverride(name=ExecutedMethodInputOutput.FIELD_COMPRESSED,column=@Column(name="output_compressed"))
	})
	private ExecutedMethodInputOutput output;
	
	private String throwable;

	/**/
	
	public ExecutedMethodInputOutput getInput(){
		if(input==null)
			input = new ExecutedMethodInputOutput();
		return input;
	}
	
	public ExecutedMethodInputOutput getOutput(){
		if(output==null)
			output = new ExecutedMethodInputOutput();
		return output;
	}
	
	@Override
	public String toString() {
		Boolean hasOutputs = getOutput().getCount() != null && getOutput().getCount() > 0 ;
		return String.format(TO_STRING_FORMAT, className,methodName,getInput().getCount()==null || getInput().getCount()==0 ? Constant.EMPTY_STRING : getInput().getText()
				, hasOutputs ? Constant.CHARACTER_EQUAL : Constant.EMPTY_STRING , hasOutputs ? getOutput().getText() : Constant.EMPTY_STRING)
				+(StringUtils.isBlank(throwable)?Constant.EMPTY_STRING:" ,  "+throwable);
	}
	
	/**/
	
	private static final String TO_STRING_FORMAT = "%s.%s(%s)%s%s";
	
	public static final String FIELD_CLASS_NAME = "className";
	public static final String FIELD_METHOD_NAME = "methodName";
	public static final String FIELD_INPUT = "input";
	public static final String FIELD_OUTPUT = "output";
	public static final String FIELD_THROWABLE = "throwable";
}
