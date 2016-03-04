package org.cyk.system.root.model.file.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractModelElement;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LabelValueCollection extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 9095448953171778448L;

	private String name;
	private List<LabelValue> collection = new ArrayList<>(); 
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	@Override
	public String toString() {
		return StringUtils.join(collection,",");
	}

}
