package org.cyk.system.root.model.file.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractModelElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LabelValueCollection extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 9095448953171778448L;

	private Collection<LabelValue> collection = new ArrayList<>(); 
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	public void add(String label,String value){
		collection.add(new LabelValue(label, value));
	}
	
	@Override
	public String toString() {
		return StringUtils.join(collection,",");
	}

}
