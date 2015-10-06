package org.cyk.system.root.model.file.report;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.utility.common.generator.AbstractGeneratable;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class LabelValueReport extends AbstractGeneratable<LabelValueReport> implements Serializable {

	private static final long serialVersionUID = -3815250939177148339L;

	private String identifier,label,value;
	
	@Override
	public void generate() {
		label = RandomStringUtils.randomAlphabetic(5);
		value = provider.randomWord(5, 15);
	}

}
