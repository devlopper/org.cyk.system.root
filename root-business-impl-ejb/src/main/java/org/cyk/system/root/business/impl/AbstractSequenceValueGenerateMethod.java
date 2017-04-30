package org.cyk.system.root.business.impl;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.generator.ValueGenerator.GenerateMethod;

public abstract class AbstractSequenceValueGenerateMethod<IDENTIFIABLE extends AbstractIdentifiable> implements GenerateMethod<IDENTIFIABLE, String> {

	@Override
	public String execute(IDENTIFIABLE identifiable) {
		return null;
	}

}
