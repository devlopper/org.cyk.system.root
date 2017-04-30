package org.cyk.system.root.model.generator;

import java.io.Serializable;

import org.cyk.system.root.model.generator.ValueGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public abstract class AbstractValueGenerator<INPUT, OUTPUT> implements ValueGenerator<INPUT, OUTPUT>,Serializable {

	private static final long serialVersionUID = -8659646916509796342L;

	protected String identifier,description;
	protected Class<INPUT> inputClass;
	protected Class<OUTPUT> outputClass;
	protected GenerateMethod<INPUT, OUTPUT> method;
	
	/**/
	
	@Override
	public OUTPUT generate(INPUT input) {
		return method.execute(input);
	}
	
}
