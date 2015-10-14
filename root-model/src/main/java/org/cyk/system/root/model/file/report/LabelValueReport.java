package org.cyk.system.root.model.file.report;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.utility.common.generator.AbstractGeneratable;

@Getter @Setter @NoArgsConstructor
public class LabelValueReport extends AbstractGeneratable<LabelValueReport> implements Serializable {

	private static final long serialVersionUID = -3815250939177148339L;

	private LabelValueCollectionReport collection;
	private String identifier,label,value;
	private String[] extendedValues;

	public LabelValueReport(LabelValueCollectionReport collection,String identifier, String label, String value) {
		super();
		this.collection = collection;
		this.identifier = identifier;
		this.label = label;
		this.value = value;
	}
	
	public void addExtendedValues(String...values){
		extendedValues = values;
	}
	
	@Override
	public void generate() {
		label = RandomStringUtils.randomAlphabetic(5);
		value = provider.randomWord(5, 15);
	}

}
