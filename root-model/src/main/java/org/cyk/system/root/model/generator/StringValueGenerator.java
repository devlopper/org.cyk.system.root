package org.cyk.system.root.model.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

@Getter @Setter
public class StringValueGenerator<INPUT> extends AbstractValueGenerator<INPUT, String> implements Serializable {

	private static final long serialVersionUID = 2018757764631209047L;

	private String prefix,suffix;
	
	public StringValueGenerator(String identifier, String description,Class<INPUT> inputClass) {
		super(identifier, description, inputClass, String.class,new GenerateMethod<INPUT, String>() {
			@Override
			public String execute(INPUT input) {
				return RandomStringUtils.randomAlphabetic(8);
			}
		});
	}
	
	@Override
	public String generate(INPUT input) {
		List<String> values = new ArrayList<String>();
		if(StringUtils.isNotBlank(prefix))
			values.add(prefix);
		values.add(method.execute(input));
		if(StringUtils.isNotBlank(suffix))
			values.add(suffix);
		return StringUtils.join(values.toArray());
	}
	
}
