package org.cyk.system.root.model.generator;

import org.apache.commons.lang3.RandomStringUtils;

public class StringValueGenerator<INPUT> extends AbstractValueGenerator<INPUT, String> {

	public StringValueGenerator(String identifier, String description,Class<INPUT> inputClass) {
		super(identifier, description, inputClass, String.class,new GenerateMethod<INPUT, String>() {

			@Override
			public String execute(INPUT input) {
				return RandomStringUtils.randomAlphabetic(8);
			}
		});
	}

}
