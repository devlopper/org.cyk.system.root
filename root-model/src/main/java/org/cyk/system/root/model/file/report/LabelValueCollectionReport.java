package org.cyk.system.root.model.file.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.utility.common.generator.AbstractGeneratable;

@Getter @Setter
public class LabelValueCollectionReport extends AbstractGeneratable<LabelValueCollectionReport> implements Serializable {

	private static final long serialVersionUID = -3815250939177148339L;

	private String name;
	private List<LabelValueReport> collection = new ArrayList<>();
	private Integer labelWidth,labelHeight,valueWidth,valueHeight;
	
	@Override
	public void generate() {
		name = RandomStringUtils.randomAlphabetic(5);
		for(int i=0;i<provider.randomInt(3, 6);i++){
			LabelValueReport labelValueReport = new LabelValueReport();
			labelValueReport.generate();
			collection.add(labelValueReport);
		}
	}

}
